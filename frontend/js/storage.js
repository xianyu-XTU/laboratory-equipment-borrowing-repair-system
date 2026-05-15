/**
 * 实验室设备管理系统 - 统一 localStorage 数据层
 */
const STORAGE_KEYS = {
    USERS: 'lab_equipment_users',
    DEVICES: 'lab_devices',
    APPLIES: 'lab_borrow_applies',
    RECORDS: 'lab_borrow_records',
    REPAIRS: 'lab_repairs',
    NOTICES: 'lab_notices'
};

function initSharedData() {
    if (!localStorage.getItem(STORAGE_KEYS.USERS)) {
        localStorage.setItem(STORAGE_KEYS.USERS, JSON.stringify([
            { id: 1, username: 'admin', password: '123456', realName: '系统管理员', phone: '13800000000', role: '管理员', roleCode: 'ADMIN', status: 1 },
            { id: 2, username: 'lab', password: '123456', realName: '实验员', phone: '13800000001', role: '实验员', roleCode: 'LAB_ADMIN', status: 1 },
            { id: 3, username: 'student', password: '123456', realName: '学生用户', phone: '13800000002', role: '学生', roleCode: 'STUDENT', status: 1 }
        ]));
    } else {
        const users = JSON.parse(localStorage.getItem(STORAGE_KEYS.USERS));
        let needUpdate = false;
        users.forEach(u => { if (u.status === undefined) { u.status = 1; needUpdate = true; } });
        if (needUpdate) localStorage.setItem(STORAGE_KEYS.USERS, JSON.stringify(users));
    }

    if (!localStorage.getItem(STORAGE_KEYS.DEVICES)) {
        localStorage.setItem(STORAGE_KEYS.DEVICES, JSON.stringify([
            { id: 1, device_no: 'DEV2026001', device_name: '数字示波器', category: '电子测量设备', location: 'A101实验室', status: '可借' },
            { id: 2, device_no: 'DEV2026002', device_name: '数字万用表', category: '电子测量设备', location: 'A101实验室', status: '可借' },
            { id: 3, device_no: 'DEV2026003', device_name: '实验室台式电脑', category: '计算机设备', location: 'B203实验室', status: '已借出' },
            { id: 4, device_no: 'DEV2026004', device_name: '电子天平', category: '化学实验设备', location: 'C301实验室', status: '维修中' },
            { id: 5, device_no: 'DEV2026005', device_name: '信号发生器', category: '电子测量设备', location: 'A102实验室', status: '可借' }
        ]));
    }

    if (!localStorage.getItem(STORAGE_KEYS.APPLIES)) {
        localStorage.setItem(STORAGE_KEYS.APPLIES, JSON.stringify([
            { id: 101, user_id: 3, user_name: '学生用户', device_id: 5, device_name: '信号发生器', reason: '课程实验', apply_time: '2026-05-01 09:15', expected_return: '2026-05-20', status: '待审批' }
        ]));
    }

    if (!localStorage.getItem(STORAGE_KEYS.RECORDS)) {
        localStorage.setItem(STORAGE_KEYS.RECORDS, JSON.stringify([
            { id: 1, user_id: 3, device_id: 3, device_name: '实验室台式电脑', user_name: '学生用户', borrow_time: '2026-04-20 10:00', expected_return: '2026-05-10', return_time: null, status: '借用中' },
            { id: 2, user_id: 3, device_id: 2, device_name: '数字万用表', user_name: '学生用户', borrow_time: '2026-04-25 14:30', expected_return: '2026-05-05', return_time: '2026-05-03', status: '已归还' }
        ]));
    }

    if (!localStorage.getItem(STORAGE_KEYS.REPAIRS)) {
        localStorage.setItem(STORAGE_KEYS.REPAIRS, JSON.stringify([
            { id: 1, user_id: 3, device_id: 4, device_name: '电子天平', user_name: '学生用户', fault_desc: '显示屏不亮', report_time: '2026-04-18 15:20', status: '维修中', result: '已安排维修' }
        ]));
    }

    if (!localStorage.getItem(STORAGE_KEYS.NOTICES)) {
        localStorage.setItem(STORAGE_KEYS.NOTICES, JSON.stringify([
            { id: 1, title: '五一假期实验室开放安排', content: '5月1日-5月5日实验室正常开放，设备借用需提前申请。', publish_time: '2026-04-28', publisher: '管理员' },
            { id: 2, title: '新设备已入库', content: '新增20套单片机开发板，欢迎同学们借用学习。', publish_time: '2026-04-25', publisher: '管理员' },
            { id: 3, title: '设备维护提醒', content: '示波器固件升级已完成，借用时注意规范操作。', publish_time: '2026-04-20', publisher: '实验员' }
        ]));
    }
}

function getStore(key) { return JSON.parse(localStorage.getItem(key)) || []; }
function setStore(key, data) { localStorage.setItem(key, JSON.stringify(data)); }

function getDevices() { return getStore(STORAGE_KEYS.DEVICES); }
function saveDevices(d) { setStore(STORAGE_KEYS.DEVICES, d); }
function getApplies() { return getStore(STORAGE_KEYS.APPLIES); }
function saveApplies(a) { setStore(STORAGE_KEYS.APPLIES, a); }
function getRecords() { return getStore(STORAGE_KEYS.RECORDS); }
function saveRecords(r) { setStore(STORAGE_KEYS.RECORDS, r); }
function getRepairs() { return getStore(STORAGE_KEYS.REPAIRS); }
function saveRepairs(r) { setStore(STORAGE_KEYS.REPAIRS, r); }
function getNotices() { return getStore(STORAGE_KEYS.NOTICES); }
function saveNotices(n) { setStore(STORAGE_KEYS.NOTICES, n); }
function getUsers() { return getStore(STORAGE_KEYS.USERS); }
