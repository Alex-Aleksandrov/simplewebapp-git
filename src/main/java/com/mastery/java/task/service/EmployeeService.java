package com.mastery.java.task.service;


import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    public Employee findById(Integer id) {
        return employeeDao.findId(id);
    }

    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    public void deleteEmployee(Integer id) {
        employeeDao.deleteEmployee(id);
    }

    public void newEmployee(Employee newEmployee) {
        employeeDao.newEmployee(newEmployee);
    }

    public void updateEmployee(Employee employee, Integer id) {
        employeeDao.updateEmployee(employee, id);
    }
}

