package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    PageResult queryPage(CategoryPageQueryDTO categoryPageQueryDTO);

    void remove(Long id);

    void startOrStopCategory(Integer status, Long id);

    void save(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);
}
