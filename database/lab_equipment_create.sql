CREATE DATABASE IF NOT EXISTS lab_equipment
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

USE lab_equipment;

DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS repair_record;
DROP TABLE IF EXISTS borrow_record;
DROP TABLE IF EXISTS borrow_apply;
DROP TABLE IF EXISTS device;
DROP TABLE IF EXISTS device_category;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;

CREATE TABLE role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码'
) COMMENT='角色表';

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    status TINYINT DEFAULT 1 COMMENT '状态：1正常，0禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (role_id) REFERENCES role(id)
) COMMENT='用户表';

CREATE TABLE device_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    description VARCHAR(255) COMMENT '分类说明',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT='设备分类表';

CREATE TABLE device (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '设备ID',
    device_no VARCHAR(50) NOT NULL UNIQUE COMMENT '设备编号',
    device_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    model VARCHAR(100) COMMENT '型号规格',
    location VARCHAR(100) COMMENT '存放位置',
    purchase_date DATE COMMENT '购入日期',
    status TINYINT DEFAULT 1 COMMENT '状态：1可借，2已借出，3维修中，4报废',
    description VARCHAR(255) COMMENT '设备说明',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES device_category(id)
) COMMENT='设备表';

CREATE TABLE borrow_apply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '申请ID',
    user_id BIGINT NOT NULL COMMENT '申请人ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    apply_reason VARCHAR(255) COMMENT '借用原因',
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    expected_return_time DATETIME COMMENT '预计归还时间',
    status TINYINT DEFAULT 0 COMMENT '状态：0待审批，1通过，2拒绝',
    approve_user_id BIGINT COMMENT '审批人ID',
    approve_time DATETIME COMMENT '审批时间',
    approve_remark VARCHAR(255) COMMENT '审批备注',
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (device_id) REFERENCES device(id)
) COMMENT='借用申请表';

CREATE TABLE borrow_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '借还记录ID',
    apply_id BIGINT NOT NULL COMMENT '申请ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    user_id BIGINT NOT NULL COMMENT '借用人ID',
    borrow_time DATETIME COMMENT '借出时间',
    return_time DATETIME COMMENT '归还时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1借用中，2已归还，3逾期',
    remark VARCHAR(255) COMMENT '备注',
    FOREIGN KEY (apply_id) REFERENCES borrow_apply(id),
    FOREIGN KEY (device_id) REFERENCES device(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
) COMMENT='借还记录表';

CREATE TABLE repair_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '报修ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    user_id BIGINT NOT NULL COMMENT '报修人ID',
    fault_desc VARCHAR(255) NOT NULL COMMENT '故障描述',
    repair_status TINYINT DEFAULT 0 COMMENT '状态：0待处理，1维修中，2已完成',
    repair_result VARCHAR(255) COMMENT '维修结果',
    report_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '报修时间',
    finish_time DATETIME COMMENT '完成时间',
    FOREIGN KEY (device_id) REFERENCES device(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
) COMMENT='报修记录表';

CREATE TABLE notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '公告ID',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    publish_user_id BIGINT COMMENT '发布人ID',
    publish_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1发布，0隐藏',
    FOREIGN KEY (publish_user_id) REFERENCES user(id)
) COMMENT='公告表';

INSERT INTO role(role_name, role_code) VALUES
('学生', 'STUDENT'),
('实验员', 'LAB_ADMIN'),
('管理员', 'ADMIN');

INSERT INTO user(username, password, real_name, phone, email, role_id) VALUES
('admin', '123456', '系统管理员', '13800000000', 'admin@test.com', 3),
('lab', '123456', '实验员', '13800000001', 'lab@test.com', 2),
('student', '123456', '学生用户', '13800000002', 'student@test.com', 1);

INSERT INTO device_category(category_name, description) VALUES
('电子测量设备', '示波器、万用表、信号发生器等'),
('计算机设备', '台式机、笔记本、服务器等'),
('物理实验设备', '力学、电学、光学实验设备'),
('化学实验设备', '烧杯、试管、电子天平等');

INSERT INTO device(device_no, device_name, category_id, model, location, purchase_date, status, description) VALUES
('DEV2026001', '数字示波器', 1, 'DS1102E', 'A101实验室', '2024-09-01', 1, '用于电子电路波形测试'),
('DEV2026002', '数字万用表', 1, 'UT61E+', 'A101实验室', '2024-09-10', 1, '用于电压、电流、电阻测量'),
('DEV2026003', '实验室台式电脑', 2, 'Lenovo M460', 'B203实验室', '2023-06-15', 1, '实验课程使用'),
('DEV2026004', '电子天平', 4, 'FA2004', 'C301实验室', '2023-10-20', 3, '当前处于维修中');
