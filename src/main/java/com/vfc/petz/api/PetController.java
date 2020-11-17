package com.vfc.petz.api;

import com.vfc.petz.domain.dto.PageResponse;
import com.vfc.petz.domain.dto.PetRequest;
import com.vfc.petz.domain.dto.PetResponse;
import com.vfc.petz.domain.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/customers/{customerId}/pets")
@Validated
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public PageResponse<PetResponse> listAll(@PathVariable(name = "customerId") UUID customerId,
                                             @RequestParam(value = "name", required = false) String name,
                                             @SortDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        return petService.listAll(customerId, name, pageable);
    }

    @GetMapping("/{petId}")
    public PetResponse findById(@PathVariable(name = "customerId") UUID customerId,
                                @PathVariable(name = "petId") UUID petId) {
        return petService.findById(customerId, petId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponse addPet(@PathVariable(name = "customerId") UUID customerId,
                              @RequestBody @Valid PetRequest petRequest) {
        return petService.create(customerId, petRequest);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePet(@PathVariable(name = "customerId") UUID customerId,
                          @PathVariable(name = "petId") UUID petId) {
        petService.deleteById(customerId, petId);
    }
}
