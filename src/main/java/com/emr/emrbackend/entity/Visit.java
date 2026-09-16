package com.emr.emrbackend.entity;

import com.emr.emrbackend.entity.enums.VisitStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment; // nullable, walk-in ได้

    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VisitStatus status = VisitStatus.WAITING;

    @Column(name = "queue_number")
    private Integer queueNumber;

    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;

    @Column(name = "chief_complaint", length = 500)
    private String chiefComplaint;

    @Column(length = 1000)
    private String diagnosis;
}
