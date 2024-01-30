package com.girludev.demoparkapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "vacancies")
@EntityListeners(AuditingEntityListener.class)
public class Vacancy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "status", nullable = false, length = 4)
    @Enumerated(EnumType.STRING)
    private StatusVacancy status;

    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime creation_date;
    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime dmodification_date;
    @CreatedBy
    @Column(name = "created_by")
    private String created_by;
    @LastModifiedBy
    @Column(name = "modified_by")
    private String modified_by;
    public enum StatusVacancy {
        FREE,
        BUSY
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(id, vacancy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
