package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void save(DishDTO dishDTO);

    PageResult queryPage(DishPageQueryDTO dishDTO);

    void startOrStop(Integer status, Long id);

    DishVO getById(Long id);

    void updateDishAndFlavor(DishDTO dishDTO);

    void delete(List<Long> ids);
}
