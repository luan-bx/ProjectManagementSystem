package com.shark.users.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.users.entity.WxUsersEntity;

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
	@Update("UPDATE `wxusers` SET user_id = #{userId} where phone=#{phone};")
	void updateUserIdByPhone(@Param("userId") int userId, String phone);
	
	/*
	 * test
	 */
	@Select("select * from `wxusers`")
	List<WxUsersEntity> getAll();
}
