package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品接口")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * @description: 新增菜品
     * @param: dishDTO
     * @return: com.sky.result.Result
     * @author: hong
     * @date: 2024/11/9 18:54
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.save(dishDTO);
        return Result.success();
    }

    /**
     * @description: 菜品分页查询
     * @param: dishDTO
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     * @author: hong
     * @date: 2024/11/9 19:17
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> save(DishPageQueryDTO dishDTO) {
        log.info("菜品分页查询：{}", dishDTO);
        PageResult page = dishService.queryPage(dishDTO);
        return Result.success(page);
    }

    /**
     * @description: 菜品起售、停售
     * @param: status,id
     * @return: com.sky.result.Result
     * @author: hong
     * @date: 2024/11/9 19:25
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售、停售")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("菜品起售、停售,status：{}，id：{}", status, id);
        dishService.startOrStop(status,id);
        return Result.success();
    }

    /** 
     * @description: 根据id查询菜品信息
     * @param: id 
     * @return: com.sky.result.Result<com.sky.vo.DishVO> 
     * @author: hong
     * @date: 2024/11/9 19:41
     */ 
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品信息")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("菜品id为：{}", id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }


}
