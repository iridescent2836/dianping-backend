
# backend

后端代码

## 项目环境
- Java version: 17
- Spring Boot version: 3.2.3
- Maven version: 3.9.8
- MySQL version: 8.0.36 (MySQL Community Server - GPL)
- 本项目是一个基于Spring Boot的后端应用，使用MySQL作为数据库，采用JPA进行数据持久化。
- 运行在7892端口

## 快速开始

### 环境要求
- OS: Windows 11 (x86_64)
- MySQL 8.0.36+
- JDK 17+
- Maven 3.9.8+
- Default locale: zh_CN
- Platform encoding: UTF-8

### 开发工具配置
1. JDK配置
   - 安装 Java 17
   - 设置 JAVA_HOME 环境变量

2. Maven配置
   - 安装 Maven 3.9.8
   - Maven home: D:\Maven\apache-maven-3.9.8

### 数据库配置
MySQL Community Server - GPL
[待补充具体配置信息]

## API文档
Swagger UI访问地址: http://localhost:7892/swagger-ui/index.html

更详细的 API 文档请访问：http://localhost:8000/doc.html

## 项目结构
[待补充项目结构]

## 构建和运行
[待补充构建步骤]

## 主要功能
[待补充功能描述]

## 类的设计
### 类的定义（类名、属性、主要方法）
#### Account

| 属性名        | 类型             | 数据库列                | 描述                       |
| ------------- | ---------------- | ----------------------- | -------------------------- |
| `id`          | `Long`           | `id` (PK, auto-inc)     | 账户主键，自增             |
| `userId`      | `Long`           | `user_id` (FK)          | 关联的用户 ID              |
| `balance`     | `BigDecimal`     | `balance`               | 账户余额，精度10、标度2     |
| `status`      | `Integer`        | `status`                | 账户状态（0=正常，1=冻结） |
| `createTime`  | `LocalDateTime`  | `create_time`           | 创建时间                   |
| `updateTime`  | `LocalDateTime`  | `update_time`           | 最后更新时间               |

**主要方法**

| 方法名               | 返回类型    | 参数                             | 描述               |
| -------------------- | ----------- | -------------------------------- | ------------------ |
| `createAccount`      | `Account`   | `User user`                      | 新建账户           |
| `recharge`           | `Account`   | `Long userId, BigDecimal amount` | 按用户ID充值       |
| `getAccount`         | `Account`   | `Long userId`                    | 按用户ID查询账户   |
| `getAccountByUsername` | `Account` | `String username`                | 按用户名查询账户   |
| `recharge`           | `Account`   | `String username, BigDecimal amount` | 按用户名充值   |

---

#### Product

| 属性名        | 类型             | 数据库列                    | 描述                                           |
| ------------- | ---------------- | --------------------------- | ---------------------------------------------- |
| `id`          | `Long`           | `id` (PK, auto-inc)         | 商品主键，自增                                 |
| `storeId`     | `Long`           | `store_id` (FK)             | 所属商店ID                                     |
| `name`        | `String`         | `name`                      | 商品名称                                       |
| `description` | `String`         | `description`               | 商品描述                                       |
| `price`       | `BigDecimal`     | `price`                     | 商品价格，精度10，标度2                        |
| `imageUrl`    | `String`         | `image_url`                 | 商品图片 URL                                   |
| `status`      | `Integer`        | `status`                    | 商品状态（0=待审核，1=已上架，2=已下架，3=未通过） |
| `createTime`  | `LocalDateTime`  | `create_time`               | 创建时间                                       |
| `updateTime`  | `LocalDateTime`  | `update_time`               | 最后更新时间                                   |

**主要方法**

| 方法名                     | 返回类型                        | 参数                                                        | 描述                         |
| -------------------------- | ------------------------------- | ----------------------------------------------------------- | ---------------------------- |
| `createProduct`            | `Product`                       | `Long storeId, ProductDto productDto, User merchant`        | 创建商品并提交上架申请       |
| `updateProduct`            | `Product`                       | `Long productId, ProductDto productDto, User merchant`      | 修改商品并提交修改申请       |
| `deleteProduct`            | `void`                          | `Long productId, User merchant`                             | 下架商品                     |
| `reviewProduct`            | `void`                          | `Long applicationId, boolean approved, String comment`      | 审核商品申请                 |
| `getStoreProducts`         | `List<Product>`                 | `Long storeId`                                              | 查询某店铺下所有商品         |
| `getStoreProductsByStatus` | `List<Product>`                 | `Long storeId, Integer status`                              | 按状态查询店铺商品           |
| `getPendingApplications`   | `List<ProductApplication>`      | —                                                           | 查询所有待审核申请           |
| `getMerchantApplications`  | `List<ProductApplication>`      | `Long merchantId`                                           | 查询某商户的申请记录         |

---

#### ProductApplication

| 属性名       | 类型        | 数据库列                  | 描述                             |
| ------------ | ----------- | ------------------------- | -------------------------------- |
| `id`         | `Long`      | `id` (PK, auto-inc)       | 申请记录主键，自增               |
| `product`    | `Product`   | `product_id` (FK)         | 关联的商品                       |
| `merchant`   | `User`      | `merchant_id` (FK)        | 提交申请的商户                   |
| `type`       | `Integer`   | `type`                    | 申请类型（1=上架，2=修改）       |
| `status`     | `Integer`   | `status`                  | 审核状态（0=待审核，1=通过，2=拒绝） |
| `comment`    | `String`    | `comment`                 | 审核意见                         |
| `createTime` | `Long`      | `create_time`             | 申请创建时间（时间戳）           |
| `reviewTime` | `Long`      | `review_time`             | 审核时间（时间戳）               |

**主要方法**

| 方法名                   | 返回类型                     | 参数                     | 描述               |
| ------------------------ | ---------------------------- | ------------------------ | ------------------ |
| `getPendingApplications` | `List<ProductApplication>`   | —                        | 查询所有待审核申请 |
| `getMerchantApplications`| `List<ProductApplication>`   | `Long merchantId`        | 查询某商户的申请   |

---

#### Store

| 属性名           | 类型                  | 数据库列                        | 描述                                 |
| ---------------- | --------------------- | ------------------------------- | ------------------------------------ |
| `id`             | `Long`                | `store_id` (PK, auto-inc)       | 商店主键，自增                       |
| `name`           | `String`              | `store_name`                    | 商店名称                             |
| `description`    | `String`              | `store_description`             | 商店描述                             |
| `address`        | `String`              | `store_address`                 | 商店地址                             |
| `phone`          | `String`              | `store_phone`                   | 联系电话                             |
| `capital`        | `Double`              | `store_capital`                 | 注册资金                             |
| `registerDate`   | `LocalDateTime`       | `store_register_date`           | 注册时间                             |
| `ownerIdCard`    | `String`              | `owner_id_card`                 | 店主身份证号                         |
| `categories`     | `List<String>`        | `store_categories.category` (FK)| 商品类别                             |
| `status`         | `StoreStatus` (ENUM)  | `status`                        | 商店状态（PENDING/APPROVED/REJECTED） |
| `reviewComment`  | `String`              | `review_comment`                | 审核意见                             |
| `ownerId`        | `Long`                | `user_id` (FK)                  | 店主用户ID                           |
| `logoUrl`        | `String`              | `logo_url`                      | 商店 Logo URL                        |
| `createTime`     | `LocalDateTime`       | `create_time`                   | 创建时间                             |
| `updateTime`     | `LocalDateTime`       | `update_time`                   | 最后更新时间                         |

**主要方法**

| 方法名                    | 返回类型                            | 参数                                                   | 描述                     |
| ------------------------- | ----------------------------------- | ------------------------------------------------------ | ------------------------ |
| `createStore`             | `Store`                             | `StoreRegistrationDto storeDto, User user`             | 商户注册新店             |
| `updateStore`             | `Store`                             | `Long id, StoreRegistrationDto storeDto, User user`    | 更新店铺信息             |
| `deleteStore`             | `void`                              | `Long storeId, Long merchantId`                        | 删除店铺                 |
| `reviewStore`             | `Store`                             | `Long storeId, boolean approved, String comment`       | 审核店铺注册申请         |
| `getStoresByUserRole`     | `List<Store>`                       | `User user`                                            | 根据用户角色查询店铺     |
| `getStoreById`            | `Store`                             | `Long id`                                              | 按ID查询店铺             |
| `getMerchantStores`       | `List<Store>`                       | `Long merchantId`                                      | 查询某商户的店铺         |
| `getStoresByStatus`       | `List<Store>`                       | `Store.StoreStatus status`                             | 按审核状态查询店铺       |
| `getAllStores`            | `List<Store>`                       | —                                                      | 查询所有店铺             |
| `getStore`                | `Store`                             | `Long storeId`                                         | 按ID查询店铺             |
| `getAllStoresWithOwners`  | `List<StoreWithOwnerDTO>`           | —                                                      | 查询店铺及其店主信息列表 |

---

#### User

| 属性名       | 类型                  | 数据库列                          | 描述                                       |
| ------------ | --------------------- | --------------------------------- | ------------------------------------------ |
| `id`         | `Long`                | `user_id` (PK, auto-inc)          | 用户主键，自增                             |
| `username`   | `String`              | `user_name` (unique, not null)    | 用户名                                     |
| `password`   | `String`              | `user_password` (not null)        | 用户密码（已加密存储）                     |
| `email`      | `String`              | `user_email` (not null)           | 用户邮箱                                   |
| `phone`      | `String`              | `user_phone` (not null)           | 用户手机号                                 |
| `role`       | `UserRole` (ENUM)     | `user_role` (not null)            | 用户角色（ADMIN/USER/MERCHANT）            |
| `accountId`  | `Long`                | `account_id`                      | 关联的账户ID                               |

**主要方法**

| 方法名                   | 返回类型              | 参数                                  | 描述                                     |
| ------------------------ | --------------------- | ------------------------------------- | ---------------------------------------- |
| `findByUsername`         | `User`                | `String username`                     | 根据用户名查找用户                       |
| `saveUser`               | `User`                | `User user`                           | 保存用户信息                             |
| `getCurrentUser`         | `User`                | —                                     | 获取当前登录用户                         |
| `loadUserByUsername`     | `UserDetails`         | `String username`                     | Spring Security加载用户详情              |
| `register`               | `User`                | `RegisterDto registerDto`             | 用户注册                                 |
| `login`                  | `User`                | `LoginDto loginDto`                   | 用户登录                                 |
| `getUser`                | `User`                | `Long userId`                         | 按ID查询用户                             |
| `updateUser`             | `User`                | `Long userId, UserUpdateDto userDto`  | 更新用户信息                             |
| `deleteUser`             | `void`                | `Long userId`                         | 删除用户                                 |
| `getUserById`            | `User`                | `Long userId`                         | 按ID查询用户                             |
| `getUserByUsername`      | `User`                | `String username`                     | 按用户名查询用户                         |
| `existsByUsername`       | `boolean`             | `String username`                     | 检查用户名是否存在                       |
| `existsByEmail`          | `boolean`             | `String email`                        | 检查邮箱是否存在                         |
| `existsByPhone`          | `boolean`             | `String phone`                        | 检查手机号是否存在                       |
| `save`                   | `User`                | `User user`                           | 保存或更新用户                           |
| `getAllUsers`            | `List<User>`          | —                                     | 查询所有用户                             |


### 类之间的关系（继承、关联、聚合、组合等）
- **`Account` — `User`（一对一单向关联）**  
  `Account.userId` 外键指向 `User.id`，表示“每个账户属于一个用户”。

- **`Product` — `Store`（多对一单向关联）**  
  `Product.storeId` 外键指向 `Store.id`，表示“一个商店下可以有多个商品”。

- **`ProductApplication` — `Product`（多对一单向关联）**  
  `ProductApplication.product` 通过 `@ManyToOne` 关联到 `Product`，表示“一个商品可以对应多条申请记录”。

- **`ProductApplication` — `User (merchant)`（多对一单向关联）**  
  `ProductApplication.merchant` 通过 `@ManyToOne` 关联到 `User`，表示“一个商户可以提交多条商品申请”。

- **`Store` — `User (owner)`（多对一单向关联）**  
  `Store.ownerId` 外键指向 `User.id`，表示“一个用户（商户）可以拥有多个店铺”。

#### 继承 / 实现  
- **`User → UserDetails`**  
  `User` 实现了 Spring Security 的 `UserDetails` 接口，获得了认证/授权所需的方法签名。

#### 关联 (Association)  
- **`Account – User`**  
  `Account.userId` 外键关联到 `User.id`，表示“每个账户属于一个用户”。  
- **`Product – Store`**  
  `Product.storeId` 外键关联到 `Store.id`，表示“一个商店下可拥有多件商品”。  
- **`ProductApplication → Product`**  
  `@ManyToOne` 关联到 `Product`，表示“多条申请记录可对应同一商品”。  
- **`ProductApplication → User(merchant)`**  
  `@ManyToOne` 关联到 `User`，表示“多条申请记录可由同一商户提交”。  
- **`Store – User(owner)`**  
  `Store.ownerId` 外键关联到 `User.id`，表示“一个用户可拥有多个店铺”。

#### 聚合 (Aggregation)  
- **`Store — categories`**  
  `Store` 聚合了一个 `List<String> categories`，表示“店铺可以有多个分类，但分类独立于店铺生命周期”。  
- **`Store — products (Transient)`**  
  虽然产品列表为 `@Transient`，但逻辑上 `Store` 聚合了其下属的 `Product` 对象集合。

#### 组合 (Composition)  
- **`ProductApplication` 组合了 `Product` 和 `User`**  
  申请记录的生命周期紧密依赖于所关联的商品和商户，不可脱离它们单独存在。  
- **`Store` 组合了 `StoreStatus` 枚举**  
  店铺状态在 `Store` 对象创建时即生成，不可独立于 `Store` 存在。
