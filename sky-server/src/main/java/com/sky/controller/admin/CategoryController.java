package com.sky.controller.admin;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类管理接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @description: 分类管理-分页查询
     * @param: categoryPageQueryDTO
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     * @author: hong
     * @date: 2024/11/8 21:05
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询参数：{}", categoryPageQueryDTO);
        PageResult result = categoryService.queryPage(categoryPageQueryDTO);
        return Result.success(result);
    }

    /**
     * @description: 根据id删除分类
     * @param: id
     * @return: com.sky.result.Result
     * @author: hong
     * @date: 2024/11/8 21:23
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result remove(Long id) {
        log.info("删除id：{}", id);
        categoryService.remove(id);
        return Result.success();
    }

    /**
     * @description: 启用或禁用分类
     * @param: status, id
     * @return: com.sky.result.Result
     * @author: hong
     * @date: 2024/11/8 21:32
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用或禁用分类")
    public Result startOrStopCategory(@PathVariable Integer status, Long id) {
        log.info("启用或禁用分类,id：{}，status：{}", status, id);
        categoryService.startOrStopCategory(status, id);
        return Result.success();
    }
}
