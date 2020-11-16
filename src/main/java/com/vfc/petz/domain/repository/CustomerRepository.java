package com.vfc.petz.domain.repository;

import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.EntityStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("!test")
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByIdAndStatusIsNot(UUID id, EntityStatus status);

    @Query("SELECT c FROM Customer c WHERE (:name is null or c.name = :name) and (c.status <> :status)")
    Page<Customer> findByNameAndStatusIsNot(String name, EntityStatus status, Pageable pageable);
}
