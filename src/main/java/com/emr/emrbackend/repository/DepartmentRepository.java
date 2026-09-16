package com.emr.emrbackend.repository;

import com.emr.emrbackend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    // ค้นหาแผนกจากชื่อ (สำหรับตรวจสอบว่าชื่อซ้ำไหม)
    Optional<Department> findByName(String name);
    
    // ค้นหาแผนกที่มีชื่อบางส่วน (สำหรับฟิลเตอร์)
    List<Department> findByNameContainingIgnoreCase(String keyword);
}
