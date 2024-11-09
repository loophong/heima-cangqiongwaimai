package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    Page<Category> queryPage(String name, Integer type);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    void update(Category category);

    void insert(Category category);

    @Select("select * from category where type = #{type}")
    List<Category> selectListByType(Integer type);
}
