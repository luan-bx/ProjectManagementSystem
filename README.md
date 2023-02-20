# PMS
项目管理系统开发

# 开发节点

## 数据库

​	骞昊负责数据表的设计，节点安排如下：

- 2022-01-10～2022-01-14

  完成项目方面的流程梳理，以及项目请求、评审、签订表的设计和测试，3～4个表左右

- 2022-01-17～2022-01-22

  部署数据库表到组内服务器中，并进行登录mapper接口的本地网络的测试

- 2022-02-10～2022-02-11

  完成项目设计、生产相关表（较多，需要考虑解耦）的设计，5～6个表左右

- 2022-02-14～2022-02-18

  完成项目生产相关表的设计和测试

- 2022-02-21～2022-02-25

  完成项目装配表的设计，并进行项目全表相关性的测试

- 2022-02-28～2022-03-04

  针对服务端的接口进行修改和测试。

- 2022-03-07～2022-03-18

  数据库部分差不多完成，只需要根据在服务端接口设计中进行按需修改即可。

  我考虑这两周，最快速度把毕业论文完成，期间可以辅佐师弟相关服务器问题的解决和答疑。

- 2022-03-21～答辩前一周

  两个师弟哪里有需要就帮哪里，比如邮件通信、Redis缓存。

- 答辩前一周～答辩

  准备一下答辩。

## 服务端

栾百翔负责服务端的设计，节点安排如下：

- 2022-01-10～2022-01-14

  两种注册方式服务端设计，controller层和service层

- 2022-01-17～2022-01-22

  两种登录方式部分服务端设计，controller层和service层

- 2022-02-14～2022-02-18

  用户部分的测试和BUG修改，和海哥对接一下注册接口

- 2022-02-21～2022-02-25

  项目请求、状态、签订的service层和control层，并进行测试

- 2022-02-28～2022-03-11

  项目设计、生成的service层和control层，并进行测试

- 2022-03-14～2022-03-19

  项目装配表的service层和control层，并进行测试

- 2022-03-21~2022-03-25

  整体的测试和修改

- 2022-03-28～2022-04-02

  分页显示项目信息、按条件查询项目信息部分服务端设计、邮件通知
  
- 2022-04-02～往后

  代码优化、Redis缓存



## 前端
唐海歌负责前端页面的设计，节点安排如下：

2022.01.17-2022.01.21
完成登录、注册页面的设计，以及后续页面的粗略设计。本周只做页面布局，功能实现放在开学后进行。

寒假期间
学习js，可能的话实现登录注册页面的功能

2022.02.14-2022.02.18
完善项目审核、签订页面布局，并初步实现页面功能

2022.02.21-2022.02.25
完善项目审核、签订页面功能实现

2022.02.28-2022.03.04
完成项目设计页面布局，初步实现项目设计功能

2022.03.07-2022.03.11
完善项目设计功能，完成项目生产页面布局

2022.03.14-2022.03.18
实现生产页面的功能，若有时间完成项目装配页面的布局

2022.03.21-2022.03.25
实现项目装配页面的功能

2022.03.28-2022.04.08
对前面所做页面进行修改和完善，测试功能是否正确运行

2.16- 3.15

前端页面结束

3.15 - 4.1

学习一下后端知识，Java，mysql，springboot

4.1 -～

边写论文，邮件功能、redis功能（插件的性质）


# 开发会议

## 20220106

### 注册

**Phone一旦绑定之后，其他方式注册提示重复注册**

- 用户-密码方式

  > - 用户名
  > - 密码
  > - 手机号
  > - 邮箱

- 第三方（微信）

  > - Nick_name
  > - 性别
  > - 头像地址url
  > - 地点。。

  **在users用户表不存在记录的情况下**

  1. *前端点击跳转微信登录*，返回上述信息（不包含openiD)

  2. controller层，请求微信网关返回openId, 封装是WxUsersEntity

  3. *前端(js? 跳转页面)，提示绑定手机号（实名认证）*，phone -->WxUsersEntity

  4. 直接调用WxUsersMapping这个接口直接插入数据库。

  5. 需要同时插入users表

     1. *前端跳转到公司信息填写页面*，email、number、is_admin、post_id、department_id、wx_id

        email、number直接填写

        - 岗位 post，前端下拉框（从post表选择现有岗位）选择其中一个，选中后向后端返回post_id，判断这个post_id是否是is_admin（高级管理员）
        - 岗位 department，前端下拉框（从department表选择现有部门）选择其中一个，选中后向后端返回department_id
        - Wx_id，service层通过phone关联查询（mapper接口）,前端或者内存含有wx_id。

     2. 插入users表数据库

  **在users用户表存在记录的情况下**（之前使用用户名-密码注册过，现在是想关联第三方）
  
  1. *前端点击跳转微信登录*，返回上述信息（不包含openiD)（看一下微信api，能不能返回phone）
  2. controller层，请求微信网关返回openId, 封装是WxUsersEntity
  3. *前端(js? 跳转页面)，提示绑定手机号（实名认证）*，phone -->WxUsersEntity
     - 如果phone已经存在，提示“已经通过其他方式注册，进行相关联”
     - 如果phone不存在，（默认一个用户，一个号码）
  4. 通过phone查询users表，返回users_id ---》WxUsersEntity，进行插入。
### 岗位

  ​	提供一个页面，service层和controller层相应给接口，CURD岗位(根据身份/岗位不同，权限不同)

  ```MySQL
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、改动，用户');
  INSERT INTO `sys_permission` (`description`) VALUES ('查看用户');
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、改动岗位');
  INSERT INTO `sys_permission` (`description`) VALUES ('查看岗位');
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、改动项目组');
  INSERT INTO `sys_permission` (`description`) VALUES ('查看项目组');
  INSERT INTO `sys_permission` (`description`) VALUES ('增加、删除、查看、改动外协');
  ```

  1. 前端页面填好新增岗位的信息的时候，封装到PostEntity
  2. 在sys_users_permission表，填写岗位相对应的权限（用户插入的时候，可能考虑）

### 部门

  ​	提供一个页面，service层和controller层相应给接口，CURD项目组(只能由高级管理员admin，有权限)

### 权限

  ​	提供一个页面，service层和controller层相应给接口，CURD权限(只能由高级管理员admin，有权限)

## 20220112

登录部分：

1. 大致确定一下，有哪些页面---海歌

2. 理一下流程

   接口

项目部分：

1. 大致确定一下，页面--海歌

**例子**：

url=http://pms

- 如果登录过了(cookie)，url直接跳转主页（用户的主页page5）

- 如果没有登录、没有注册，url--》登录、注册页面（page1)

  ![image-20220112095309215](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220112095309215.png)

### 注册

- 先用户-密码，再微信
- 先微信再用户-密码

#### 先用户-密码，再微信

1. 用户名-密码注册

点击page1中的“立即注册”，

- 

  进入page2(`<form>`表单开始，提供下列字段)，填写个人信息（在填写用户名时，如果已经注册过，js弹框*ajax*提示已经注册过）。

  controller层接口：`api1[2][3]`，对接前端ajax接口，根据字段去数据库找信息，null或空

- 

  再点击页面（ne x t)进入page3，填写公司相关信息(工号、岗位（同时填写is_admin）、部门)，在Page3中点击“提交/注册”（`<form>`表单提交submit, action=api2)按钮进行注册

  controller层接口：api2，接受这些字段，放入UserEntity.

  service层：service2，先通过UUID生成OpenID放入UserEntity，再UserMapping.insert(UserEntity)

<img src="https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220112095512097.png" alt="image-20220112095512097" style="zoom:50%;" />

2. 微信注册

   点击page1中第三方登录的“微信”，跳转扫码页面，显示成功登录，返回的信息放入WxUsersEntity

   登录后

   - 如果可以直接获取phone字段，直接放入WxUsersEntity
   - 如果没有，**js弹框提示绑定手机号**，再放入WxUsersEntity

   controller层接口，api3，接收WxUsersEntity字段

   service层接口，service3，先去微信网关拿到wxOpenId放入WxUsersEntity. WxUsersMapping.getUserId()通过phone查询users表，返回users_id ---》WxUsersEntity，进行插入再WxUsersMapping.insert()。

#### 先微信，再用户-密码

1. 微信注册

   点击page1中第三方登录的“微信”，跳转扫码页面，显示成功登录，返回的信息放入WxUsersEntity。

   登录后，**js弹框提示绑定手机号、邮件**，再放入WxUsersEntity

   进入page3填写公司相关信息(工号、岗位（同时填写is_admin）、部门)，放入UsersEntity

   controller层：api4,接收WxUsersEntity，同时icon字段用微信头像，gender、phone也是，都放入UsersEntity中

   service层，serivce4，先去微信网关拿到wxOpenId放入WxUsersEntity，先插入UserMapping.insert(UserEntity)，WxUsersMapping.getUserId()通过phone查询users表，返回users_id ---》WxUsersEntity，进行插入再WxUsersMapping.insert()。

2. 用户-密码

   - 1

   进入page2(`<form>`表单开始，提供下列字段)，填写个人信息（在填写用户名时，如果已经注册过，js弹框*ajax*提示已经注册过）。

   controller层接口：`api1[2][3]`，对接前端ajax接口，根据字段去数据库找信息，null或空

   - 2

   提示当前phone和email已经注册(点击next的时候，js提示“正在进行相关绑定”）

   controller层接口：api7，接收上述字段，放入UserEntity

   service层接口：service7，同时先通过UUID生成OpenID，通过当前手机号查询到数据库的users表的id，调用UserMapping.updateFromUser进行数据库的更新

#### 个人中心

​	登录后，可以通过点击头像进入个人中心page4，可以进行用户账号的设置

- 先微信，再用户-密码的情况下，缺

  ```java
  private String userName;
  	private String password;
  	private String openId;
  ```

  可以设置用户-密码方式，填写用户名，密码

  Controller层：api5，接收userName，password，同时先通过UUID生成OpenID，通过当前手机号查询到数据库的users表的id，调用UserMapping.updateFromUser进行数据库的更新

- 先用户-密码，再微信

  点击微信绑定，跳转扫码页面，显示成功登录，返回的信息放入WxUsersEntity。

  Controller层：api6，把当前账号的phone放入WxUsersEntity，通过phone查询users表，返回users_id ---》WxUsersEntity，进行插入再WxUsersMapping.insert()。

### 登录

防heartData项目写，进入page5系统主页

### 项目

- 主页显示

![image-20220112104742347](https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220112104742347.png)

显示近期的几个项目

分栏（已完成、正在进行中）

筛选查询

## 20220216

### 服务端代码 code review

- 用户注册、登录相关代码已提出修改

- basecontroller层的index方法

  提取部门、岗位列表

  List = Mapping.getall();

  怎么把列表放到request对象，返回给前端.

- 测试用例

  1. 完全不重复信息
  2. 分别重复name、phone、email
  3. Wxid，存在/不存在

### 前端注册流程

- 用户名-密码的注册页面

  <img src="https://gitee.com/Object_Jason/my-pic-go/raw/master/img/image-20220112095512097.png" alt="image-20220112095512097" style="zoom:50%;" />

  - `user_name  '用户名',

     `password  '密码，加密存储',

     `phone`  '手机号',

     `email`  '邮箱',

    ​	`gender`  '性别，0 未知，1 女性，2 男性',

    ​	`icon` '头像地址',

  - 有一个Next按钮

- 公司信息填写页面

  - `number`  '工号',

    ​	`is_admin`  '是否是高级管理员',

    ​	`post' 岗位'，下拉选项，这个是后端返回

    ​	`department`  '部门'，下拉选项，这个是后端返回

  - 下方是注册按钮，点击完后回到注册页面

    <form>表单的action=Register

http://localhost/signup?user_name=2&password=2&phone=2&email=2%40163.com&gender=&number=2&is_admin=1&post=2&department=2&submit=注册

http://localhost/signup?user_name=2&password=2&phone=2&email=2%40163.com&gender=女&number=2&is_admin=1&post=2&department=2&submit=注册

## 20220222

![image-20220222171614222](http//jason-qianhao/image-20220222171614222.png)

## 20220329

后续优化：

小小千千 2022/03/29 上午10:13
1. 信息默认值

小小千千 2022/03/29 上午10:13
login.html

小小千千 2022/03/29 上午10:14
2. if...else

小小千千 2022/03/29 上午10:15
enum枚举类的使用

小小千千 2022/03/29 上午10:44
3. signup页面按钮☑️

4. 分页处理

5. BaseUtil.toIndex☑️

   分栏逻辑优化,3,7

6. 查询筛选

   **日期、项目名称**、po号、合同号

   根据状态查看所有项目

## 20220330

1. Bug

   - Index.html中的`th:case`语句错误☑️

2. 新增

   - 新增“项目详情页”，包含所有状态下的已提交信息（展示）和文件（做下载接口），可以参看首页的分栏显示。
   - **修改按钮对应的页面信息（后面做）**，目前假设不改project name，后续前端加入隐藏域，传id，对应ProjectDto和相关代码修改。
   - **权限（最后一个复杂点）**

3. 修改

   首页和自页面按钮修改(第一种，直接通过Js判断后，按钮显不显示；第二种，两个html，后端控制跳转。)

   ![image-20220330104541293](http://jason-qianhao.xyz/202203301045376.png)

4. 总结

   百翔：ProjectController、ProjectService，下载接口

   海歌：新增“项目详情页”，**修改按钮对应的页面信息（后面做）**，Mybatis，entity/mapper --》 mysql, @Update

   **周六上午9点前推一下代码。**

## 20220408

1. 修改
   - 日期组件问题，推荐自动填充
   - 设计、生产页面，每个已经提交过的子任务，要提示已经提交过
2. 下周任务
   - 后端权限
   - 前端分类页面显示、详情页、个人中心
   - 邮件看情况
