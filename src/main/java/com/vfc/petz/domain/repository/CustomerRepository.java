package com.vfc.petz.domain.repository;

import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.EntityStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("!test")
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Customer c WHERE (c.id = :id) and (c.status <> :status)")
    Optional<Customer> findByIdAndStatusIsNot(UUID id, EntityStatus status);

    @Query("SELECT c FROM Customer c WHERE (:name is null or UPPER(c.name) like :name%) and (c.status <> :status)")
    Page<Customer> findByNameAndStatusIsNot(String name, EntityStatus status, Pageable pageable);
}
