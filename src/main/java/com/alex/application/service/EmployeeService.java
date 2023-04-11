package com.alex.application.service;

import com.alex.application.model.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EmployeeService {

     List<Employee> getEmployeeList();
     Employee getEmployeeByName(String name);
}
