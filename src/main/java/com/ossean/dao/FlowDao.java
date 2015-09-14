package com.ossean.dao;

import com.ossean.main.Project;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by houxiang on 15/9/12.
 */
public interface FlowDao {

    @Select("SELECT relative_memo_id from recommend_tables WHERE osp_id = #{id}")
    public List<Integer> selData(@Param("id") int osp_id);

    @Insert("insert into `recommend_result_relation`(osp_id,relative_osp_id,relation_weight) values(#{osp_id},#{relative_osp_id},#{relation_weight})")
    public int insertRelation(@Param("osp_id") int osp_id , @Param("relative_osp_id") int relative_osp_id ,@Param("relation_weight") float relation_weight);

    @Select("select max(osp_id) from `recommend_tables` ")
    public int selMax();

    @Select("SELECT id , tags from open_source_projects_tags_is_not_null LIMIT #{begin},#{end}")
    public List<Project> selProjects(@Param("begin") int begin , @Param("end") int end);

    @Insert("insert into `recommend_result`(osp_id,relative_osp_id,simility_weight) values(#{osp_id},#{relative_osp_id},#{relation_weight})")
    public int insertWeight(@Param("osp_id") int osp_id , @Param("relative_osp_id") int relative_osp_id ,@Param("relation_weight") float relation_weight);

    @Select("SELECT relative_memo_id from #{table_name} WHERE osp_id = #{id}")
    public List<Integer> selRelativeMemo(@Param("table_name") String table_name , @Param("id") int id );

}
