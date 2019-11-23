package com.he.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.he.po.Menu;
import com.he.po.UserRole;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
//  1.所有菜单选项
    List<Menu> getAllMenu();
    
//    2.根据用户取得菜单
    List<Menu> getMenusByUsersId(Integer id);
    
    //3.新增用户角色关系以批量方式
    int insertListMenu(List<Menu> ms);
    
//    <insert id="insertIntClass" parameterType="java.util.List" >
//	insert
//	into calss(class_id,name,grade,major_id)
//	values
//	<foreach collection="list" item="item" index="index"
//		separator=",">
//		(#{classId,jdbcType=VARCHAR},
//		#{name,jdbcType=VARCHAR},
//		#{grade,jdbcType=VARCHAR},
//        #{majorId,jdbcType=VARCHAR})
//	</foreach>
//</insert>
    
    
    //4.批量删除用户角色关系
    int deleteListMenu(List<Menu> ms);
     
//    <delete id="delAll" parameterType="java.util.List" >
//    delete from bill_type where id in
//    <foreach collection="list" item="ids" open="(" close=")" separator=",">
//      #{ids,jdbcType=VARCHAR}
//    </foreach>
//    </delete>

    
    
   //5.修改用户角色关系
    int updateListMenu(List<Menu> ms);
    
//	<update id="updateInitStudentIDandClass" parameterType="java.util.List">
//	
//    <foreach collection="list" item="item" index="index" open="" close="" separator=",">
//        update 
//        student_status
//         set class_id=#{item.classId,jdbcType=VARCHAR}，
//         student_id=#{item.studentId,jdbcType=VARCHAR}
//    	where
//    	 basic_id=#{item.basicId,jdbcType=INTEGER}
//    </foreach>
//            		
//	</update>
    
    //6.所有菜单，前端显示使用
    List<Menu> selectAllMenu();
    
    
    
//    7.查询拥有与否某权限的菜单 
    List<Menu> selectnoaddrolemenuinfo(@Param("roleId")String roleId,@Param("type")String type);

   //8.查询是否界面父节点为1
    
    Menu selectparentis1(int menuid);
    
    //9根据条件查组件
    Menu selectMenuIdByName(Menu m);
    
}