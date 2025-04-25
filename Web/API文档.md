# 接口文档

## 1. 用户相关接口

### 1.1 用户注册
- **请求体 (RegisterDto)**
  - `role`: 用户角色 (必填)
    - 可选值: USER(普通用户), MERCHANT(商户), ADMIN(管理员)
  - `username`: 用户名 (必填)
    - 格式: 字母、数字、下划线
    - 长度: 3-10位
  - `phone`: 手机号 (必填)
    - 格式: 11位中国大陆手机号
  - `email`: 邮箱 (必填)
    - 格式: 标准邮箱地址
  - `password`: 密码 (必填)
    - 格式: 必须包含字母和数字或特殊符号
    - 长度: 6-32位

### 1.2 用户登录
- **请求体 (LoginDto)**
  - `username`: 用户名 (必填)
  - `password`: 密码 (必填)

### 1.3 用户信息
- **响应体 (UserInfoDto)**
  - `role`: 用户角色
  - `name`: 用户名
  - `phone`: 手机号
  - `email`: 邮箱

## 2. 商店相关接口

### 2.1 商店注册
- **请求体 (StoreRegistrationDto)**
  - `shopName`: 商店名称 (必填)
    - 长度: 2-20字符
  - `description`: 商店简介 (必填)
    - 长度: 最多500字符
  - `address`: 备案地址 (必填)
    - 长度: 最多100字符
  - `capital`: 注册资金 (必填)
    - 最小值: 1000
  - `idNumber`: 店主身份证号 (必填)
    - 格式: 18位中国大陆身份证号
  - `categories`: 商品类别 (必填)
    - 数量: 1-5个
  - `registrationDate`: 注册时间 (必填)
    - 格式: yyyy-MM-dd

## 3. 实体类

### 3.1 用户实体 (User)
- `id`: 用户ID (自动生成)
- `username`: 用户名
  - 唯一性: 必须唯一
  - 长度: 最多10字符
- `password`: 密码
- `email`: 邮箱
- `phone`: 手机号
  - 长度: 11位
- `role`: 用户角色
  - 可选值: ADMIN, USER, MERCHANT

### 3.2 商店实体 (Store)
- `id`: 商店ID (自动生成)
- `name`: 商店名称
  - 长度: 最多20字符
- `description`: 商店简介
  - 长度: 最多500字符
- `address`: 备案地址
  - 长度: 最多100字符
- `capital`: 注册资金
- `registerDate`: 注册时间
- `ownerIdCard`: 店主身份证号
  - 长度: 18位
- `categories`: 商品类别
- `status`: 商店状态
  - 可选值: PENDING(待审核), APPROVED(已审核通过), REJECTED(已拒绝)
- `owner`: 店主用户ID (关联User实体)


## 文档及调试查看
访问http://localhost:8000/doc.html