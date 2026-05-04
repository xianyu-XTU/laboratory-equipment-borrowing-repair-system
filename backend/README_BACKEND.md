# 校园实验室设备管理系统后端

## 技术栈

- Spring Boot 3
- MyBatis-Plus
- MySQL
- Lombok
- Maven

## 运行步骤

1. 先在 MySQL 中执行仓库根目录下的 `database/lab_equipment.sql`
2. 修改 `src/main/resources/application.yml` 中的数据库用户名和密码
3. 在当前 `backend` 目录运行：

```bash
mvn spring-boot:run
```

## 测试账号

- 管理员：admin / 123456
- 实验员：lab / 123456
- 学生：student / 123456

## 常用接口

登录：

```http
POST /api/auth/login
Content-Type: application/json

{
  "username":"admin",
  "password":"123456"
}
```

登录成功后，把返回的 token 放到请求头：

```http
Authorization: Bearer 你的token
```

设备列表：

```http
GET /api/devices?page=1&size=10
```

借用申请：

```http
POST /api/borrow/apply
Authorization: Bearer 学生token
Content-Type: application/json

{
  "deviceId":1,
  "applyReason":"课程实验使用",
  "expectedReturnTime":"2026-05-20T18:00:00"
}
```

审批申请：

```http
POST /api/borrow/approve
Authorization: Bearer 实验员或管理员token
Content-Type: application/json

{
  "applyId":1,
  "status":1,
  "approveRemark":"同意借用"
}
```

归还设备：

```http
POST /api/borrow/return
Authorization: Bearer 实验员或管理员token
Content-Type: application/json

{
  "recordId":1,
  "remark":"设备完好归还"
}
```

报修：

```http
POST /api/repairs/report
Authorization: Bearer 用户token
Content-Type: application/json

{
  "deviceId":1,
  "faultDesc":"无法正常开机"
}
```

统计：

```http
GET /api/stats/overview
```
