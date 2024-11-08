package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * @description: 新增员工
     * @param: employeeDTO
     * @return: com.sky.result.Result
     * @author: hong
     * @date: 2024/11/8 16:59
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工，员工数据：{}", employeeDTO.toString());
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * @description: 分页查询
     * @param: employeePageQueryDTO
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     * @author: hong
     * @date: 2024/11/8 17:00
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询参数：{}", employeePageQueryDTO);
        PageResult result = employeeService.queryPage(employeePageQueryDTO);
        return Result.success(result);
    }

    /**
     * @description: 启用或禁用账号
     * @param: status,id
     * @return: com.sky.result.Result
     * @author: hong
     * @date: 2024/11/8 17:33
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用或禁用账号")
    public Result startOrUpdate(@PathVariable Integer status, Long id){
        log.info("status"+status);
        log.info("id"+id);
        employeeService.startOrUpdate(status,id);
        return Result.success();
    }

    /** 
     * @description: 通过id查询
     * @param: id 
     * @return: com.sky.result.Result<com.sky.entity.Employee> 
     * @author: hong
     * @date: 2024/11/8 18:08
     */ 
    @GetMapping("/{id}")
    @ApiOperation("通过id查询")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("id"+id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * @description: 修改员工信息
     * @param: employeeDTO
     * @return: com.sky.result.Result
     * @author: hong
     * @date: 2024/11/8 18:13
     */
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info(employeeDTO.toString());
        employeeService.update(employeeDTO);
        return Result.success();
    }


}
