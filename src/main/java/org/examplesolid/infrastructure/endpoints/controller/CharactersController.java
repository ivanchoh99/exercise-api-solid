package org.examplesolid.infrastructure.endpoints.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.examplesolid.domain.abstraction.service.ICharacter;
import org.examplesolid.infrastructure.endpoints.dto.request.FactRequest;
import org.examplesolid.infrastructure.endpoints.dto.response.CharacterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;

@Tag(name = "ApiResponse", description = "Using Rick and Morty API practice some exercise and endpoints")
@RestController
@RequestMapping("/api/characters")
public class CharactersController {

    private final ICharacter service;

    @Autowired
    public CharactersController(ICharacter service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<CharacterResponse>> getCharactersPagination(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getSortedCharacters(pageable));
    }

    @PostMapping("/fact")
    public ResponseEntity<CharacterResponse> persistFact(@RequestBody FactRequest newFact) throws NameNotFoundException {
        return ResponseEntity.ok(service.addFact(newFact.nameCharacter(), newFact.funFact()));
    }
}
