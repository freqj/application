package com.alex.application.service.impl;

import com.alex.application.model.Employee;
import com.alex.application.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
    @Autowired
    HibernateTemplate template;
    @Override
    public List<Employee> getEmployeeList() {
        logger.info("Getting employee list...");
        List<Employee> list = template.loadAll(Employee.class);
        logger.info("Success");
        return list;
    }

    @Override
    public Employee getEmployeeByName(String name) {
        logger.info("Getting employee by name:" + name);
        DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
        criteria.add(Restrictions.eq("name",name));
        Employee employee = (Employee) template.findByCriteria(criteria).get(0);
        logger.info("Success");
        return employee;
    }

}
