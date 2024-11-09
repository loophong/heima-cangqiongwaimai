package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult queryPage(CategoryPageQueryDTO categoryPageQueryDTO);

    void remove(Long id);

    void startOrStopCategory(Integer status, Long id);

    void save(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    List<Category> getTypeList(Integer type);
}
