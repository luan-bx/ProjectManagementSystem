# PMS
中小企业项目流程管理系统

# 需求和方案设计

​	整体系统主要包括用户登录/注册、项目新建、项目查询

## 用户登录/注册

​	公司人员继续分为：高级管理员、项目经理（管理者）、机械设计工程师、电气设计工程师、软件设计工程师、商务人员（采购）、入库管理等。

### 用户注册

新用户第一次登录系统，跳转用户注册页面。

注册方式可以通过“用户名-密码”或第三方登录方式注册（微信登录等）。

- 以“用户名-密码”注册

  用户输入用户名、密码、邮箱、手机号等个人信息进行注册。

- 以第三方方式注册

  跳转第三方登录，并绑定手机号。

注册需填写公司人员相关的信息，工号、部门、岗位等信息。

部门、岗位提供可自定义选项

不同岗位权限不同：

- 高级管理员，具有查看所有项目组的项目、增添人员权限、增添部门、岗位等功能
- 项目经理，具有查看当前项目组的项目、**增加外协人员权限**等功能
- 各级岗位，具有相应职责权限，各岗位权限不交叉。

注册成功后发送邮件、手机信息等提醒注册成功，并跳转系统首页。若注册失败则返回注册页面，并提示相关错误。

### 用户登录

- 以“用户名-密码”方式登录

  若用户名不存在，提示注册；否则若登录失败，提示密码错误，登录成功直接进入系统首页

- 以第三方方式登录

  跳转第三方验证登录，授权成功后进入系统首页。

## 项目新建

​	在项目新建过程中，包含很多阶段，每个阶段需要单独建数据库表进行存储，最后通过一张项目总表进行流程关联。

### 项目流程状态

在项目新建过程中，项目状态流程大致为：

- 项目请求中

- 项目评审中

**若项目审核通过后**，状态依次为：

- 项目签订中

- 项目设计中 

- 项目生产中。可继续分为“项目采购中”，“项目开发中”等过程

- 项目装配中

**若项目审核不通过，**状态显示为：

- 项目审核不通过

**项目进行过程中，如果有发生异常的部分再进行对应状态的跳转，装配异常-》装配过程出现问题-〉设计问题，设计更新图图纸-》关键件问题-〉更新BOM**

`pro_status`表状态包含上述状态。

### 项目请求中

​	收到客户SOW邮件后，公司相关人员填写项目相关信息，并上传SOW文件。此时项目状态为“项目请求中”。

### 项目评审中

- 商务人员收到系统通知后，可以登录系统进行查看项目请求信息。此时项目状态为“项目评审中”。

- 商务人员联系公司相关人员进行项目报价和技术方案设计。若项目审核不通过，商务人员在系统给出审核不通过原因，此时项目状态为“项目审核不通过”。若项目审核通过，继续下面的签订等流程。

### 项目签订中

​	审核通过后，项目状态变为“项目签订中”。
​	公司相关人员进行PO和相关合同事宜处理，并在系统上填写相关数据和上传文件，包括：

- 输入PO号
- 合同号
- 公司内部项目编号
- 客户代码
- 产品类别(不一定需要)
- 生产线编号(不一定需要)
- 设备工位号(不一定需要)
- 对应的客户工程师
- 交货日期
- 付款条款（三到四次，30% 60% 10% 或者30-30-30-10)，本地磁盘url，一个目录，里面存放一个PO的所有付款付款条款

**额外功能：系统自动提醒交付，比如提前一个月，提醒财务收款，开票，提醒信息一直在，直到提醒条件消除。**

### 项目设计中

​	公司在项目签订后，将项目状态变为“项目设计中”。机械、电气、软件三种设计师同时进行项目方案的设计。

​	每个工程师在设计时，需要在页面填写相关信息（项目设计状态表内容），设计完成后在页面填写项目相关内容信息和项目文件上传。

- 项目设计表
  - PO号
  - 合同号
  - 内部项目编号
  - 机械设计工程师
  - 电气设计工程师
  - 软件设计工程师
  - 机械设计开始日期
  - 机械设计完成日期
  - 电气设计开始日期
  - 电气设计完成日期
  - 软件开始日期
  - 软件完成日期
  - 备注
- 项目设计关联表
  - 项目设计表id
  - 机械设计表id
  - 电气设计表id
  - 软件设计表id
- 机械设计表
  - BOM表
  - 机械设计3D图纸
  - 2D图纸
  - 气路图
  - 装配组件图
  - 型材框架图
  - 易损件清单
  - 易损件图纸
- 电气设计表
  - BOM表
  - 电气图纸
  - 备件清单
- 软件设计表
  - PLC程序

### 项目生产中

项目设计完成后，将项目状态变为“项目生产中”。

分为两部分人员，采购和机械零件加工人员分别在系统填写信息。（这里可以通过**扫描二维码**填写信息）

另外可能需要外协，单独设计表。

- 项目生产表
  - PO号
  - 合同号
  - 内部项目编号
  - 采购表id
  - 机械零件加工表id
  - 外协表id

- 采购表

  - 名称
  - 型号
  - 数量
  - 供应商
  - 类别
  - 价格
  - 下单日期
  - 货期
  - 实际到货日期
  - 入库日期
  - 库位号（由库管人员更新）

- 机械零件加工表

  - 名称
  - 图号
  - 加工开始日期
  - 实际加工完成日期
  - 价格
  - 数量

- 外协表

  - 外协的项目id
  - 外协公司名称
  - 外协公司对接人员
  - 外协内容简单描述
  - 外协文件存放路径

### 项目装配中

项目生产完成后，将项目状态变为“项目装配中”。

相关人员需要在系统填写下列信息：

- PO号
- 合同号
- 内部项目编号
- 机械装配人员
- 机械装配开始日期
- 电气装配人员
- 电气装配开始日期
- 机械完成日期
- 电气完成日期
- 软件调试人员
- 软件开始调试日期
- 软件结束调试日期
- 检验和测试人员
- 检验开始日期
- 检验完成日期 

# 数据库设计

​	系统主要分为用户数据库表和项目数据库表两大类。相应的用户服务包含客户和公司两部分，项目服务包含项目相关表，设备编号表等部分。

## 用户数据库

​	根据客户“用户名-密码”和第三方方式（这里以微信登录为例）登录的情况进行设计，并且添加权限表等辅助表。

### post表-岗位表

- 建数据库表

  ```mysql
  CREATE TABLE `post`(
  	`id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  	`name` VARCHAR(20) NOT NULL COMMENT '岗位名称',
  	`number` VARCHAR(10) COMMENT '岗位代号',
  	`created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  	PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name`(`name`) USING BTREE,
    UNIQUE INDEX `number`(`number`) USING BTREE
  )ENGINE = InnoDB COMMENT = '岗位表';
  ```

- 测试用例插入

  ```mysql
  INSERT INTO `post` (`name`, `number`) VALUES('高级管理员', 'p1');
  INSERT INTO `post` (`name`, `number`) VALUES('项目经理', 'p2');
  INSERT INTO `post` (`name`, `number`) VALUES('机械设计工程师', 'p3');
  INSERT INTO `post` (`name`, `number`) VALUES('电气设计工程师', 'p4');
  INSERT INTO `post` (`name`, `number`) VALUES('软件设计工程师', 'p5');
  INSERT INTO `post` (`name`, `number`) VALUES('商务人员(采购)', 'p6');
  INSERT INTO `post` (`name`, `number`) VALUES('入库管理', 'p7');
  ```

  ![image-20211229122626326](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20211229122626326.png)

- JavaBean实体类

  ```java
  public class PostEntity {
  	private int id;
  	private String name;
  	private String number;
  	private Timestamp created;
  	private Timestamp updated;
  }
  
  ```

- Mapper映射接口

  ```java
  public interface PostMapping {
  
  	/*
  	 * 插入一个岗位记录
  	 */
  	@Insert("INSERT INTO `post` (`name`, `number`) VALUES(#{name}, #{number});")
  	void insert(PostEntity postEntity);
  }
  ```

### department表-部门表

- 建数据库表

  ```mysql
  CREATE TABLE `department`(
  	`id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  	`name` VARCHAR(20) NOT NULL COMMENT '部门名称',
  	`number` VARCHAR(10) COMMENT '部门代号',
  	`created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  	PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name`(`name`) USING BTREE,
    UNIQUE INDEX `number`(`number`) USING BTREE
  )ENGINE = InnoDB COMMENT = '部门表';
  ```

- 测试用例插入

  ```mysql
  INSERT INTO `department` (`name`, `number`) VALUES('部门1', 'n01');
  INSERT INTO `department` (`name`, `number`) VALUES('部门2', 'n02');
  INSERT INTO `department` (`name`, `number`) VALUES('部门3', 'n03');
  ```

  ![image-20211229122752115](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20211229122752115.png)

- JavaBean实体类

  ```java
  public class DepartmentEntity {
  	private int id;
  	private String name;
  	private String number;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface DepartmentMapping {
  
  	/*
  	 * 插入一个部门记录
  	 */
  	@Insert("INSERT INTO `department` (`name`, `number`) VALUES(#{name}, #{number});")
  	void insert(DepartmentEntity departmentEntity);
  }
  ```

### 用户表

​	这里写了两个版本，**当前版本为方案二**。

- 第一个是默认高级管理员、项目经理等岗位和权限挂钩
- 第二个是将岗位和权限分开建表

#### 方案1

​	保存“用户名-密码“方式进行注册的信息，包含的字段和参数如下：

```mysql
CREATE TABLE `user`(
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
	`openId` varchar(100) NOT NULL COMMENT '用户openid',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
	`gender` int(5) NOT NULL COMMENT '性别，0 未知，1 女性，2 男性',
	`number` VARCHAR(20) NOT NULL COMMENT '工号',
	`post_id` int(10) not null COMMENT '岗位id',
	`department_id` int(10) not null COMMENT '部门id',
	`wx_id` int(10) COMMENT '关联的微信用户表id',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
	UNIQUE INDEX `openId`(`openId`) USING BTREE,
	UNIQUE INDEX `number`(`number`) USING BTREE
) ENGINE = InnoDB COMMENT = '用户表';
```

- 测试用例插入

  ```mysql
  INSERT INTO `user`(`user_name`, `password`, `openId`, `phone`, `email`, `gender`, `number`, `post_id`, `department_id`)
  			 VALUES('高级管理员1', '123456', 'dddddd1', '15333333333', 'test1@email.com', 2, 'c2101', 1, 1);
  INSERT INTO `user`(`user_name`, `password`, `openId`, `phone`, `email`, `gender`, `number`, `post_id`, `department_id`)
  			 VALUES('项目经理1', '123456', 'dddddd2', '15433333333', 'test2@email.com', 1, 'c2102', 2, 1);
  INSERT INTO `user`(`user_name`, `password`, `openId`, `phone`, `email`, `gender`, `number`, `post_id`, `department_id`)
  			 VALUES('机械设计工程师1', '123456', 'dddddd3', '15533333333', 'test3@email.com', 2, 'c2103', 3, 1);
  ```
  
  ![image-20211229123043455](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20211229123043455.png)
  
  联合`post`表和`department`表查询：
  
  ![image-20211229123342961](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20211229123342961.png)
  
- Javabean实体类

  ```java
  @Setter
  @Getter
  public class UserEntity {
  	private int id;
  	private String userName;
  	private String password;
  	private String openId;
  	private String phone;
  	private String email;
  	private int gender;
  	private String number;
  	private int postId;
  	private String postName;
  	private int departmentId;
  	private String departmentName;
  	private int wxId;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射层接口

  ```java
  public interface UserMapping {
  
  	/*
  	 * 插入一个客户用户记录
  	 */
  	@Insert(...)
  	void insert(UserEntity customerUserEntity);
  	
  	/*
  	 * 通过客户用户名查询用户
  	 */
  	@Select(...)
  	UserEntity queryCuByUserName(@Param("userName") String userName);
  	
  	/*
  	 * 通过客户openId查询用户
  	 */
  	@Select(...)
  	UserEntity queryCuByOpenId(@Param("openId") String openId);
  	
  	/*
  	 * 通过客户手机号查询用户
  	 */
  	@Select(...)
  	UserEntity queryCuByPhone(@Param("phone") String phone);
  	
  	/*
  	 * 通过客户邮箱查询用户
  	 */
  	@Select(...)
  	UserEntity queryCuByEmail(@Param("email") String email);
  }
  ```

#### 方案2

​	多添加权限表sys_permission和中间表sys_users_permission。

##### 权限表

```mysql
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB COMMENT='权限表';
```

- 测试用例插入

  ```mysql
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、改动，用户');
  INSERT INTO `sys_permission` (`description`) VALUES ('查看用户');
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、改动岗位');
  INSERT INTO `sys_permission` (`description`) VALUES ('查看岗位');
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、改动项目组');
  INSERT INTO `sys_permission` (`description`) VALUES ('查看项目组');
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、查看、改动外协');
  ```

##### 用户-权限关联表

```mysql
CREATE TABLE `sys_users_permission` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`user_id`,`permission_id`) USING BTREE,
	KEY `user_id` (`user_id`) USING BTREE,
  KEY `permission_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB COMMENT='用户-权限表';
```

- 测试用例

  ```mysql
  INSERT INTO `sys_users_permission` VALUES(2, 2);
  INSERT INTO `sys_users_permission` VALUES(2, 4);
  INSERT INTO `sys_users_permission` VALUES(2, 6);
  INSERT INTO `sys_users_permission` VALUES(2, 7);
  ```

<img src="https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20211230171133186.png" alt="image-20211230171133186" style="zoom:67%;" />

##### user表

```mysql
CREATE TABLE `user`(
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `user_name` varchar(50) COMMENT '用户名', # 考虑到微信方式登录不设置这些，暂时不加not null，但是需要在前端用js判断，注册的时候不能为null
  `password` varchar(32) COMMENT '密码，加密存储',
	`openId` varchar(100) COMMENT '用户openid',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `email` varchar(50) NOT NULL COMMENT '邮箱', # 考虑到后面通知采用邮件的方式进行，暂时先用not NULL
	`gender` int(5) NOT NULL COMMENT '性别，0 未知，1 女性，2 男性',
	`icon` varchar(500) DEFAULT NULL COMMENT '头像地址',
	`number` VARCHAR(20) NOT NULL COMMENT '工号',
	`is_admin` tinyint(1) NOT NULL COMMENT '是否是高级管理员',
	`post_id` int(10) not null COMMENT '岗位id',
	`department_id` int(10) not null COMMENT '部门id',
	`wx_id` int(10) COMMENT '关联的微信用户表id',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
	UNIQUE INDEX `openId`(`openId`) USING BTREE,
	UNIQUE INDEX `number`(`number`) USING BTREE
) ENGINE = InnoDB COMMENT = '用户表';
```

测试用例同方案一。联合5张表查询语句如下：

```mysql
SELECT
	m.*,
	n.description
FROM
	(SELECT
		u.id,
		u.user_name,
		u.password,
		u.openId,
		u.phone,
		u.email,
		u.gender,
		u.icon,
		u.number,
		u.is_admin,
		p.name as 'postName',
		d.name as 'departmentName',
		u.wx_id,
		u.created,
		u.updated
	from
		`user` u,
		department d,
		post p
	where
		u.post_id = p.id and
		u.department_id = d.id
	) m
left JOIN
	(
	SELECT
	a.user_id,
	b.description
	FROM
		`sys_users_permission` a,
		`sys_permission` b
	WHERE
		a.permission_id = b.id
	) n
	ON n.user_id = m.id
WHERE m.user_name = '项目经理1';
```

![image-20211230171407741](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20211230171407741.png)

### wxusers表-微信用户表

- 建数据库表

  ```mysql
  # 微信用户表
  # 微信登录后，如果没有关联users表，需要前端跳转填写身份、工号等信息的页面，并同时在users表中插入记录。
  CREATE TABLE `wxusers`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `nick_name` varchar(50) NOT NULL COMMENT '微信用户名',
    `avatar_url` varchar(500) NOT NULL COMMENT '微信头像地址',
  	`openId` varchar(100) NOT NULL COMMENT '微信网关返回的用户唯一标识',
    `phone` varchar(20) NOT NULL COMMENT '手机号', # 注册时通过手机号和users表进行关联
  	`user_id` int(10) COMMENT '关联的用户表id',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `phone`(`phone`) USING BTREE,
  	UNIQUE INDEX `openId`(`openId`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '微信用户表';
  ```

- 测试用例插入

  - 未关联users用户表

    ```mysql
    INSERT INTO `wxusers`(`nick_name`, `avatar_url`, `openId`, `phone`) 
    										   VALUES('高级管理员1的wx', 'https://xxxx', 'unadf323jq', '15333333333');
    ```

  ![image-20220105181328051](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220105181328051.png)

  - 通过phone字段查询关联的users表id

    ```mysql
    # 联合查询
    SELECT
    	u.id
    FROM
    	`user` u
    JOIN
    	wxusers w
    	ON	u.phone = w.phone
    WHERE w.phone = '15333333333';
    ```

  ![image-20220105181628099](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220105181628099.png)

  - 更新wxusers表的user_id字段

    ```mysql
    UPDATE `wxusers` SET user_id = 1;
    ```

    ![image-20220105181749774](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220105181749774.png)

- JavaBean实体类

  ```java
  public class WxUsersEntity {
  	private int id;
  	private String nickName;
  	private String avatarUrl;
  	private String openId;
  	private String phone;
  	private int userId;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射层接口

  ```java
  public interface WxUsersMapping {
  
  	/*
  	 * 插入一个微信用户记录
  	 */
  	@Insert("INSERT INTO `wxusers`(`nick_name`, `avatar_url`, `openId`, `phone`)"
  			+ "	VALUES(#{nickName}, #{avatarUrl}, #{openId}, #{phone});")
  	void insert(WxUsersEntity wxUsersEntity);
  	
  	/*
  	 * 根据手机号查询users表对应的userid
  	 */
  	@Select("SELECT"
  			+ "	u.id "
  			+ "FROM"
  			+ "	`user` u "
  			+ "JOIN "
  			+ "	wxusers w"
  			+ "	ON	u.phone = w.phone "
  			+ "WHERE w.phone = #{phone};")
  	int getUserId(String phone);
  	
  	/*
  	 * 更新wxusers表的userId字段
  	 */
  	@Update("UPDATE `wxusers` SET user_id = #{userId};")
  	void updateUserId(int userId);
  }
  ```

## 项目数据库

​	每个流程分开建立表，并加上流程状态表，标记每个流程的状态。

### pro_status表-项目流程状态表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_status`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `status_name` varchar(50) NOT NULL COMMENT '流程描述', 
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `status_name`(`status_name`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目流程状态表';
  ```

- 测试用例插入

  ```mysql
  INSERT into `pro_status` (`status_name`)VALUES('项目请求中');
  INSERT into `pro_status` (`status_name`)VALUES('项目评审中');
  INSERT into `pro_status` (`status_name`)VALUES('项目签订中');
  INSERT into `pro_status` (`status_name`)VALUES('项目设计中');
  INSERT into `pro_status` (`status_name`)VALUES('项目生产中');
  INSERT into `pro_status` (`status_name`)VALUES('项目装配中');
  INSERT into `pro_status` (`status_name`)VALUES('项目审核不通过');
  ```

  <img src="https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220110095751813.png" alt="image-20220110095751813" style="zoom:80%;" />

- JavaBean实体类

  ```java
  public class StatusEntity {
  	private int id;
  	private String statusName;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface StatusMapping {
  
  	/*
  	 * 插入一条状态记录
  	 */
  	@Insert("INSERT into `pro_status` (`status_name`)VALUES(#{statusName});")
  	void insert(StatusEntity statusEntity);
  	
  	/*
  	 * 根据状态描述查询id
  	 */
  	@Select("SELECT id FROM `pro_status` WHERE `status_name` = #{statusName};")
  	int getIdByStatusName(String statusName);
  	
  	/*
  	 * 修改状态描述
  	 */
  	@Update("UPDATE `pro_status` SET `status_name` = #{newName} WHERE `status_name` = #{statusName};")
  	void updateStatusName(String statusName, String newName);
  }
  ```

### pro_request表-项目请求表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_request`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `name` varchar(100) NOT NULL COMMENT '项目名称', 
  	`company_name` VARCHAR(200) NOT NULL COMMENT '公司名称',
  	`description` VARCHAR(500) COMMENT '项目简单描述',
  	`sow_url` VARCHAR(300) NOT NULL COMMENT '项目的sow文件本地磁盘路径',
  	`quotation_url` VARCHAR(300) NOT NULL COMMENT '项目的报价文件本地磁盘路径',
  	`design_url` VARCHAR(300) NOT NULL COMMENT '项目的设计方案文件本地磁盘路径',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name`(`name`) USING BTREE,
  	INDEX `company_name`(`company_name`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目请求表';
  ```

- 插入测试用例

  ```mysql
  INSERT into `pro_request` (`name`, `company_name`, `description`, `sow_url`, `quotation_url`, `design_url`)VALUES
  	('项目1', '客户公司1', '项目1的描述', '/var/project/sow/sow1_url', '/var/project/quotation/quotation1_url', '/var/project/design/design1_url'),
  	('项目2', '客户公司2', '项目2的描述', '/var/project/sow/sow2_url', '/var/project/quotation/quotation2_url', '/var/project/design/design2_url');
  ```

  ![image-20220110105443363](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220110105443363.png)

- JavaBean实体类

  ```java
  public class RequestEntity {
  	private int id;
  	private String name;
  	private String companyName;
  	private String description;
  	private String sowUrl;
  	private String quotationUrl;
  	private String designUrl;
  }
  ```

- Mapper映射接口

  ```java
  public interface RequestMapping {
  
  	/*
  	 * 插入一个项目请求记录
  	 */
  	@Insert("NSERT into `pro_request` (`name`, `company_name`, `description`, `sow_url`, `quotation_url`, `design_url`)VALUES"
  			+ "	(#{name}, #{companyName}, #{description}, #{sowUrl}, #{quotationUrl}, #{designUrl})")
  	void insert(RequestEntity requestEntity);
  	
  	/*
  	 * 根据项目名称查询项目信息
  	 */
  	@Select("SELECT * FROM `pro_request` WHERE `name` = #{name};")
  	RequestEntity getRequestByName(String name);
  }
  ```

### pro_sign表-项目签订表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_sign`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
  	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
  	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
  	`customer_id` VARCHAR(100) NOT NULL COMMENT '客户代码',
  	`product_category` VARCHAR(50) COMMENT '产品类别',
  	`line_number` VARCHAR(100) COMMENT '生产线编号',
  	`device_number` VARCHAR(100) COMMENT '设备工位号',
  	`customer_engineer` VARCHAR(50) COMMENT '对应的客户工程师',
  	`delivery_date` timestamp(0) NOT NULL COMMENT '交货日期',
  	`pay_url` VARCHAR(300) NOT NULL COMMENT '付款条款文件本地磁盘路径',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE,
  	INDEX `po_id`(`po_id`) USING BTREE,
  	INDEX `sign_id`(`sign_id`) USING BTREE,
    UNIQUE INDEX `pro_id`(`pro_id`) USING BTREE,
  	INDEX `customer_id`(`customer_id`) USING BTREE,
  	INDEX `delivery_date`(`delivery_date`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目签订表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_sign` (`po_id`, `sign_id`, `pro_id`, `customer_id`, `product_category`, `line_number`, `device_number`, `customer_Engineer`, `delivery_date`, `pay_url`) VALUES
   ('项目1poId', '项目1signId', '项目1proId', '项目1customerId', '项目1productCategory', '项目1LineNumber', '项目1deviceNumber', '项目1customerEngineer', '2022-04-01 00:00:00', '项目1payUrl'),
   ('项目2poId', '项目2signId', '项目2proId', '项目2customerId', '项目2productCategory', '项目2LineNumber', '项目2deviceNumber', '项目2customerEngineer', '2022-06-01 00:00:00', '项目1payUrl');
  ```

  ![image-20220111102937122](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220111102937122.png)

- JavaBean实体类

  ```java
  public class SignEntity {
  	private int id;
  	private String poId;
  	private String signId;
  	private String proId;
  	private String customerId;
  	private String productCategory;
  	private String lineNumber;
  	private String deviceNumber;
  	private String customerEngineer;
  	private Timestamp deliveryDate;
  	private String payUrl;
  }
  
  ```

- Mapper映射接口

  ```java
  public interface SignMapping {
  
  	/*
  	 * 插入一条签订记录
  	 */
  	@Insert("INSERT INTO `pro_sign` (`po_id`, `sign_id`, `pro_id`, `customer_id`, `product_category`, `line_number`, "
  			+ "`device_number`, `customer_Engineer`, `delivery_date`, `pay_url`) VALUES"
  			+ " (#{poId}, #{signId}, #{proId}, #{customerId}, #{productCategory}, #{lineNumber}, #{deviceNumber}, "
  			+ "#{customerEngineer}, #{deliveryDate}, #{payUrl});")
  	void insert(SignEntity signEntity);
  	
  	/*
  	 * 根据po查询签订记录
  	 */
  	@Select("SELECT * FROM `pro_sign` WHERE `po_id` = #{poId};")
  	SignEntity getSignByPoId(String poId);
  	
  	/*
  	 * 根据合同号查询签订记录
  	 */
  	@Select("SELECT * FROM `pro_sign` WHERE `sign_id` = #{signId};")
  	SignEntity getSignBySignId(String signId);
  	/*
  	 * 根据公司项目编号查询签订记录
  	 */
  	@Select("SELECT * FROM `pro_sign` WHERE `pro_id` = #{proId};")
  	SignEntity getSignByProId(String proId);
  	/*
  	 * 根据客户公司代码查询签订记录
  	 */
  	@Select("SELECT * FROM `pro_sign` WHERE `customer_id` = #{customerId};")
  	SignEntity getSignByCustomerId(String customerId);
  	/*
  	 * 根据交货日期查询签订记录
  	 */
  	@Select("SELECT * FROM `pro_sign` WHERE `delivery_date` = #{deliveryDate};")
  	SignEntity getSignByDeliveryDate(Timestamp deliveryDate);
  }
  ```

### 项目设计相关表

#### pro_design表-项目设计表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_design`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
  	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
  	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
  	`mec_engineer` VARCHAR(50) NOT NULL COMMENT '机械设计工程师',
  	`ele_engineer` VARCHAR(50) NOT NULL COMMENT '电气设计工程师',
  	`sof_engineer` VARCHAR(50) NOT NULL COMMENT '软件设计工程师',
  	`mec_start_date` timestamp(0) NOT NULL COMMENT '机械设计开始日期',
  	`mec_end_date` timestamp(0) NOT NULL COMMENT '机械设计完成日期',
  	`ele_start_date` timestamp(0) NOT NULL COMMENT '电气设计开始日期',
  	`ele_end_date` timestamp(0) NOT NULL COMMENT '电气设计完成日期',
  	`sof_start_date` timestamp(0) NOT NULL COMMENT '软件开始日期',
  	`sof_end_date` timestamp(0) NOT NULL COMMENT '软件完成日期',
  	`tip` VARCHAR(300) NOT NULL COMMENT '备注',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE,
  	INDEX `po_id`(`po_id`) USING BTREE,
  	INDEX `sign_id`(`sign_id`) USING BTREE,
    UNIQUE INDEX `pro_id`(`pro_id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目设计表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_design` (`po_id`, `sign_id`, `pro_id`, `mec_engineer`, `ele_engineer`, `sof_engineer`, `mec_start_date`, `mec_end_date`, `ele_start_date`, `ele_end_date`,
  `sof_start_date`, `sof_end_date`, `tip`) VALUES 
  ('项目1poId', '项目1signId', '项目1proId', '项目1机械', '项目1电气', '项目1软件', '2022-01-01: 00:00:00', '2022-02-01: 00:00:00', '2022-01-01: 00:00:00', '2022-02-01: 00:00:00', 
  '2022-01-01: 00:00:00', '2022-02-01: 00:00:00', '项目1备注');
  ```

  ![image-20220112161502193](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220112161502193.png)

- JavaBean实体类

  ```java
  public class DesignEntity {
  	private int id;
  	private String poId;
  	private String signId;
  	private String proId;
  	private String mecEngineer;
  	private String eleEngineer;
  	private String sofEngineer;
  	private Timestamp mecStartDate;
  	private Timestamp mecEndDate;
  	private Timestamp eleStartDate;
  	private Timestamp eleEndDate;
  	private Timestamp sofStartDate;
  	private Timestamp sofEndDate;
  	private String tip;
  }
  ```

- Mapper映射接口

  ```java
  public interface DesignMapping {
  
  	/*
  	 * 插入一条设计记录
  	 */
  	@Insert("INSERT INTO `pro_design` (`po_id`, `sign_id`, `pro_id`, `mec_engineer`, `ele_engineer`, `sof_engineer`, `mec_start_date`, `mec_end_date`, `ele_start_date`, `ele_end_date`,"
  			+ "`sof_start_date`, `sof_end_date`, `tip`) VALUES "
  			+ "(#{poId}, #{signId}, #{proId}, #{mecEngineer}, #{eleEngineer}, #{sofEngineer}, #{mecStartDate}, #{mecEndDate}, #{eleStartDate}, #{eleEndDate}, "
  			+ "#{sofStartDate}, #{sofEndDate}, #{tip});")
  	void insert(DesignEntity designEntity);
  }
  ```

#### pro_design_mechanics表-机械师设计表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_design_mechanics`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `bom_url` varchar(300) NOT NULL COMMENT 'BOM表存储路径', 
    `threeD_url` varchar(300) NOT NULL COMMENT '机械设计3D图纸',
  	`twoD_url` varchar(300) NOT NULL COMMENT '2D图纸', 
  	`gas_url` varchar(300) NOT NULL COMMENT '气路图', 
  	`comp_url` varchar(300) NOT NULL COMMENT '装配组件图', 
  	`prof_url` varchar(300) NOT NULL COMMENT '型材框架图', 
  	`vul_list_url` varchar(300) NOT NULL COMMENT '易损件清单', 
  	`vul_draw_url` varchar(300) NOT NULL COMMENT '易损件图纸',  
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '机械设计表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_design_mechanics` (`bom_url`, `threeD_url`, `twoD_url`, `gas_url`, `comp_url`, `prof_url`, `vul_list_url`, `vul_draw_url`) VALUES
  	('项目1bomurl', '项目1threeD_url', '项目1twoD_url', '项目1gas_url', '项目1comp_url', '项目1prof_url', '项目1vul_list_url', '项目1vul_draw_url');
  ```

  ![image-20220210134900698](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220210134900698.png)

- JavaBean实体类

  ```java
  public class DesignMechancisEntity {
  	private int id;
  	private String bomUrl;
  	private String threeDUrl;
  	private String twoDUrl;
  	private String gasUrl;
  	private String compUrl;
  	private String profUrl;
  	private String vulListUrl;
  	private String vulDrawUrl;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface DesignMechancisMapping {
  
  	/*
  	 * 插入一条机械设计师记录
  	 */
  	@Insert("INSERT INTO `pro_design_mechanics` (`bom_url`, `threeD_url`, `twoD_url`, `gas_url`, `comp_url`, `prof_url`, `vul_list_url`, `vul_draw_url`) VALUES "
  			+ "	(#{bomUrl}, #{threeDUrl}, #{twoDUrl}, #{gasUrl}, #{compUrl}', #{profUrl}, #{vulListUrl}, #{vulDrawUrl});")
  	void insert(DesignMechancisEntity designMechancisEntity);
  }
  ```

#### pro_design_electrical表-电气设计表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_design_electrical`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `bom_url` varchar(300) NOT NULL COMMENT 'BOM表存储路径', 
    `graph_url` varchar(300) NOT NULL COMMENT '电气图纸',
  	`list_url` varchar(300) NOT NULL COMMENT '备件清单', 
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '电气设计表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_design_electrical` (`bom_url`, `graph_url`, `list_url`) VALUES
  	('项目1bomurl', '项目1graph_url', '项目1list_url');
  ```

  ![image-20220210140601614](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220210140601614.png)

- JavaBean实体类

  ```java
  public class DesignElectricalEntity {
  	private int id;
  	private String bomUrl;
  	private String graphUrl;
  	private String listUrl;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface DesignElectricalMapping {
  
  	/*
  	 * 插入一条电气设计记录
  	 */
  	@Insert("INSERT INTO `pro_design_electrical` (`bom_url`, `graph_url`, `list_url`) VALUES "
  			+ "	(#{bomUrl}, #{graphUrl}, #{listUrl});")
  	void insert();
  }
  ```

#### pro_design_software表-软件设计表

- 建立数据库表

  ```mysql
  CREATE TABLE `pro_design_software`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `prog_url` varchar(300) NOT NULL COMMENT 'PLC程序存储路径', 
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '软件设计表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_design_software` (`prog_url`) VALUES
  	('项目1prog_url');
  ```

  ![image-20220210141208718](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220210141208718.png)

- JavaBean实体类

  ```java
  public class DesignSoftwareEntity {
  	private int id;
  	private String progUrl;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface DesignSoftwareMapping {
  	@Insert("INSERT INTO `pro_design_software` (`prog_url`) VALUES (#{progUrl});")
  	void insert(DesignSoftwareEntity designSoftwateEntity);
  }
  ```

#### pro_design_relation表-项目设计关联表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_design_relation`(
    `desi_id` int(100) NOT NULL COMMENT '项目设计表id',
    `mech_id` int(100) NOT NULL COMMENT '机械设计表id', 
  	`elec_id` int(100) NOT NULL COMMENT '电气设计表id', 
  	`soft_id` int(100) NOT NULL COMMENT '软件设计表id', 
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  	INDEX `desi_id`(`desi_id`) USING BTREE,
  	INDEX `mech_id`(`mech_id`) USING BTREE,
  	INDEX `elec_id`(`elec_id`) USING BTREE,
  	INDEX `soft_id`(`soft_id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目设计关联表';
  ```

- 插入测试用例

  ```java
  INSERT INTO `pro_design_relation` (`desi_id`, `mech_id`, `elec_id`, `soft_id`) VALUES
  	(1, 1, 1, 1);
  SELECT m.*, e.*, s.*
  FROM	pro_design d, pro_design_mechanics m, pro_design_electrical e, pro_design_software s, pro_design_relation r
  WHERE r.`desi_id` = d.id and r.`mech_id` = m.id and r.`elec_id` = e.id and r.`soft_id` = s.id and d.pro_id = '项目1proId';
  ```

  ![image-20220210142257140](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220210142257140.png)

- JavaBean实体类

  ```java
  public class DesignRelationEntity {
  	private int desiId;
  	private int mechId;
  	private int elecId;
  	private int softId;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface DesignRelationMapping {
  
  	/*
  	 * 插入一条设计关系表记录
  	 */
  	@Insert("INSERT INTO `pro_design_relation` (`desi_id`, `mech_id`, `elec_id`, `soft_id`) VALUES "
  			+ "(#{desiId}, #{mechId}, #{elecId}, #{softId});")
  	void insert(DesignRelationEntity designRelationEntity);
  	
  	/*
  	 * 根据项目公司内部项目表好pro_id查询项目机械设计信息
  	 */
  	@Select("SELECT m.*"
  			+ " FROM pro_design d, pro_design_mechanics m, pro_design_relation r "
  			+ " WHERE r.`desi_id` = d.id and r.`mech_id` = m.id and d.pro_id = '项目1proId';")
  	DesignMechancisEntity getMechancisByProid(int proId);
  }
  ```

### 项目生产相关表

#### pro_product表-项目生产表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_product`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
  	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
  	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
  	`pur_id` int(10) NOT NULL COMMENT '采购表id',
  	`process_id` int(10) NOT NULL COMMENT '机械零件加工表id',
  	`out_id` int(10) NOT NULL COMMENT '外协表id',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE,
  	INDEX `po_id`(`po_id`) USING BTREE,
  	INDEX `sign_id`(`sign_id`) USING BTREE,
    UNIQUE INDEX `pro_id`(`pro_id`) USING BTREE,
  	INDEX `pur_id`(`pur_id`) USING BTREE,
  	INDEX `process_id`(`process_id`) USING BTREE,
  	INDEX `out_id`(`out_id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目生产表';
  ```

- 插入测试用例

  ```mysql
  INSERT into `pro_product` (`po_id`, `sign_id`, `pro_id`, `pur_id`, `process_id`, `out_id`)values 
  	('项目1poId', '项目1signId', '项目1proId', 1, 1, 1);
  ```

  ![image-20220210155149273](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220210155149273.png)

- JavaBean实体类

  ```java
  public class ProductEntity {
  	private int id;
  	private String poId;
  	private String signId;
  	private String proId;
  	private int purId;
  	private int processId;
  	private int outId;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface ProductMapping {
  
  	/*
  	 * 插入一条生产记录
  	 */
  	@Insert("INSERT into `pro_product` (`po_id`, `sign_id`, `pro_id`, `pur_id`, `process_id`, `out_id`)values "
  			+ "	(#{poId}, #{signId}, #{proId}, #{purId}, #{processId}, #{outId});")
  	void insert(ProductEntity productEntity);
  }
  ```

#### pro_product_purchase表-采购表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_product_purchase`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `name` varchar(100) NOT NULL COMMENT '名称', 
  	`model` VARCHAR(100) NOT NULL COMMENT '型号',
  	`number` int(50) NOT NULL COMMENT '数量',
  	`supplier` VARCHAR(100) NOT NULL COMMENT '供应商',
  	`type` VARCHAR(50) NOT NULL COMMENT '类别',
  	`price` DOUBLE(10,2) NOT NULL COMMENT '价格',
  	`order_date` timestamp(0) NOT NULL COMMENT '下单日期',
  	`delivery_date` timestamp(0) NOT NULL COMMENT '货期',
  	`arrival_date` timestamp(0) NOT NULL COMMENT '实际到货日期',
  	`warehousing_date` timestamp(0) NOT NULL COMMENT '入库日期',
  	`location_number` int(50) NOT NULL COMMENT '库位号',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '采购表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_product_purchase` (`name`, `model`, `number`, `supplier`, `type`, `price`, `order_date`, `delivery_date`, `arrival_date`, `warehousing_date`, `location_number`) VALUES
  	('项目1采购物品1', '项目1采购型号1', 50, '项目1采购物品1供应商', '项目1采购物品1类别', 7.45, '2022-02-01 00:00:00', '2023-02-01 00:00:00', '2022-02-02 00:00:00', '2022-02-02 00:00:00', 1);
  ```

  ![image-20220211102107219](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220211102107219.png)

- JavaBean实体类

  ```java
  public class ProductPurchaseEntity {
  	private int id;
  	private String name;
  	private String model;
  	private int number;
  	private String supplier;
  	private String type;
  	private double price;
  	private Timestamp orderDate;
  	private Timestamp deliveryDate;
  	private Timestamp arrivalDate;
  	private Timestamp warehousingDate;
  	private int locationNumber;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface ProductPurchaseMapping {
  
  	/*
  	 * 插入一条采购记录
  	 */
  	@Insert("INSERT INTO `pro_product_purchase` (`name`, `model`, `number`, `supplier`, `type`, `price`, `order_date`, `delivery_date`, `arrival_date`, `warehousing_date`, `location_number`) VALUES " + 
  			"	(#{name}, #{model}, #{number}, #{supplier}, #{type}, #{price}, #{orderDate}, #{deliveryDate}, #{arrivalDate}, #{warehousingDate}, #{locationNumber});")
  	void insert(ProductPurchaseEntity productPurchaseEntity);
  }
  ```

#### pro_product_process表-零件加工表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_product_process`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `name` varchar(100) NOT NULL COMMENT '名称', 
  	`graph_number` VARCHAR(100) NOT NULL COMMENT '图号',
  	`number` int(50) NOT NULL COMMENT '数量',
  	`price` DOUBLE(10,2) NOT NULL COMMENT '价格',
  	`start_date` timestamp(0) NOT NULL COMMENT '加工开始日期',
  	`end_date` timestamp(0) NOT NULL COMMENT '实际加工完成日期',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '机械零件加工表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_product_process` (`name`, `graph_number`, `number`, `price`, `start_date`, `end_date`) VALUES 
  	('项目1零件1', '零件1图号', 50, 66.88, '2022-02-01 00:00:00', '2022-02-03 00:00:00');
  ```

  ![image-20220211103145716](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220211103145716.png)

- JavaBean实体类

  ```java
  public class ProductProcessEntity {
  	private int id;
  	private String name;
  	private String graphNumber;
  	private int number;
  	private double price;
  	private Timestamp startDate;
  	private Timestamp endDate;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface ProductProcessMapping {
  
  	/*
  	 * 插入一条零件加工记录
  	 */
  	@Insert("INSERT INTO `pro_product_process` (`name`, `graph_number`, `number`, `price`, `start_date`, `end_date`) VALUES " + 
  			"	(#{name}, #{graph_number}, #{number}, #{price}, #{startDate}, #{endDate});")
  	void insert(ProductProcessEntity productProcessEntity);
  }
  ```

#### pro_product_outsource表-外协表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_product_outsource`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `po_id` varchar(100) NOT NULL COMMENT '项目poId', 
  	`company` VARCHAR(100) NOT NULL COMMENT '外协公司名称',
  	`eng_name` VARCHAR(10) NOT NULL COMMENT '外协公司对接人员',
      `content_desc` VARCHAR(100) NOT NULL COMMENT '外协内容简单描述',
  	`file_url` varchar(300) NOT NULL COMMENT '外协文件存放路径',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '外协表';
  ```

- 插入测试用例

  ```mysql
  INSERT into `pro_product_outsource` (`po_id`, `company`, `eng_name`, `content_desc`, `file_url`) VALUES ('项目1poId', '外协公司名称', '外协公司对接人员', '外协内容简单描述', '外协文件存放路径');
  ```

  ![image-20220221111927839](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220221111927839.png)

- JavaBean实体类

  ```java
  public class ProductOutsourceEntity {
  	private int id;
  	private String poId;
  	private String company;
  	private String engName;
      private String contentDesc;
  	private String fileUrl;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface ProductOutsourceMapping {
  
  	/*
  	 * 插入一条外协记录
  	 */
  	@Insert("INSERT into `pro_product_outsource` (`po_id`, `company`, `eng_name`, `content_desc`, `file_url`) VALUES "
  			+ "(#{poId}, #{company}, #{engName}, #{contentDesc}, #{fileUrl});")
  	void insert(ProductOutsourceEntity productOutsourceEntity);
  }
  ```

### pro_assembling表-项目装配表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_assembling`(
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
    `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
  	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
  	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
  	`me_name` VARCHAR(50) NOT NULL COMMENT '机械装配人员',
  	`me_start_date` timestamp(0) NOT NULL COMMENT '机械装配开始日期',
  	`me_end_date` timestamp(0) NOT NULL COMMENT '机械装配完成日期',
  	`el_name` VARCHAR(50) NOT NULL COMMENT '电气装配人员',
  	`el_start_date` timestamp(0) NOT NULL COMMENT '电气装配开始日期',
  	`el_end_date` timestamp(0) NOT NULL COMMENT '电气装配完成日期',
  	`so_name` VARCHAR(50) NOT NULL COMMENT '软件调试人员',
  	`so_start_date` timestamp(0) NOT NULL COMMENT '软件调试开始日期',
  	`so_end_date` timestamp(0) NOT NULL COMMENT '软件调试完成日期',
  	`check_name` VARCHAR(50) NOT NULL COMMENT '检验和测试人员',
  	`check_start_date` timestamp(0) NOT NULL COMMENT '检验和测试开始日期',
  	`check_end_date` timestamp(0) NOT NULL COMMENT '检验和测试完成日期',
    `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
    PRIMARY KEY (`id`) USING BTREE,
  	INDEX `po_id`(`po_id`) USING BTREE,
  	INDEX `sign_id`(`sign_id`) USING BTREE,
    UNIQUE INDEX `pro_id`(`pro_id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目装配表';
  ```

- 插入测试用例

  ```mysql
  INSERT INTO `pro_assembling` (`po_id`, `sign_id`, `pro_id`, `me_name`, `me_start_date`, `me_end_date`, `el_name`, `el_start_date`,
  	`el_end_date`, `so_name`, `so_start_date`, `so_end_date`, `check_name`, `check_start_date`, `check_end_date`) VALUES
  	('项目1poId', '项目1signId', '项目1proId', '项目1me_name', '2022-02-01 00:00:00', '2022-02-02 00:00:00', '项目1el_name', '2022-02-01 00:00:00', '2022-02-02 00:00:00',
  	'项目1so_name', '2022-02-01 00:00:00', '2022-02-02 00:00:00', '项目1check_name', '2022-02-01 00:00:00', '2022-02-02 00:00:00');
  ```

  ![image-20220211104630045](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220211104630045.png)

- JavaBean实体类

  ```java
  public class AssemblingEntity {
  	private int id;
  	private String poId;
  	private String signId;
  	private String proId;
  	private String meName;
  	private Timestamp meStartDate;
  	private Timestamp meEndDate;
  	private String elName;
  	private Timestamp elStartDate;
  	private Timestamp elEndDate;
  	private String soName;
  	private Timestamp soStartDate;
  	private Timestamp soEndDate;
  	private String checkName;
  	private Timestamp checkStartDate;
  	private Timestamp checkEndDate;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface AssemblingMapping {
  
  	/*
  	 * 插入一条装配记录
  	 */
  	@Insert("INSERT INTO `pro_assembling` (`po_id`, `sign_id`, `pro_id`, `me_name`, `me_start_date`, `me_end_date`, `el_name`, `el_start_date`," + 
  			"	`el_end_date`, `so_name`, `so_start_date`, `so_end_date`, `check_name`, `check_start_date`, `check_end_date`) VALUES " + 
  			"	(#{poId}, #{signId}, #{proId}, #{meName}, #{meStartDate}, #{meEndDate}, #{elName}, #{elStartDate}, #{elEndDate}, #{soName}, #{soStartDate}, #{soEndDate}, #{checkName}, #{checkStartDate}, #{checkEndDate});")
  	void insert(AssemblingEntity assemblingEntity);
  }
  ```


### pro_user_relation表_项目-用户关系表

- 建数据库表

  ```mysql
  CREATE TABLE `pro_user_relation`(
    `pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
    `user_id` int(10) NOT NULL COMMENT '用户数据库id',
  	`created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
    `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  	PRIMARY KEY (`pro_id`,`user_id`) USING BTREE,
    INDEX `pro_id`(`pro_id`) USING BTREE,
  	INDEX `user_id`(`user_id`) USING BTREE
  ) ENGINE = InnoDB COMMENT = '项目-用户关联表';
  ```

- 插入测试用例

  ```mysql
  INSERT into `pro_user_relation` (`pro_id`, `user_id`) VALUE (1, 1);
  ```

  ![image-20220221110051945](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220221110051945.png)

- JavaBean实体类

  ```java
  public class ProUserRelationEntity {
  
  	private int proId;
  	private int userId;
  	private Timestamp created;
  	private Timestamp updated;
  }
  ```

- Mapper映射接口

  ```java
  public interface ProUserRelationMapping {
  
  	/*
  	 * 插入一条项目-用户关系记录
  	 */
  	@Insert("INSERT into `pro_user_relation` (`pro_id`, `user_id`) VALUE (#{proId}, #{userId});")
  	void insert(ProUserRelationEntity proUserRelationEntity);
  	
  	/*
  	 * 根据userId,查询相关的proId
  	 */
  	@Select("select `pro_id` from `pro_user_relation` where `user_id` = #{userId}")
  	List<Integer> getProIdListByUserId(int userId);
  	
  	/*
  	 * 根据proId, 查询相关的userId
  	 */
  	@Select("select `user_id` from `pro_user_relation` where `pro_id` = #{proId}")
  	List<Integer> getUserIdListByProId(int proId);
  }
  ```

# 数据库部署

1. 服务器建库

   ![image-20220117113150791](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117113150791.png)

   目前只把服务端的用户部分数据库导入。

   - user表

     ![image-20220117113324995](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117113324995.png)

   - department表

     ![image-20220117113351050](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117113351050.png)

   - post表

     ![image-20220117113415795](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117113415795.png)

   - sys_permission表

     ![image-20220117113453419](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117113453419.png)

   - sys_users_permissioin表

     ![image-20220117113529340](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117113529340.png)

   - wxusers表

     ![image-20220117114259131](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117114259131.png)

2. 测试类编写

   - 调整mysql配置项

     ```yaml
     spring:
     # mysql
        datasource:
           url: jdbc:mysql://114.212.128.235:3306/pms # 组内服务器部署Mysql，连接需要连接内网
           username: root
           password: Shark666@nju
           driver-class-name: com.mysql.jdbc.Driver
     ```

   - 测试类

     ```java
     @RestController
     public class TestRemoteMysql {
     
     	@Autowired
     	private UserMapping userMapping;
     	
     	/*
     	 * 测试组内服务器mysql部署是否成功
     	 * - 测试用户查询
     	 */
     	@RequestMapping("/testMysql")
     	public String testMysql() {
     		List<UserEntity> all = userMapping.getAll();
     		return JSONObject.toJSONString(all);
     	}
     }
     ```

   - 启动类

     ```java
     @SpringBootApplication
     @MapperScan(basePackages = {"com.shark.users.mapper", "com.shark.project.mapper"})
     public class PMSapp {
     
     	public static void main(String[] args) {
     		SpringApplication.run(PMSapp.class, args);
     	}
     }
     ```

3. 测试结果

   连接远端服务器注意一定要在学校内网下！

   - users表

     ![image-20220117113040134](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117113040134.png)

   - department表

     ![image-20220117114610140](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117114610140.png)

   - post表

     ![image-20220117114701328](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117114701328.png)

   - sys_permission表

     ![image-20220117114754261](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117114754261.png)

   - sys_users_permissioin表

     ![image-20220117114856564](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117114856564.png)

   - wxusers表

     ![image-20220117114942590](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220117114942590.png)

# 前端模板

找了很久，也找了很多，最后暂时确定这个让师弟先开发功能，后面有调整再更换模板。

![image-20220119150857744](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220119150857744.png)

# 项目部署

项目所需环境：

- `JDK 1.8`
- `mysql 8.0`

- `redis` 稳定版即可

## JDK安装

1. 查看有无系统自带jdk 

   ```shell
   rpm -qa |grep java
   ```

   如果有可以进行批量卸载

   ```shell
   rpm -qa | grep java | xargs rpm -e --nodeps 
   ```

2. 查询yum可用的jdk版本

   ```shell
   yum list java*
   ```

   安装jkd1.8

   ```shell
   yum install java-1.8.0-openjdk* -y
   ```

3. 验证是否安装成功

   ```shell
   java -version
   ```

4. 查看当前java版本和安装位置

   ```shell
   alternatives --config java
   ```

5. 设置环境变量

   ```shell
   vim /etc/profile
   
   export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.x86_64
   export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
   export PATH=$JAVA_HOME/bin:$PATH
   
   . /etc/profile
   ```

## MySQL安装

### 下载MySQL安装包

1. 首先需要查询本机centos或其他Linux版本信息

   输入`lsb_release -a`

2. 在官网https://dev.mysql.com/downloads/mysql下载对应版本

   - 可以现在bundle版本的，如mysql-8.0.15-1.el7.x86_64.rpm-bundle.tar。
   - 也可以单独下载各个模块：common –>client-plugs –> libs –> client –> server。

### 安装Mysql

按照common –>client-plugs –> libs –> client –> server顺序进行安装。

使用命令`rpm -ivh ***.rpm`进行模块的安装。

> 注意：
>
> 当提示“mariadb-libs 被 mysql-community-libs-8.0.15-1.el7.x86_64 取代”，是lib和系统自带的冲突。使用命令`yum remove mysql-libs -y`后再使用`rpm -ivh ***` 命令继续安装。

###  MySQL的root密码和相关权限设置

1. 首先执行`service mysqld restart`指令重启mysql服务，为了后面查看日志里面的默认root密码。

   ```shell
   [root@localhost /]# service mysqld restart
   Redirecting to /bin/systemctl restart mysqld.service
   [root@localhost /]# /bin/systemctl restart mysqld.service
   ```

2. 查看日志默认密码，mysql的日志在`/var/log/mysqld.log`里面

   ```shell
   [root@localhost log]# cat mysqld.log 
    .... A temporary password is generated for root@localhost: #+Tp!)#Fv6e;
   ```

3. 修改登陆密码

   ```shell
   [root@localhost /]# mysql -u root -p
   Enter password: 
   Welcome to the MySQL monitor.  Commands end with ; or \g.
   Your MySQL connection id is 13
   Server version: 8.0.15
   Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.
   Oracle is a registered trademark of Oracle Corporation and/or its
   affiliates. Other names may be trademarks of their respective
   owners.
   Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
   mysql>  ALTER USER 'root'@'localhost' IDENTIFIED BY '...@...123';
   ```

   注意这里密码有复杂度的要求，尽量设置复杂一点并好记忆的密码～

4. 开放所有ip地址都能访问：

   ```shell
   CREATE USER 'root'@'%' IDENTIFIED BY 'root123';
   ```

   root123是你自己设置的密码，若执行开放指定ip能访问，把%换成ip地址。

5. 修改加密方式：

   ```shell
   ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root123';
   ```

   MySQL8默认是caching_sha2_password

6. 开放防火墙端口 查看防火墙开放的端口。`firewall-cmd --zone=public --list-ports` 开启防火墙端口3306：`firewall-cmd --zone=public --add-port=3306/tcp --permanent`

###  远程连接数据库授权

1. 创建时间数据库

   ```shell
   CREATE SCHEMA `testd_atabase` DEFAULT CHARACTER SET utf8
   ```

2. 授权

   ```shell
   grant all on *.* to 'root'@'%';
   ```

3. grant权限修改

   ```shell
   update mysql.user set Grant_priv='Y',Super_priv='Y' ;
   ```

## 后台运行jar包

1. 查看防火墙是否开放了jar包运行端口

   ```shell
   firewall-cmd --zone=public --list-ports—查看开放端口
   firewall-cmd --zone=public --add-port=8081/tcp --permanent--开放
   firewall-cmd --zone=public --remove-port=8081/tcp --permanent--关闭
   firewall-cmd --reload --刷新配置
   systemctl stop firewalld.service  --关闭防火墙(安全隐患)
   firewall-cmd --state --查看防火墙状态
   ```

   **开放端口后，一定要使用`firewall-cmd --reload`命令刷新配置才能生效**

2. 查看当前java进程，如果之前启动过可以先关闭进程

   ```shell
   ps -ef|grep java #查看java相关进程
   kill -9 PID #关闭Jar包进程
   ```

3. 后台启动Jar包

   ```shell
   nohup java -jar jarName-0.0.1-SNAPSHOT.jar >msg.log 2>&1 &
   ```


## 服务器部署方式

1. 自购服务器部署

   此项目管理系统针对百人左右规模的企业进行设计，QPS不高，所需服务器配置相应要求也不太高。可选择：

   - Linux操作系统：RedHat或Centos
   - 内存：8G以上
   - 硬盘容量：10T-20T左右，后续不够可以进行自行扩容
   - 带宽：企业内部使用，QPS预估在10左右，带宽5M左右即可

2. 云服务器

   根据上述参数可以选择在云服务商购买云服务器进行部署，如阿里云或腾讯云。
