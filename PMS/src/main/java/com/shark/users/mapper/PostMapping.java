package com.shark.users.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.users.entity.PostEntity;

public interface PostMapping {

	/*
	 * 插入一个岗位记录
	 */
	@Insert("INSERT INTO `post` (`name`, `number`) VALUES(#{name}, #{number});")
	void insert(PostEntity postEntity);
	
	/*
	 * 通过name查询id
	 */
	@Select("select id from `post` where name=#{name}")
	int getIdByName(String name);
	
	/*
	 * 通过id查询name
	 */
	@Select("select name from `post` where id=#{id}")
	String getNameById(int id);
	/*
	 * test
	 */
	@Select("select * from `post`")
	List<PostEntity> getAll();
	
	/*
	 * 删除
	 */
	@Delete("delete from `post` where name = #{name}")
	void deletePostByName(String name);
	/*
	 * 20220823-thg
	 * 根据name修改岗位名称
	 */
	@Update("UPDATE `post` SET `name`=#{name} WHERE `name`=#{originName};")
	void updateNameByName(@Param("name")String name, @Param("originName")String originName);
	/*
	 * 修改用户表中岗位名称
	 */
	@Update("UPDATE `user` SET `post_name`=#{postName} WHERE `post_name`=#{originName}")
	void updatePostNameFromUserByOriginName(@Param("postName")String postName, @Param("originName")String originName);
}
