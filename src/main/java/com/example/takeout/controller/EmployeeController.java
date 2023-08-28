package com.example.takeout.controller;

import cn.hutool.core.lang.Tuple;
import com.example.takeout.constant.UserDetailsKeyConstant;
import com.example.takeout.dto.AddEmployeeDto;
import com.example.takeout.dto.EmployeeDisplayedDto;
import com.example.takeout.service.EmployeeService;
import com.example.takeout.util.Result;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        Long userId = (Long) details.get(UserDetailsKeyConstant.USER_ID);
        employeeService.addEmployee(addEmployeeDto, userId, userId);
        return Result.ok();
    }

    @GetMapping("/get/{userId}")
    public Result<EmployeeDisplayedDto> getEmployeeById(@PathVariable("userId") @NotNull Long userId) {
        EmployeeDisplayedDto employee = employeeService.getEmployeeById(userId);
        return Result.ok(employee);
    }

    @GetMapping("/page_query")
    public Result<?> pageQuery(@RequestParam("name") @NotNull String name,
                               @RequestParam("current_page_number") @NotNull Integer currentPageNumber,
                               @RequestParam("page_size") @NotNull Integer pageSize) {
        Tuple pageQueryResult = employeeService.pageQueryByName(name, currentPageNumber, pageSize);
        Long totalNumber = pageQueryResult.get(0);
        List<EmployeeDisplayedDto> employeeDisplayedDtos = pageQueryResult.get(1);
        return Result.ok(Map.of("total_number", totalNumber, "records", employeeDisplayedDtos));
    }
}