package com.emr.emrbackend.service;

import com.emr.emrbackend.entity.Department;
import com.emr.emrbackend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /**
     * ดึงรายชื่อแผนกทั้งหมด
     */
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * ดึงแผนกตาม ID
     */
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    /**
     * สร้างแผนกใหม่
     * - ตรวจสอบว่าชื่อไม่ซ้ำ
     */
    @Transactional
    public Department createDepartment(Department department) {
        // ตรวจสอบว่าชื่อ department ไม่ซ้ำ
        departmentRepository.findByName(department.getName())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Department name already exists: " + department.getName());
                });
        
        return departmentRepository.save(department);
    }

    /**
     * อัปเดตแผนก
     */
    @Transactional
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + id));

        // ตรวจสอบว่าชื่อใหม่ไม่ซ้ำกับแผนกอื่น (ยกเว้นตัวเอง)
        if (!department.getName().equals(departmentDetails.getName())) {
            departmentRepository.findByName(departmentDetails.getName())
                    .ifPresent(existing -> {
                        throw new IllegalArgumentException("Department name already exists: " + departmentDetails.getName());
                    });
        }

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());

        return departmentRepository.save(department);
    }

    /**
     * ลบแผนก
     */
    @Transactional
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }

    /**
     * ค้นหาแผนกจากคีย์เวิร์ด
     */
    public List<Department> searchDepartments(String keyword) {
        return departmentRepository.findByNameContainingIgnoreCase(keyword);
    }
}