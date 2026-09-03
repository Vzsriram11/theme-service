package com.sriram.themevest.controller;

import com.sriram.themevest.dto.Employee;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/v1/employees")
public class GenericController {

@GetMapping
public List<Employee> getEmployees()
{
    System.out.println("Get all  Employees");
    Employee employee1 = Employee.builder()
            .name("Sriram")
            .age(42)
            .email("abc")
            .build();

    Employee employee2 = Employee.builder()
            .name("Keerthy")
            .age(36)
            .email("def")
            .build();
    List<Employee> employees = List.of(employee1,employee2);
    return employees;

}

    @PostMapping
    public  Employee createEmployee( @RequestBody @Valid Employee employee)
    {
        System.out.println("Creating Employee:"+employee);
        return  employee;

    }

}
