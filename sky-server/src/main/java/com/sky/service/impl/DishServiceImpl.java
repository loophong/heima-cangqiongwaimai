package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private FlavorMapper flavorMapper;

    @Transactional
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        log.info("dish" + dish);
        dish.setCreateTime(LocalDateTime.now());
        dish.setCreateUser(BaseContext.getCurrentId());
        dish.setUpdateTime(LocalDateTime.now());
        dish.setUpdateUser(BaseContext.getCurrentId());
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

    public PageResult queryPage(DishPageQueryDTO dishDTO) {
        PageHelper.startPage(dishDTO.getPage(), dishDTO.getPageSize());
        Page<DishVO> page = dishMapper.queryPage(dishDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public void startOrStop(Integer status, Long id) {
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
    }

    public DishVO getById(Long id) {
        DishVO dishVO = dishMapper.selectById(id);
        dishVO.setFlavors(flavorMapper.selectByDishId(id));
        return dishVO;
    }

    @Transactional
    public void updateDishAndFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        // 处理口味信息，先删除后新增
        flavorMapper.deleteByDishId(dish.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(e -> {
                e.setDishId(dish.getId());
            });
            flavorMapper.batchInsert(flavors);
        }
    }

    @Override
    public void delete(List<Long> ids) {
        if (ids != null && ids.size() > 0){
            dishMapper.delete(ids);
            flavorMapper.deleteByDishIds(ids);
        }
    }
}
