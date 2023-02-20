package com.shark.base.mapper;

import org.apache.ibatis.annotations.Select;

import com.shark.base.dto.ProjectDto;

public interface ProjectDtoMapping {

	/**
	 * 根据project的id查询ProjectDto所需要的参数
	 * @param id
	 * @return
	 */
	@Select("SELECT p.name, s.status_name, p.created as submitTime, r.company_name\n"
			+ "FROM project p, pro_status s, pro_request r\n"
			+ "WHERE p.status_id = s.id AND p.request_id = r.id AND p.id = #{id};")
	ProjectDto getDtoById(int id);
	
	/**
	 * 根据project的name查询ProjectDto所需要的参数
	 * @param name
	 * @return
	 */
	@Select("SELECT p.name, s.status_name, p.created as submitTime, r.company_name\n"
			+ "FROM project p, pro_status s, pro_request r\n"
			+ "WHERE p.status_id = s.id AND p.request_id = r.id AND p.name = #{name};")
	ProjectDto getDtoByName(String name);
	
}
