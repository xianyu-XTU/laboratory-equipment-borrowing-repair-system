-- ============================================
-- 实验室设备管理系统 - 大量测试数据
-- 总数据量：约 350-400 条
-- ============================================

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE borrow_record;
TRUNCATE TABLE borrow_apply;
TRUNCATE TABLE repair_record;
TRUNCATE TABLE notice;
TRUNCATE TABLE device;
TRUNCATE TABLE device_category;
TRUNCATE TABLE user;
TRUNCATE TABLE role;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 1. 角色表 (5条)
-- ============================================
INSERT INTO role (role_name, role_code) VALUES
('学生', 'STUDENT'),
('实验员', 'LAB_ADMIN'),
('管理员', 'ADMIN'),
('教师', 'TEACHER'),
('系主任', 'DEPARTMENT_HEAD');

-- ============================================
-- 2. 用户表 (50条)
-- ============================================
INSERT INTO user (username, password, real_name, phone, email, role_id, status) VALUES
('student001', '123456', '李明', '13800000001', 'liming@school.com', 1, 1),
('student002', '123456', '王红', '13800000002', 'wanghong@school.com', 1, 1),
('student003', '123456', '张强', '13800000003', 'zhangqiang@school.com', 1, 1),
('student004', '123456', '刘丽', '13800000004', 'liuli@school.com', 1, 1),
('student005', '123456', '陈晨', '13800000005', 'chenchen@school.com', 1, 1),
('student006', '123456', '赵磊', '13800000006', 'zhaolei@school.com', 1, 1),
('student007', '123456', '周婷', '13800000007', 'zhouting@school.com', 1, 1),
('student008', '123456', '吴迪', '13800000008', 'wudi@school.com', 1, 1),
('student009', '123456', '郑爽', '13800000009', 'zhengshuang@school.com', 1, 1),
('student010', '123456', '孙阳', '13800000010', 'sunyang@school.com', 1, 1),
('student011', '123456', '林欣', '13800000011', 'linxin@school.com', 1, 1),
('student012', '123456', '郭峰', '13800000012', 'guofeng@school.com', 1, 1),
('student013', '123456', '唐雅', '13800000013', 'tangya@school.com', 1, 1),
('student014', '123456', '沈浩', '13800000014', 'shenhao@school.com', 1, 1),
('student015', '123456', '宋阳', '13800000015', 'songyang@school.com', 1, 1),
('student016', '123456', '韩梅', '13800000016', 'hanmei@school.com', 1, 1),
('student017', '123456', '彭博', '13800000017', 'pengbo@school.com', 1, 1),
('student018', '123456', '陆瑶', '13800000018', 'luyao@school.com', 1, 1),
('student019', '123456', '苏哲', '13800000019', 'suzhe@school.com', 1, 1),
('student020', '123456', '蔡琴', '13800000020', 'caiqin@school.com', 1, 1),
('teacher001', '123456', '王建国', '13900000001', 'wangjg@school.com', 4, 1),
('teacher002', '123456', '李芳', '13900000002', 'lifang@school.com', 4, 1),
('teacher003', '123456', '张明远', '13900000003', 'zhangmy@school.com', 4, 1),
('teacher004', '123456', '刘德华', '13900000004', 'liudh@school.com', 4, 1),
('teacher005', '123456', '陈思思', '13900000005', 'chenss@school.com', 4, 1),
('teacher006', '123456', '赵本山', '13900000006', 'zhaobs@school.com', 4, 1),
('teacher007', '123456', '周杰伦', '13900000007', 'zhoujl@school.com', 4, 1),
('teacher008', '123456', '吴彦祖', '13900000008', 'wu yz@school.com', 4, 1),
('teacher009', '123456', '郑伊健', '13900000009', 'zhengyj@school.com', 4, 1),
('teacher010', '123456', '孙俪', '13900000010', 'sunli@school.com', 4, 1),
('lab001', '123456', '张工', '13700000001', 'zhanggong@lab.com', 2, 1),
('lab002', '123456', '李工', '13700000002', 'ligong@lab.com', 2, 1),
('lab003', '123456', '王工', '13700000003', 'wanggong@lab.com', 2, 1),
('lab004', '123456', '赵工', '13700000004', 'zhaogong@lab.com', 2, 1),
('lab005', '123456', '刘工', '13700000005', 'liugong@lab.com', 2, 1),
('admin001', '123456', '超级管理员', '13600000001', 'admin@lab.com', 3, 1),
('dept001', '123456', '张院长', '13500000001', 'zhangdean@school.com', 5, 1),
('dept002', '123456', '李院长', '13500000002', 'lidean@school.com', 5, 1);

-- ============================================
-- 3. 设备分类表 (12条)
-- ============================================
INSERT INTO device_category (category_name, description) VALUES
('示波器类', '数字/模拟示波器，用于信号测量'),
('万用表类', '数字万用表，电压电流电阻测量'),
('信号源类', '函数信号发生器、任意波形发生器'),
('电源类', '直流稳压电源、可编程电源'),
('计算机类', '台式机、笔记本、工作站'),
('网络设备类', '路由器、交换机、服务器'),
('投影设备类', '投影仪、幕布、音响'),
('测量仪器类', '电子天平、温湿度计、噪声仪'),
('通信设备类', '频谱仪、网络分析仪'),
('实验平台类', '开发板、实验箱、套件'),
('存储设备类', '硬盘、U盘、NAS'),
('外设类', '键盘、鼠标、显示器');

-- ============================================
-- 4. 设备表 (80条)
-- ============================================
INSERT INTO device (device_no, device_name, category_id, model, location, purchase_date, status, description) VALUES
-- 示波器类 (1-10)
('DSO-001', '数字示波器', 1, 'Rigol DS1054Z', 'A-101', '2023-01-15', 1, '50MHz 4通道'),
('DSO-002', '数字示波器', 1, 'Rigol DS1054Z', 'A-101', '2023-01-15', 1, '50MHz 4通道'),
('DSO-003', '数字示波器', 1, 'Rigol DS1054Z', 'A-101', '2023-01-15', 2, '50MHz 4通道'),
('DSO-004', '数字示波器', 1, 'Rigol DS1054Z', 'A-101', '2023-01-15', 1, '50MHz 4通道'),
('DSO-005', '数字示波器', 1, 'Rigol DS1054Z', 'A-101', '2023-01-15', 3, '50MHz 4通道'),
('DSO-006', '数字示波器', 1, 'Tektronix TBS1102C', 'A-102', '2023-03-20', 1, '100MHz 2通道'),
('DSO-007', '数字示波器', 1, 'Tektronix TBS1102C', 'A-102', '2023-03-20', 1, '100MHz 2通道'),
('DSO-008', '数字示波器', 1, 'Tektronix TBS1102C', 'A-102', '2023-03-20', 2, '100MHz 2通道'),
('DSO-009', '手持示波器', 1, 'Fluke 123B', 'A-103', '2023-05-10', 1, '便携式'),
('DSO-010', '手持示波器', 1, 'Fluke 123B', 'A-103', '2023-05-10', 4, '便携式-待报废'),
-- 万用表类 (11-20)
('DMM-001', '数字万用表', 2, 'Fluke 17B+', 'A-201', '2023-02-01', 1, '高精度'),
('DMM-002', '数字万用表', 2, 'Fluke 17B+', 'A-201', '2023-02-01', 1, '高精度'),
('DMM-003', '数字万用表', 2, 'Fluke 17B+', 'A-201', '2023-02-01', 2, '高精度'),
('DMM-004', '数字万用表', 2, 'Fluke 17B+', 'A-201', '2023-02-01', 1, '高精度'),
('DMM-005', '数字万用表', 2, 'Fluke 17B+', 'A-201', '2023-02-01', 3, '高精度-维修'),
('DMM-006', '数字万用表', 2, 'UNI-T UT61E', 'A-202', '2023-04-15', 1, '高精度'),
('DMM-007', '数字万用表', 2, 'UNI-T UT61E', 'A-202', '2023-04-15', 1, '高精度'),
('DMM-008', '数字万用表', 2, 'UNI-T UT61E', 'A-202', '2023-04-15', 2, '高精度'),
('DMM-009', '台式万用表', 2, 'Rigol DM3068', 'A-203', '2023-06-01', 1, '6位半'),
('DMM-010', '台式万用表', 2, 'Rigol DM3068', 'A-203', '2023-06-01', 1, '6位半'),
-- 信号源类 (21-30)
('SG-001', '函数信号发生器', 3, 'Rigol DG1022Z', 'B-101', '2023-02-10', 1, '双通道'),
('SG-002', '函数信号发生器', 3, 'Rigol DG1022Z', 'B-101', '2023-02-10', 1, '双通道'),
('SG-003', '函数信号发生器', 3, 'Rigol DG1022Z', 'B-101', '2023-02-10', 2, '双通道'),
('SG-004', '函数信号发生器', 3, 'Siglent SDG1032X', 'B-102', '2023-05-20', 1, '30MHz'),
('SG-005', '函数信号发生器', 3, 'Siglent SDG1032X', 'B-102', '2023-05-20', 1, '30MHz'),
('SG-006', '射频信号源', 3, 'Siglent SSG3021X', 'B-103', '2023-08-01', 1, '3.2GHz'),
('SG-007', '射频信号源', 3, 'Siglent SSG3021X', 'B-103', '2023-08-01', 3, '3.2GHz-维修'),
('SG-008', '任意波形发生器', 3, 'Tektronix AFG1022', 'B-104', '2023-09-15', 1, '25MHz'),
('SG-009', '任意波形发生器', 3, 'Tektronix AFG1022', 'B-104', '2023-09-15', 1, '25MHz'),
('SG-010', '任意波形发生器', 3, 'Tektronix AFG1022', 'B-104', '2023-09-15', 2, '25MHz'),
-- 电源类 (31-40)
('PS-001', '直流稳压电源', 4, 'Rigol DP832', 'C-101', '2023-03-01', 1, '三通道'),
('PS-002', '直流稳压电源', 4, 'Rigol DP832', 'C-101', '2023-03-01', 1, '三通道'),
('PS-003', '直流稳压电源', 4, 'Rigol DP832', 'C-101', '2023-03-01', 2, '三通道'),
('PS-004', '直流稳压电源', 4, 'ATTEN APS3005S', 'C-102', '2023-04-10', 1, '单通道'),
('PS-005', '直流稳压电源', 4, 'ATTEN APS3005S', 'C-102', '2023-04-10', 1, '单通道'),
('PS-006', '直流稳压电源', 4, 'ATTEN APS3005S', 'C-102', '2023-04-10', 1, '单通道'),
('PS-007', '可编程电源', 4, 'ITECH IT6720', 'C-103', '2023-07-01', 1, '60V/5A'),
('PS-008', '可编程电源', 4, 'ITECH IT6720', 'C-103', '2023-07-01', 4, '60V/5A-报废'),
('PS-009', '可编程电源', 4, 'ITECH IT6721', 'C-103', '2023-07-01', 1, '60V/8A'),
('PS-010', '可编程电源', 4, 'ITECH IT6721', 'C-103', '2023-07-01', 2, '60V/8A'),
-- 计算机类 (41-50)
('PC-001', '台式计算机', 5, 'Lenovo M950t', 'D-101', '2023-01-10', 1, 'i7/16G/512G'),
('PC-002', '台式计算机', 5, 'Lenovo M950t', 'D-101', '2023-01-10', 1, 'i7/16G/512G'),
('PC-003', '台式计算机', 5, 'Lenovo M950t', 'D-101', '2023-01-10', 2, 'i7/16G/512G'),
('PC-004', '台式计算机', 5, 'Lenovo M950t', 'D-101', '2023-01-10', 1, 'i7/16G/512G'),
('PC-005', '台式计算机', 5, 'Lenovo M950t', 'D-101', '2023-01-10', 3, 'i7/16G/512G'),
('PC-006', '笔记本电脑', 5, 'ThinkPad X1 Carbon', 'D-102', '2023-06-15', 1, 'i7/16G/1T'),
('PC-007', '笔记本电脑', 5, 'ThinkPad X1 Carbon', 'D-102', '2023-06-15', 2, 'i7/16G/1T'),
('PC-008', '笔记本电脑', 5, 'MacBook Pro', 'D-102', '2023-09-20', 1, 'M2/16G/512G'),
('PC-009', '工作站', 5, 'Dell Precision 3660', 'D-103', '2023-11-01', 1, 'i9/32G/1T'),
('PC-010', '工作站', 5, 'Dell Precision 3660', 'D-103', '2023-11-01', 1, 'i9/32G/1T'),
-- 网络设备类 (51-60)
('NW-001', '千兆交换机', 6, 'Huawei S5735S', 'E-101', '2023-04-01', 1, '48口'),
('NW-002', '千兆交换机', 6, 'Huawei S5735S', 'E-101', '2023-04-01', 1, '48口'),
('NW-003', '千兆交换机', 6, 'Huawei S5735S', 'E-101', '2023-04-01', 2, '48口'),
('NW-004', '路由器', 6, 'Cisco ISR 4321', 'E-102', '2023-05-10', 1, '企业级'),
('NW-005', '路由器', 6, 'Cisco ISR 4321', 'E-102', '2023-05-10', 1, '企业级'),
('NW-006', '服务器', 6, 'Dell PowerEdge R750', 'E-103', '2023-07-01', 1, '机架式'),
('NW-007', '服务器', 6, 'Dell PowerEdge R750', 'E-103', '2023-07-01', 2, '机架式'),
('NW-008', '防火墙', 6, 'Huawei USG6300', 'E-104', '2023-08-15', 1, '企业级'),
('NW-009', '无线AP', 6, 'Huawei AP7060DN', 'E-105', '2023-09-01', 1, 'WiFi6'),
('NW-010', '无线AP', 6, 'Huawei AP7060DN', 'E-105', '2023-09-01', 3, 'WiFi6-维修'),
-- 投影设备类 (61-70)
('PJ-001', '投影仪', 7, 'Epson CB-695Wi', 'F-101', '2023-02-15', 1, '互动式'),
('PJ-002', '投影仪', 7, 'Epson CB-695Wi', 'F-101', '2023-02-15', 2, '互动式'),
('PJ-003', '投影仪', 7, 'BenQ MH733', 'F-102', '2023-05-20', 1, '4000流明'),
('PJ-004', '投影仪', 7, 'BenQ MH733', 'F-102', '2023-05-20', 1, '4000流明'),
('PJ-005', '激光投影', 7, 'Sony VPL-P501HZ', 'F-103', '2023-08-10', 1, '激光光源'),
('PJ-006', '激光投影', 7, 'Sony VPL-P501HZ', 'F-103', '2023-08-10', 3, '激光光源-维修'),
('PJ-007', '音响系统', 7, 'JBL KES8120', 'F-201', '2023-09-01', 1, '专业音响'),
('PJ-008', '音响系统', 7, 'JBL KES8120', 'F-201', '2023-09-01', 1, '专业音响'),
('PJ-009', '投影幕布', 7, '红叶120寸', 'F-202', '2023-03-01', 1, '电动'),
('PJ-010', '投影幕布', 7, '红叶120寸', 'F-202', '2023-03-01', 2, '电动'),
-- 测量仪器类 (71-75)
('MI-001', '电子天平', 8, 'Sartorius BSA224S', 'G-101', '2023-04-01', 1, '220g/0.1mg'),
('MI-002', '电子天平', 8, 'Sartorius BSA224S', 'G-101', '2023-04-01', 2, '220g/0.1mg'),
('MI-003', '温湿度计', 8, 'Testo 608-H1', 'G-102', '2023-06-01', 1, '实验室用'),
('MI-004', '噪声计', 8, 'CEM DT-8850', 'G-102', '2023-06-01', 1, '30-130dB'),
('MI-005', 'pH计', 8, 'Mettler Toledo FE28', 'G-103', '2023-07-01', 4, 'pH计-报废'),
-- 通信设备类 (76-78)
('CM-001', '频谱分析仪', 9, 'Rigol RSA3030N', 'H-101', '2023-09-01', 1, '3.2GHz'),
('CM-002', '频谱分析仪', 9, 'Rigol RSA3030N', 'H-101', '2023-09-01', 2, '3.2GHz'),
('CM-003', '网络分析仪', 9, 'Siglent SNA5032A', 'H-102', '2023-10-01', 1, '3.2GHz'),
-- 实验平台类 (79-80)
('EP-001', 'FPGA开发板', 10, 'Xilinx Artix-7', 'I-101', '2023-05-01', 1, 'FPGA开发套件'),
('EP-002', '单片机实验箱', 10, 'STC15实验箱', 'I-102', '2023-06-01', 2, '单片机教学');

-- ============================================
-- 5. 借用申请表 (120条)
-- ============================================
-- 生成最近3个月的借用申请记录
INSERT INTO borrow_apply (user_id, device_id, apply_reason, expected_return_time, status, approve_user_id, approve_time, approve_remark)
SELECT 
    u.id,
    d.id,
    CASE FLOOR(RAND() * 5)
        WHEN 0 THEN '课程实验需要'
        WHEN 1 THEN '毕业设计项目'
        WHEN 2 THEN '科研课题研究'
        WHEN 3 THEN '社团活动使用'
        ELSE '设备测试验证'
    END,
    DATE_ADD(NOW(), INTERVAL FLOOR(RAND() * 30) DAY),
    CASE FLOOR(RAND() * 3)
        WHEN 0 THEN 0
        WHEN 1 THEN 1
        ELSE 2
    END,
    CASE WHEN RAND() > 0.3 THEN 1 ELSE NULL END,
    CASE WHEN RAND() > 0.3 THEN NOW() - INTERVAL FLOOR(RAND() * 10) DAY ELSE NULL END,
    CASE WHEN RAND() > 0.7 THEN '请按时归还并爱护设备' ELSE NULL END
FROM user u, device d
WHERE u.role_id IN (1, 4)  -- 学生和教师
  AND d.status IN (1, 2)    -- 可借或已借出
LIMIT 120;

-- ============================================
-- 6. 借用记录表 (100条)
-- ============================================
INSERT INTO borrow_record (apply_id, device_id, user_id, borrow_time, return_time, status, remark)
SELECT 
    a.id,
    a.device_id,
    a.user_id,
    DATE_ADD(a.apply_time, INTERVAL FLOOR(RAND() * 3) DAY),
    CASE WHEN RAND() > 0.4 THEN DATE_ADD(a.apply_time, INTERVAL FLOOR(RAND() * 20) DAY) ELSE NULL END,
    CASE 
        WHEN RAND() < 0.4 THEN 1
        WHEN RAND() < 0.7 THEN 2
        ELSE 3
    END,
    CONCAT('借用记录', FLOOR(RAND() * 100))
FROM borrow_apply a
WHERE a.status = 1
LIMIT 100;

-- ============================================
-- 7. 报修记录表 (60条)
-- ============================================
INSERT INTO repair_record (device_id, user_id, fault_desc, repair_status, repair_result, report_time, finish_time)
SELECT 
    d.id,
    u.id,
    CASE FLOOR(RAND() * 6)
        WHEN 0 THEN '无法开机，电源指示灯不亮'
        WHEN 1 THEN '显示异常，屏幕闪烁'
        WHEN 2 THEN '按键失灵，无法正常操作'
        WHEN 3 THEN '测量结果不准，偏差较大'
        WHEN 4 THEN '接口损坏，无法连接'
        ELSE '运行卡顿，系统响应慢'
    END,
    FLOOR(RAND() * 3),
    CASE WHEN RAND() > 0.6 THEN '维修完成，设备恢复正常' ELSE NULL END,
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 60) DAY),
    CASE WHEN RAND() > 0.5 THEN DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 30) DAY) ELSE NULL END
FROM device d, user u
WHERE d.status IN (3, 1, 2)  -- 维修中、可借、已借出都可能报修
  AND u.role_id IN (1, 2, 4)  -- 学生、实验员、教师
  AND RAND() > 0.7
LIMIT 60;

-- ============================================
-- 8. 公告表 (35条)
-- ============================================
INSERT INTO notice (title, content, publish_user_id, publish_time, status, is_top)
SELECT 
    CASE FLOOR(RAND() * 6)
        WHEN 0 THEN '实验室开放时间调整通知'
        WHEN 1 THEN '新设备采购到位通知'
        WHEN 2 THEN '设备维护保养公告'
        WHEN 3 THEN '实验课程安排调整'
        WHEN 4 THEN '假期实验室值班安排'
        ELSE '实验室安全培训通知'
    END,
    CONCAT('各位师生：', 
           CASE FLOOR(RAND() * 4)
               WHEN 0 THEN '为保障实验教学正常开展，'
               WHEN 1 THEN '根据学校统一安排，'
               WHEN 2 THEN '为提高设备使用效率，'
               ELSE '为确保实验室安全，'
           END,
           '请各位师生注意相关通知内容，合理安排实验时间。', 
           '详情请咨询实验室管理中心。'),
    CASE WHEN RAND() > 0.7 THEN 1 ELSE 2 END,
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY),
    CASE WHEN RAND() > 0.2 THEN 1 ELSE 0 END,
    CASE WHEN RAND() > 0.9 THEN 1 ELSE 0 END
FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) a,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3) b
LIMIT 35;

-- ============================================
-- 9. 更新设备状态（根据借用记录）
-- ============================================
-- 将正在借用中的设备状态更新为已借出
UPDATE device d 
SET d.status = 2 
WHERE EXISTS (
    SELECT 1 FROM borrow_record br 
    WHERE br.device_id = d.id AND br.status = 1
);

-- 将逾期未还的设备状态更新为已借出（未归还）
UPDATE device d 
SET d.status = 2 
WHERE EXISTS (
    SELECT 1 FROM borrow_record br 
    WHERE br.device_id = d.id AND br.status = 3 AND br.return_time IS NULL
);

-- ============================================
-- 10. 数据统计验证
-- ============================================
SELECT '角色表' AS 表名, COUNT(*) AS 记录数 FROM role
UNION SELECT '用户表', COUNT(*) FROM user
UNION SELECT '设备分类表', COUNT(*) FROM device_category
UNION SELECT '设备表', COUNT(*) FROM device
UNION SELECT '借用申请表', COUNT(*) FROM borrow_apply
UNION SELECT '借用记录表', COUNT(*) FROM borrow_record
UNION SELECT '报修记录表', COUNT(*) FROM repair_record
UNION SELECT '公告表', COUNT(*) FROM notice
ORDER BY 表名;

-- 设备状态统计
SELECT 
    CASE status
        WHEN 1 THEN '可借'
        WHEN 2 THEN '已借出'
        WHEN 3 THEN '维修中'
        WHEN 4 THEN '报废'
        ELSE '未知'
    END AS 设备状态,
    COUNT(*) AS 数量
FROM device
GROUP BY status;

-- 借用记录状态统计
SELECT 
    CASE status
        WHEN 1 THEN '借用中'
        WHEN 2 THEN '已归还'
        WHEN 3 THEN '逾期'
        ELSE '未知'
    END AS 借用状态,
    COUNT(*) AS 数量
FROM borrow_record
GROUP BY status;
