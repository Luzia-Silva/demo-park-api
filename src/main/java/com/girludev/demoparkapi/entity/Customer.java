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

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @OneToOne // Chave estrangeira de um para um
    @JoinColumn(name = "id_user", nullable = false) // Join na tabela user
    private User user;

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
}
