package com.shark.backendSystem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.backendSystem.entity.CompanyNameEntity;
import com.shark.backendSystem.entity.DesignEleFieldEntity;
import com.shark.backendSystem.entity.DesignMecFieldEntity;
import com.shark.project.entity.ProjectEntity;
import com.shark.users.entity.UserEntity;

public interface BackSysMapping {
	/*
	 * 删除用户
	 */
	@Delete("delete from `user` where user_name = #{userName}")
	void deleteUserByUserName(String userName);
	
	/*
	 * 全部用户信息
	 */
	@Select("select * from `user`;")
	List<UserEntity> getAll();
	
	/*
	 * 20220823-thg,新用户信息
	 */
	@Select("SELECT * FROM `user` WHERE `post_id`=16;")
	List<UserEntity> getNew();
	
	/*
	 * 更新用户信息
	 */
	@Update("UPDATE `user` SET  `phone`=#{phone}, `email`=#{email} ,`number`=#{number}, "
			+ "`post_id`=#{postId}, `post_name`=#{postName} , `department_id`=#{departmentId}, `department_name`=#{departmentName} "+ "WHERE `user_name`=#{originUserName};")
	void updateUserById( @Param("phone") String phone, @Param("email") String email, @Param("number") String number, 
			@Param("postId") int postId, @Param("postName")String postName, @Param("departmentId") int departmentId, @Param("departmentName")String departmentName, @Param("originUserName") String originUserName);
	
	/*
	 * 更新公司名称
	 */
	@Update("UPDATE `company_name` SET `company_name1`=#{companyName1}, `company_name2`=#{companyName2}"+ "WHERE `id`=#{id};")
	void updateCompanyName(CompanyNameEntity companyNameEntity);
	
	/*
	 * 查询公司名称
	 */
	@Select("select * from `company_name` where `id` = #{id}")
	CompanyNameEntity getCompanyNameById(int id);
	/*
	 * 机械设计字段名
	 */
	@Select("SELECT * FROM `design_mec_field` WHERE `id` = #{id};")
	DesignMecFieldEntity getDesignMecField(int id);
	/*
	 * 更新机械设计字段名
	 */
	@Update("UPDATE `design_mec_field` SET `d_mec_bom_url`=#{dMecBomUrl}, `d_mec_threeD_url`=#{dMecThreeDUrl}, "
			+ "`d_mec_twoD_url`=#{dMecTwoDUrl}, `d_mec_gas_url`=#{dMecGasUrl}, `d_mec_comp_url`=#{dMecCompUrl}, "
			+ "`d_mec_prof_url`=#{dMecProfUrl},`d_mec_vul_list_url`=#{dMecVulListUrl}, `d_mec_vul_draw_url`=#{dMecVulDrawUrl} "
			+ "WHERE `id`=#{id};")
	void updateDesignMecField(DesignMecFieldEntity designMecFieldEntity);

	/*
	 * 电气设计字段名
	 */
	@Select("SELECT * FROM `design_ele_field` WHERE `id` = #{id};")
	DesignEleFieldEntity getDesignEleField(int id);
	
	/*
	 * 更新电气设计字段名
	 */
	@Update("UPDATE `design_ele_field` SET `d_ele_bom_url`=#{dEleBomUrl}, `d_ele_graph_url`=#{dEleGraphUrl}, "
			+ " `d_ele_list_url`=#{dEleListUrl} "+"WHERE `id`=#{id};")
	void updateDesignEleField(DesignEleFieldEntity designEleFieldEntity);
	/*
	 * 
	 */
}
