package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlavorMapper {
    void batchInsert(List<DishFlavor> flavors);

    List<DishFlavor> selectByDishId(Long dishId);
}
