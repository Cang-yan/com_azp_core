package com.azp.core.sys.service;

import com.azp.core.sys.model.Department;
import com.azp.core.sys.model.DepartmentFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class DepartmentExtendsService {
    @Autowired
    private DepartmentService departmentService;

    public List<Department> getList() {
        return departmentService.getListByFilter(new DepartmentFilterMapper()).stream().filter(department -> !department.getName().equals("Visitor")).collect(Collectors.toList());
    }

}
