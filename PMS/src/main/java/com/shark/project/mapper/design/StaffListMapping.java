package com.shark.project.mapper.design;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.design.EleEngineerEntity;
import com.shark.project.entity.design.MecEngineerEntity;
import com.shark.project.entity.design.SofEngineerEntity;

public interface StaffListMapping {

	/*
	 * 插入一个eleEngineer记录
	 */
	@Insert("INSERT INTO `eleEngineer` (`name`) VALUES (#{name});")
	void insertEleEngineer(EleEngineerEntity eleEngineerEntity);
	
	/*
	 * 获取全部eleEngineer名单
	 */
	@Select("select * from `eleEngineer`")
	List<EleEngineerEntity> getEleEngineerAll();
	
	/*
	 * 插入一个mecEngineer记录
	 */
	@Insert("INSERT INTO `mecEngineer` (`name`) VALUES (#{name});")
	void insertMecEngineer(MecEngineerEntity mecEngineerEntity);
	
	/*
	 * 获取全部mecEngineer名单
	 */
	@Select("select * from `mecEngineer`")
	List<MecEngineerEntity> getMecEngineerAll();
	
	/*
	 * 插入一个sofEngineer记录
	 */
	@Insert("INSERT INTO `sofEngineer` (`name`) VALUES (#{name});")
	void insertSofEngineer(SofEngineerEntity sofEngineerEntity);
	
	/*
	 * 获取全部sofEngineer名单
	 */
	@Select("select * from `sofEngineer`")
	List<SofEngineerEntity> getSofEngineerAll();
}
