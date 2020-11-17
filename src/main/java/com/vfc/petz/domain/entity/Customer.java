package com.vfc.petz.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tab_customer")
@JsonIgnoreProperties(ignoreUnknown = true, value = "pets")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityStatus status;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();

    @CreatedDate
    @Column(name = "dt_creation", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "dt_last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
