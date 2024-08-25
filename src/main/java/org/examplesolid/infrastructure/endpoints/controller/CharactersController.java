package org.examplesolid.infrastructure.endpoints.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterBaseInformation;
import org.examplesolid.domain.port.service.ICharacter;
import org.examplesolid.infrastructure.endpoints.dto.request.NewFunFactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;

@Tag(name = "Character", description = "Using Rick and Morty API practice some exercise and endpoints")
@RestController
@RequestMapping("/api/solid")
public class CharactersController {

    private final ICharacter service;

    @Autowired
    public CharactersController(ICharacter service) {
        this.service = service;
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterBaseInformation>> getCharacters() {
        return ResponseEntity.ok(service.getCharactersFromApiAndSort());
    }

    @GetMapping("/characters-db")
    public ResponseEntity<List<Character>> gerCharactersDB(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "100", required = false) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllCharactersFromDB(pageable));
    }

    @PostMapping("/persist-characters")
    public ResponseEntity<Void> persistCharactersFromApi() {
        service.saveCharactersFromApi();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add/fun-fact")
    public ResponseEntity<Character> persistNewFunFact(@RequestBody NewFunFactRequest newFunFact) throws NameNotFoundException {
        return ResponseEntity.ok(service.addFunFact(newFunFact.nameCharacter(), newFunFact.funFact()));
    }
}
