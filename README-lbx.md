# PMS
项目管理系统开发

# 配合需求

日期：
前端：
数据库：

# 开发节点

服务端设计节点安排如下：

- 2022-01-10～2022-01-14

  两种注册方式服务端设计，controller层和service层

- 2022-01-17～2022-01-22

  两种登录方式部分服务端设计，controller层和service层

- 2022-02-14～2022-02-25

  项目状态修改部分服务端设计

- 2022-02-28～2022-03-11

  项目生产资料的存储、项目装配信息的存储部分服务端设计

- 2022-03-14～2022-03-25

  项目新建部分服务端设计

- 2022-03-28～2022-04-08

  分页显示项目信息、按条件查询项目信息部分服务端设计


## 问题

1.18
用户名-密码注册时，储存的密码加密？
微信扫码请求网关
微信扫码登录只需要openid的if逻辑



## PMS服务端
一、注册

1.1用户名-密码注册

1.11注册界面接收以下信息：
用户名、密码、手机号、邮箱

1.12用户名密码注册逻辑：
接收前端用户注册填写的个人信息：用户名、手机号、邮箱，服务器请求数据库，查看以上三个字段是否有记录，来判断是否为新用户。
（1）如果是新用户，则继续填写公司相关信息：
gender;icon;number;isAdmin;postId;postName;departmentId;departmentName。
controller层：接收这些字段，放入UserEntity。
service层：先通过UUID生成OpenID放入UserEntity，再UserMapping.insert(UserEntity)插入一个用户。
（2）如果第三方微信注册过，则提示用户名？手机号？邮箱？“已经注册过，正在进行相关绑定”。
controller层接口：接收上述字段，放入UserEntity。
service层接口：同时先通过UUID生成OpenID，通过当前手机号查询到数据库的users表的id，调用UserMapping.updateFromUser进行数据库的更新


1.2第三方登录注册

1.21注册界面接收以下信息：
Nick_name、性别、头像地址url、地点、手机号、邮件

1.22微信扫码注册逻辑：
扫码后，授权Nick_name、性别、头像地址url、 地点，返回的信息放入WxUsersEntity。弹框提示绑定手机号、邮件，再放入UsersEntity。通过获得授权接收的手机号，服务器请求数据库，查看手机号、邮箱字段是否有记录，来判断是否为新用户。
（1）如果是新用户，继续填写公司相关信息：
gender;icon;number;isAdmin;postId;postName;departmentId;departmentName，放入UsersEntity。
controller层：接收WxUsersEntity，icon字段用微信头像，gender、phone，都放入UsersEntity中。
service层：先去微信网关拿到wxOpenId放入WxUsersEntity，插入UserMapping.insert(UserEntity)，UsersMapping.getUserId()通过phone查询users表，返回users_id放入WxUsersEntity，进行插入再WxUsersMapping.insert()插入一个用户。
（2）如果用户名-密码注册过，则绑定用户名密码注册的表单：
controller层接口：接收WxUsersEntity字段 。
service层：先去微信网关拿到wxOpenId放入WxUsersEntity。WxUsersMapping.getUserId()通过phone查询users表，返回users_id存入WxUsersEntity，再插入WxUsersMapping.insert()。
再次扫码获取授权后可直接登录。

