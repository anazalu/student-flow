package com.example.departmentapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department.getName());
    }
    
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartmentById(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
    }

    @PostMapping("/{id}/students")
    public Student enrollStudent(@PathVariable Long id, @RequestBody Student student) {
        return departmentService.enrollStudent(id, student.getName(), student.getYob());
    }

    @GetMapping("/{id}/students")
    public List<Student> getAllStudentsByDepartment(@PathVariable Long id) {
        return departmentService.getAllStudentsByDepartment(id);
    }

    @DeleteMapping("/students/{id}")
    public void expelStudentById(@PathVariable long id) {
        departmentService.expelStudent(id);
    }
}
