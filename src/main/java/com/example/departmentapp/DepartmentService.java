package com.example.departmentapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Department createDepartment(String name) {
        try {
            Department department = new Department(name);
            return departmentRepository.save(department);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Department name must be unique");
        }
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
    }

    public void deleteDepartmentById(Long id) {
        departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        departmentRepository.deleteById(id);
    }

    @Transactional
    public Student enrollStudent(Long departmentId, String studentName, int studentYob) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        Student student = new Student(studentName, studentYob, department);
        department.getStudents().add(student);
        student = studentRepository.save(student);
        return student;
    }

    public List<Student> getAllStudentsByDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        return department.getStudents();
    }

    @Transactional
    public void expelStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Department department = student.getDepartment();
        if (department != null) {
            department.getStudents().remove(student);
        }
        studentRepository.delete(student);
    }
}
