package org.examplesolid.controller;

import lombok.RequiredArgsConstructor;
import org.examplesolid.service.ICharacters;
import org.examplesolid.model.dto.CharacterDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/solid")
@RequiredArgsConstructor
public class CharactersController {

    private final ICharacters service;

    @GetMapping("/characters")
    public List<CharacterDTO> getCharacters() {
        return service.getCharacters();
    }
}
