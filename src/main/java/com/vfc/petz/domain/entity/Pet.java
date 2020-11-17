package com.vfc.petz.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tab_pet")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetType type;

    @Column(nullable = false)
    private String observation;

    @Column(name = "dt_birthdate", nullable = false)
    private LocalDateTime birthdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Customer owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityStatus status;

    @CreatedDate
    @Column(name = "dt_creation", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "dt_last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
