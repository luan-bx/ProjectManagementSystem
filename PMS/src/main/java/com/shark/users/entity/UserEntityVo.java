package com.shark.users.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEntityVo {
	private int id;
	private String userName;
	private String password;
	private String openId;
	private String phone;
	private String email;
	private String gender;
	private String icon;
	private String number;
//	private String isAdmin;
	private Integer postId;
	private Integer departmentId;
}
