package com.vfc.petz.domain.service;

import com.vfc.petz.domain.dto.PageResponse;
import com.vfc.petz.domain.dto.PetRequest;
import com.vfc.petz.domain.dto.PetResponse;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.entity.Pet;
import com.vfc.petz.domain.exception.CustomerNotFoundException;
import com.vfc.petz.domain.exception.EntityBadRequestException;
import com.vfc.petz.domain.exception.PetNotFoundException;
import com.vfc.petz.domain.mappers.PageMapper;
import com.vfc.petz.domain.mappers.PetMapper;
import com.vfc.petz.domain.repository.CustomerRepository;
import com.vfc.petz.domain.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.TimeZone;
import java.util.UUID;

@Service
public class PetService {

    private static final TimeZone SAO_PAULO_TIME_ZONE = TimeZone.getTimeZone("America/Sao_Paulo");

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    private final PetMapper petMapper;
    private final PageMapper pageMapper;

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository, PetMapper petMapper, PageMapper pageMapper) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
        this.petMapper = petMapper;
        this.pageMapper = pageMapper;
    }

    @Transactional
    public PageResponse<PetResponse> listAll(UUID ownerId, String name, Pageable pageable) {

        findCustomerById(ownerId);

        final Page<Pet> petPage = petRepository.findByNameAndOwnerIdAndStatusIsNot(name, ownerId, EntityStatus.EXCLUDED, pageable);

        return pageMapper.sourceToTarget(petPage.map(pet -> petMapper.sourceToTarget(pet, SAO_PAULO_TIME_ZONE)));
    }

    @Transactional
    public PetResponse findById(UUID ownerId, UUID id) {

        final Pet savedPet = findByIdAndOwner(id, ownerId);

        return petMapper.sourceToTarget(savedPet, SAO_PAULO_TIME_ZONE);
    }

    @Transactional
    public PetResponse create(UUID ownerId, PetRequest petRequest) {

        final Customer owner = findCustomerById(ownerId);

        final Pet pet = petMapper.targetToSource(petRequest, SAO_PAULO_TIME_ZONE);
        pet.setOwner(owner);
        pet.setStatus(EntityStatus.ACTIVE);

        owner.getPets().add(pet);

        final Pet savedPet = petRepository.save(pet);

        return petMapper.sourceToTarget(savedPet, SAO_PAULO_TIME_ZONE);
    }

    @Transactional
    public void deleteById(UUID ownerId, UUID petId) {

        final Pet savedPet = findByIdAndOwner(petId, ownerId);

        savedPet.setStatus(EntityStatus.EXCLUDED);
    }

    private Pet findByIdAndOwner(UUID id, UUID ownerId) {

        findCustomerById(ownerId);

        final Pet savedPet = petRepository.findByIdAndStatusIsNot(id, EntityStatus.EXCLUDED)
                .orElseThrow(() -> new PetNotFoundException(id));

        if (!savedPet.getOwner().getId().equals(ownerId)) {
            throw new EntityBadRequestException(String.format("This pet does not belong to this customer (%s)", ownerId.toString()));
        }
        return savedPet;
    }

    private Customer findCustomerById(UUID customerId) {
        return customerRepository.findByIdAndStatusIsNot(customerId, EntityStatus.EXCLUDED)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
