package com.emr.emrbackend.controller;

import com.emr.emrbackend.entity.Department;
import com.emr.emrbackend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}) // สำหรับ dev — ต้องปรับเป็น HTTPS ในโปรดักชัน
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * GET /api/departments
     * ดึงรายชื่อแผนกทั้งหมด
     * Optional: ?search=keyword สำหรับค้นหา
     */
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(
            @RequestParam(required = false) String search) {
        
        List<Department> departments;
        if (search != null && !search.isEmpty()) {
            departments = departmentService.searchDepartments(search);
        } else {
            departments = departmentService.getAllDepartments();
        }
        
        return ResponseEntity.ok(departments);
    }

    /**
     * GET /api/departments/{id}
     * ดึงข้อมูลแผนกตาม ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        return department.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/departments
     * สร้างแผนกใหม่
     */
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        try {
            Department createdDepartment = departmentService.createDepartment(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/departments/{id}
     * อัปเดตแผนก
     */
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long id,
            @RequestBody Department departmentDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
            return ResponseEntity.ok(updatedDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/departments/{id}
     * ลบแผนก
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
