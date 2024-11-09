package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    void insert(Dish dish);

    Page<DishVO> queryPage(DishPageQueryDTO dishDTO);
}
