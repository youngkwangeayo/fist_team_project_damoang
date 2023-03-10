package com.example.batisproject.mapper.yk;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.batisproject.entity.Category;

@Mapper
public interface Yk_categoryMapper {
    
    @Select("select*from category;")
    @Results(id = "categoryMap", value = {
        @Result(property = "id", column = "c_id"),
        @Result(property = "groupId", column = "cg_id"),
        @Result(property = "lv", column = "c_lv"),
        @Result(property = "name", column = "c_nm"),
        @Result(property = "detailLV", column = "c_d_lv"),
        @Result(property = "detailName", column = "c_d_nm"),
        @Result(property = "parentLv", column = "c_p_lv"),
        @Result(property = "detailParentLv", column = "c_d_p_lv")
    })
    List<Category> getList();

    //카테고리 대분류에서 c_id 값 받기
    @Select("select c_id from category where c_nm= '대분류' && c_d_nm= #{detailName};")
    Long CategoryId(String detailName);

    @Select("select c_d_nm from category where c_id = #{c_id};")
    String getCategoryName(Long c_id);

}



