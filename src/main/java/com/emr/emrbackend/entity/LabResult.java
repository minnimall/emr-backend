package com.emr.emrbackend.entity;

import com.emr.emrbackend.entity.enums.LabStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "lab_results")
@Getter
@Setter
public class LabResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @Column(name = "test_name", nullable = false, length = 100)
    private String testName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LabStatus status = LabStatus.ORDERED;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt = LocalDateTime.now();

    @Column(name = "tested_at")
    private LocalDateTime testedAt; // nullable จนกว่าจะมีผล

    private String value;

    @Column(length = 20)
    private String unit;

    @Column(name = "reference_range", length = 50)
    private String referenceRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by")
    private User recordedBy;
}
