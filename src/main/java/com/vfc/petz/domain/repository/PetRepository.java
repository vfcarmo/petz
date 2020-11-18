package com.vfc.petz.domain.repository;

import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.entity.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("!test")
public interface PetRepository extends CrudRepository<Pet, UUID> {

    Optional<Pet> findByIdAndStatusIsNot(UUID id, EntityStatus status);

    @Query("SELECT p FROM Pet p WHERE p.owner.id = :ownerId and (:name is null or UPPER(p.name) LIKE :name%) and (p.status <> :status)")
    Page<Pet> findByNameAndOwnerIdAndStatusIsNot(String name, UUID ownerId, EntityStatus status, Pageable pageable);
}
