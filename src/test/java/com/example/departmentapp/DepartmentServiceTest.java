package com.example.departmentapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDepartment() {
        Department mockedDepartment = new Department("Tech");
        when(departmentRepository.save(any(Department.class))).thenReturn(mockedDepartment);

        Department createdDepartment = departmentService.createDepartment("Tech");

        assertNotNull(createdDepartment);
        assertEquals("Tech", createdDepartment.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    public void testGetAllDepartments() {
        List<Department> mockedDepartments = Arrays.asList(new Department("Tech"), new Department("Bio"));
        when(departmentRepository.findAll()).thenReturn(mockedDepartments);

        List<Department> result = departmentService.getAllDepartments();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetDepartmentById() {
        Department mockedDepartment = new Department("Math");
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));

        Department foundDepartment = departmentService.getDepartmentById(1L);
        
        assertNotNull(foundDepartment);
        assertEquals("Math", foundDepartment.getName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteDepartment() {
        Department mockedDepartment = new Department("Tech");
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));

        doNothing().when(departmentRepository).deleteById(anyLong());
        
        departmentService.deleteDepartmentById(1L);

        verify(departmentRepository, times(1)).deleteById(1L);
    }    
}
