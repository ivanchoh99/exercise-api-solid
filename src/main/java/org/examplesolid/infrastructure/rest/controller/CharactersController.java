package org.examplesolid.infrastructure.rest.controller;

import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.port.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solid")
public class CharactersController {

    private final ICharacterService service;

    @Autowired
    public CharactersController(ICharacterService service) {
        this.service = service;
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterSimple>> getCharacters() {
        return ResponseEntity.ok(service.getCharactersFromApiAndSort());
    }

    @GetMapping("/charaters-db")
    public ResponseEntity<List<Character>> gerCharactersDB(
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @RequestParam(name = "size",defaultValue = "3")Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllCharactersFromDB(pageable));
    }

    @GetMapping("/character/{name}")
    public ResponseEntity<Character> getCharacterByName(@PathVariable(name = "name") String name){
        return ResponseEntity.ok(service.getCharacterFromDB(name));
    }

    @PostMapping("/persist-characters")
    public ResponseEntity<Void> persistCharactersFromApi(){
        service.saveCharactersFromApi();
        return ResponseEntity.ok().build();
    }
}
