package com.example.takeout;

import com.example.takeout.controller.EmployeeController;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TakeoutSpringApplicationTests {
    @Resource
    private EmployeeController employeeController;

    @Test
    void contextLoads() {

    }

}
