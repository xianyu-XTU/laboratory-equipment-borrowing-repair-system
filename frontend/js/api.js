const API_BASE = "http://localhost:8080";

function getToken() {
    return localStorage.getItem("token") || "";
}

function getLoginUser() {
    const text = localStorage.getItem("loginUser");
    if (!text) return null;
    try { return JSON.parse(text); } catch (e) { return null; }
}

function roleCodeToName(code) {
    return { ADMIN: "管理员", LAB_ADMIN: "实验员", STUDENT: "学生" }[code] || code || "学生";
}

function roleNameToCode(name) {
    return { "管理员": "ADMIN", "实验员": "LAB_ADMIN", "学生": "STUDENT" }[name] || name || "STUDENT";
}

function roleNameToId(name) {
    return { "管理员": 1, "实验员": 2, "学生": 3, ADMIN: 1, LAB_ADMIN: 2, STUDENT: 3 }[name] || 3;
}

function roleIdToName(id) {
    return { 1: "管理员", 2: "实验员", 3: "学生" }[Number(id)] || "学生";
}

function clearLogin() {
    localStorage.removeItem("token");
    localStorage.removeItem("loginUser");
    location.href = "login.html";
}

async function request(path, options = {}) {
    const token = getToken();
    const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
    if (token) headers.Authorization = "Bearer " + token;
    let res;
    try {
        res = await fetch(API_BASE + path, { ...options, headers });
    } catch (e) {
        throw new Error("无法连接后端，请确认 Spring Boot 已在 8080 端口启动");
    }
    let data;
    try { data = await res.json(); } catch (e) { throw new Error("服务器响应格式错误"); }
    if (res.status === 401 || data.code === 401) { clearLogin(); throw new Error("登录已过期，请重新登录"); }
    if (res.status === 403 || data.code === 403) throw new Error(data.message || "没有权限访问该接口");
    if (data.code !== 200) throw new Error(data.message || data.msg || "请求失败");
    return data.data;
}

function requireLogin(roleList) {
    const token = getToken();
    const user = getLoginUser();
    if (!token || !user) { location.href = "login.html"; return false; }
    const codes = (roleList || []).map(roleNameToCode);
    if (codes.length && !codes.includes(user.roleCode)) {
        alert("没有访问权限");
        location.href = "login.html";
        return false;
    }
    return true;
}

function requireAuth(roleList) {
    const ok = requireLogin(roleList);
    return ok ? getLoginUser() : null;
}

function setLoggedUser(user) {
    if (user.token) localStorage.setItem("token", user.token);
    const fixed = normalizeUser(user);
    localStorage.setItem("loginUser", JSON.stringify(fixed));
    return fixed;
}

function normalizeUser(user) {
    const roleCode = user.roleCode || roleNameToCode(user.role);
    return {
        ...user,
        roleCode,
        role: user.role || roleCodeToName(roleCode),
        realName: user.realName || user.username || "用户"
    };
}

function getRolePage(role) {
    const code = roleNameToCode(role);
    if (code === "ADMIN") return "board_admin.html";
    if (code === "LAB_ADMIN") return "board_lab.html";
    return "board_student.html";
}

function redirectIfLoggedIn() {
    const user = getLoginUser();
    const token = getToken();
    if (token && user) location.href = getRolePage(user.roleCode || user.role);
}

function logout() { clearLogin(); }
function handleApiError(err) { alert((err && err.message) ? err.message : "操作失败"); }
function val(id) { return document.getElementById(id).value.trim(); }
function setText(id, text) { document.getElementById(id).innerText = text; }

function formatDate(value) {
    if (!value) return "-";
    if (Array.isArray(value)) {
        const [y, m, d, h = 0, mi = 0, s = 0] = value;
        return `${y}-${String(m).padStart(2,'0')}-${String(d).padStart(2,'0')} ${String(h).padStart(2,'0')}:${String(mi).padStart(2,'0')}:${String(s).padStart(2,'0')}`;
    }
    return String(value).replace('T', ' ').slice(0, 19);
}

const deviceStatusMap = { 1: "可借", 2: "已借出", 3: "维修中", 4: "报废", 0: "停用" };
const deviceStatusCodeMap = { "可借": 1, "已借出": 2, "维修中": 3, "报废": 4, "停用": 4 };
const applyStatusMap = { 0: "待审批", 1: "已通过", 2: "已拒绝" };
const recordStatusMap = { 1: "借用中", 2: "已归还", 3: "逾期" };
const repairStatusMap = { 0: "待处理", 1: "维修中", 2: "已完成" };
const repairStatusCodeMap = { "待处理": 0, "维修中": 1, "处理中": 1, "已完成": 2 };

function statusDevice(s) { return deviceStatusMap[s] || s || "-"; }
function statusApply(s) { return applyStatusMap[s] || s || "-"; }
function statusRecord(s) { return recordStatusMap[s] || s || "-"; }
function statusRepair(s) { return repairStatusMap[s] || s || "-"; }
function repairStatusCode(s) { return typeof s === 'number' ? s : (repairStatusCodeMap[s] ?? 1); }
function deviceStatusCode(s) { return typeof s === 'number' ? s : (deviceStatusCodeMap[s] ?? 1); }

function pageRecords(p) { return (p && p.records) ? p.records : (Array.isArray(p) ? p : []); }

function mapDevice(d) {
    return {
        ...d,
        device_no: d.deviceNo || d.device_no || "",
        device_name: d.name || d.deviceName || d.device_name || "",
        category: d.categoryName || d.category || d.categoryId || "-",
        statusCode: Number(d.status),
        status: statusDevice(d.status)
    };
}

function mapApply(a) {
    return {
        ...a,
        user_name: a.userName || a.user_name || a.userId || "-",
        device_name: a.deviceName || a.device_name || a.deviceId || "-",
        device_no: a.deviceNo || a.device_no || "",
        reason: a.applyReason || a.reason || "-",
        apply_time: formatDate(a.applyTime || a.apply_time),
        expected_return: formatDate(a.expectedReturnTime || a.expected_return),
        statusCode: Number(a.status),
        status: statusApply(a.status)
    };
}

function mapRecord(r) {
    return {
        ...r,
        user_name: r.userName || r.user_name || r.userId || "-",
        device_name: r.deviceName || r.device_name || r.deviceId || "-",
        device_no: r.deviceNo || r.device_no || "",
        borrow_time: formatDate(r.borrowTime || r.borrow_time),
        return_time: formatDate(r.returnTime || r.return_time),
        expected_return: formatDate(r.expectedReturnTime || r.expected_return),
        statusCode: Number(r.status),
        status: statusRecord(r.status),
        overdue: r.isOverdue === 1 || r.isOverdue === true
    };
}

function mapRepair(r) {
    return {
        ...r,
        user_name: r.userName || r.user_name || r.userId || "-",
        device_name: r.deviceName || r.device_name || r.deviceId || "-",
        device_no: r.deviceNo || r.device_no || "",
        fault_desc: r.faultDesc || r.fault_desc || "-",
        report_time: formatDate(r.reportTime || r.report_time),
        handle_time: formatDate(r.handleTime || r.handle_time),
        result: r.handleResult || r.result || "-",
        statusCode: Number(r.status),
        status: statusRepair(r.status)
    };
}

function mapNotice(n) {
    return {
        ...n,
        publish_time: formatDate(n.publishTime || n.publish_time),
        publisher: n.publisher || n.publishUserId || "系统"
    };
}

const Api = {
    async login(username, password) {
        const data = await request('/api/auth/login', { method: 'POST', body: JSON.stringify({ username, password }) });
        return normalizeUser(data);
    },
    async register(data) {
        return request('/api/auth/register', { method: 'POST', body: JSON.stringify(data) });
    },
    async getDevices(keyword = '') {
        const q = keyword ? `&keyword=${encodeURIComponent(keyword)}` : '';
        const p = await request(`/api/devices?page=1&size=100${q}`);
        return pageRecords(p).map(mapDevice);
    },
    async saveDevice(data, id) {
        const payload = {
            id: id || data.id,
            deviceNo: data.device_no || data.deviceNo,
            name: data.device_name || data.name || data.deviceName,
            model: data.model || '',
            location: data.location || '',
            categoryId: Number(data.categoryId || data.category_id || 1),
            status: deviceStatusCode(data.status)
        };
        const method = payload.id ? 'PUT' : 'POST';
        return request('/api/devices', { method, body: JSON.stringify(payload) });
    },
    async deleteDevice(id) {
        return request(`/api/devices/${id}`, { method: 'DELETE' });
    },
    async getStats() {
        const s = await request('/api/stats/overview');
        let users = 0;
        try {
            const p = await request('/api/users?page=1&size=1');
            users = p.total || 0;
        } catch (e) { users = 0; }
        return {
            ...s,
            userCount: users,
            deviceCount: s.deviceTotal ?? 0,
            pendingApplyCount: s.pendingApplies ?? 0,
            pendingRepairCount: s.pendingRepairs ?? 0,
            borrowingRecordCount: s.borrowingRecords ?? 0
        };
    },
    async getApplies(status) {
        const q = status !== undefined && status !== null ? `&status=${status}` : '';
        const p = await request(`/api/borrow/applies?page=1&size=100${q}`);
        return pageRecords(p).map(mapApply);
    },
    async getMyBorrowApplies() { return this.getApplies(); },
    async applyBorrow(deviceId, applyReason, expectedReturnTime) {
        return request('/api/borrow/apply', { method: 'POST', body: JSON.stringify({ deviceId, applyReason, expectedReturnTime }) });
    },
    async approveBorrow(applyId, approved) {
        return request('/api/borrow/approve', { method: 'POST', body: JSON.stringify({ applyId, status: approved ? 1 : 2, approveRemark: approved ? '同意借用' : '拒绝借用' }) });
    },
    async getRecords(status) {
        const q = status !== undefined && status !== null ? `&status=${status}` : '';
        const p = await request(`/api/borrow/records?page=1&size=100${q}`);
        return pageRecords(p).map(mapRecord);
    },
    async getMyBorrowRecords() { return this.getRecords(); },
    async returnDevice(recordId, remark = '前端确认归还') {
        return request('/api/borrow/return', { method: 'POST', body: JSON.stringify({ recordId, remark }) });
    },
    async getRepairs(status) {
        const q = status !== undefined && status !== null ? `&status=${status}` : '';
        const p = await request(`/api/repairs?page=1&size=100${q}`);
        return pageRecords(p).map(mapRepair);
    },
    async getMyRepairs() { return this.getRepairs(); },
    async reportRepair(deviceId, faultDesc) {
        return request('/api/repairs/report', { method: 'POST', body: JSON.stringify({ deviceId, faultDesc }) });
    },
    async handleRepair(repairId, status, handleResult = '') {
        return request('/api/repairs/handle', { method: 'POST', body: JSON.stringify({ repairId, status: repairStatusCode(status), handleResult }) });
    },
    async getNotices() {
        const p = await request('/api/notices?page=1&size=100');
        return pageRecords(p).map(mapNotice);
    },
    async publishNotice(title, content) {
        return request('/api/notices', { method: 'POST', body: JSON.stringify({ title, content, status: 1 }) });
    },
    async deleteNotice(id) {
        alert('当前后端未提供删除公告接口，公告不会被删除。');
        return Promise.resolve();
    },
    async getUsers(keyword = '') {
        const q = keyword ? `&keyword=${encodeURIComponent(keyword)}` : '';
        const p = await request(`/api/users?page=1&size=100${q}`);
        return pageRecords(p).map(u => ({ ...u, role: roleIdToName(u.roleId) }));
    },
    async saveUser(data) {
        const payload = {
            id: data.id,
            username: data.username,
            realName: data.realName,
            phone: data.phone,
            roleId: roleNameToId(data.role),
            status: Number(data.status ?? 1)
        };
        if (!payload.id) payload.password = data.password || '123456';
        return request('/api/users', { method: payload.id ? 'PUT' : 'POST', body: JSON.stringify(payload) });
    },
    async toggleUserStatus(id, currentStatus) {
        const next = Number(currentStatus) === 1 ? 0 : 1;
        return request(`/api/users/${id}/status?status=${next}`, { method: 'PUT' });
    },
    async resetUserPassword(id) {
        return request('/api/users', { method: 'PUT', body: JSON.stringify({ id, password: '123456' }) });
    }
};
