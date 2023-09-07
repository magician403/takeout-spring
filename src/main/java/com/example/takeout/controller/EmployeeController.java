package com.example.takeout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeout.dto.EmployeeDto;
import com.example.takeout.service.EmployeeService;
import com.example.takeout.util.Result;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
@Validated
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @PostMapping("/add_employee")
    public Result<?> addEmployee(@RequestBody @Validated EmployeeDto employeeDto) {
        employeeService.addEmployee(employeeDto);
        return Result.ok();
    }

    @GetMapping("/get/{userId}")
    public Result<?> getEmployeeById(@PathVariable("userId") @NotNull Long userId) {
        EmployeeDto employee = employeeService.getEmployeeByUserId(userId);
        return Result.ok(employee);
    }

    @GetMapping("/page_query")
    public Result<?> pageEmployeeByName(@RequestParam("name") @NotNull String name,
                                        @RequestParam("current_page_number") @NotNull Integer currentPageNumber,
                                        @RequestParam("page_size") @NotNull Integer pageSize) {
        Page<EmployeeDto> page = employeeService.pageEmployeeByName(name, currentPageNumber, pageSize);
        Long totalNumber = page.getTotal();
        List<EmployeeDto> employeeDtoList = page.getRecords();
        return Result.ok(Map.of("total_number", totalNumber, "records", employeeDtoList));
    }

    @PostMapping("/update")
    public Result<?> updateEmployeeById(@RequestBody @Valid @NotNull EmployeeDto employeeDto) {
        employeeService.updateEmployeeByUserId(employeeDto);
        return Result.ok();
    }
}