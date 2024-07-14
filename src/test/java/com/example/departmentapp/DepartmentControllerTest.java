package com.example.departmentapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentControllerTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        departmentRepository.deleteAll();
        // studentRepository.delete(null);
    }

    @Test
    public void testCreateDepartment() {
        String departmentJson = "{\"name\":\"Tech\"}";

        given()
            .contentType(ContentType.JSON)
            .body(departmentJson)
        .when()
            .post("/api/departments")
        .then()
            .statusCode(200)
            .body("name", equalTo("Tech"));
    }
    
    @Test
    public void testCreateDepartmentDuplicate() {
        departmentRepository.save(new Department("Tech"));
        String departmentJson = "{\"name\":\"Tech\"}";
            
        given()
            .contentType(ContentType.JSON)
            .body(departmentJson)
        .when()
            .post("/api/departments")
        .then()
            .statusCode(400);
    }
    
    @Test
    public void testGetAllDepartments() {
        departmentRepository.save(new Department("Tech"));
        departmentRepository.save(new Department("Bio"));

        when()
            .get("/api/departments")
        .then()
            .statusCode(200)
            .body("", hasSize(2))
            .body("name", hasItems("Tech", "Bio"));
    }

    @Test
    public void testGetDepartmentById() {
        Department department = new Department("Tech");
        department = departmentRepository.save(department);

        when()
            .get("/api/departments/{id}", department.getId())
        .then()
            .statusCode(200)
            .body("name", equalTo("Tech"));
    }

    @Test
    public void testGetDepartmentByIdNotExisting() {
        when()
            .get("/api/departments/{id}", 118)
        .then()
            .statusCode(400);
    }

    @Test
    public void testDeleteDepartment() {
        Department department = new Department("Tech");
        department = departmentRepository.save(department);
        Long id = department.getId();
        
        when()
            .delete("/api/departments/{id}", id)
        .then()
            .statusCode(200);
        
        when()
            .get("/api/departments/{id}", id)
        .then()
            .statusCode(400);
    }
    
    @Test
    public void testEnrollStudent() {
        Department department = new Department("Tech");
        department = departmentRepository.save(department);
        Long id = department.getId();
        String studentJson = "{\"name\":\"Name Surname\", \"yob\": 1999}";

        given()
            .contentType(ContentType.JSON)
            .body(studentJson)
        .when()
            .post("/api/departments/{id}/students", id)
        .then()
            .statusCode(200)
            .body("name", equalTo("Name Surname"))
            .body("yob", equalTo(1999));
    }
    
    @Test
    public void testEnrollStudentInvalidDept() {
        String studentJson = "{\"name\":\"Name Surname\", \"yob\": 1999}";

        given()
            .contentType(ContentType.JSON)
            .body(studentJson)
        .when()
            .post("/api/departments/{id}/students", 101)
        .then()
            .statusCode(400);
    }

    @Test
    public void testExpelStudentById() {
        Department department = new Department("Tech");
        department = departmentRepository.save(department);
        Student student = new Student("Full Name", 1999, department);
        department.getStudents().add(student);
        department = departmentRepository.save(department);
        student = studentRepository.save(student);
        Long id = student.getId();        
        when()
            .delete("/api/departments/students/{id}", id)
        .then()
            .statusCode(200);
        
        when()
            .delete("/api/departments/students/{id}", id)
        .then()
            .statusCode(400);
    }    
}
