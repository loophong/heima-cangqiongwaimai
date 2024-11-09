package com.sky.mapper;

import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    void insert(Dish dish);
}
