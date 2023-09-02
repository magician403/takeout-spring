package com.example.takeout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeout.constant.UserDetailsKey;
import com.example.takeout.dto.AddEmployeeDto;
import com.example.takeout.dto.EmployeeDisplayedDto;
import com.example.takeout.dto.UpdateEmployeeDto;
import com.example.takeout.service.EmployeeService;
import com.example.takeout.util.Result;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
@Valid
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @PostMapping("/add_employee")
    public Result<Void> addEmployee(@RequestBody @NotNull @Valid AddEmployeeDto addEmployeeDto) {
        Map<String, Object> details = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Long userId = (Long) details.get(UserDetailsKey.USER_ID);
        employeeService.addEmployee(addEmployeeDto, userId);
        return Result.ok();
    }

    @GetMapping("/get/{userId}")
    public Result<EmployeeDisplayedDto> getEmployeeById(@PathVariable("userId") @NotNull Long userId) {
        EmployeeDisplayedDto employee = employeeService.getDisplayedInfoByUserId(userId);
        return Result.ok(employee);
    }

    @GetMapping("/page_query")
    public Result<?> pageQuery(@RequestParam("name") @NotNull String name,
                               @RequestParam("current_page_number") @NotNull Integer currentPageNumber,
                               @RequestParam("page_size") @NotNull Integer pageSize) {
        Page<EmployeeDisplayedDto> page = employeeService.pageQueryByName(name, currentPageNumber, pageSize);
        Long totalNumber = page.getTotal();
        List<EmployeeDisplayedDto> employeeDisplayedDto = page.getRecords();
        return Result.ok(Map.of("total_number", totalNumber, "records", employeeDisplayedDto));
    }

    @PostMapping("/update")
    public Result<?> updateEmployeeById(@RequestBody @Valid @NotNull UpdateEmployeeDto updateEmployeeDto) {
        Map<String, Object> details = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Long userId = (Long) details.get(UserDetailsKey.USER_ID);
        employeeService.updateEmployeeByUserId(userId, updateEmployeeDto);
        return Result.ok();
    }
}