package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private FlavorMapper flavorMapper;

    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        log.info("dish" + dish);
        dishMapper.insert(dish);

        Long dishId = dish.getId();
        log.info("DishId" + dishId);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        log.info("flavors" + flavors);
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(e -> {
                e.setDishId(dishId);
            });
            flavorMapper.batchInsert(flavors);

        }


    }
}
