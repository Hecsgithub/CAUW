package com.he.dao;

import java.util.List;

import com.he.po.Role;
import com.he.po.StudentBasic;
import com.he.po.Teacher;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
    
// 1.   根据教职工号查信息
    Teacher selectTeacherById(String id); 
    
//2.新增职工以批量方式
    int insertListTeacher(List<Teacher> ts);
    
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
    
    
    //3.批量删除职工
    int deleteListTeacher(List<Teacher> ts);
     
//    <delete id="delAll" parameterType="java.util.List" >
//    delete from bill_type where id in
//    <foreach collection="list" item="ids" open="(" close=")" separator=",">
//      #{ids,jdbcType=VARCHAR}
//    </foreach>
//    </delete>

    
    
   //4.批量修改职工
    int updateListTeacher(List<Teacher> ts);
    
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
    
    //5.根据角色信息查询教师信息
    List<Teacher> getAllTeacherByRole(Role role);
   
    //6查询没有链接用户的教师
   	
   	List<Teacher> selectNoUsersTeacher();
}