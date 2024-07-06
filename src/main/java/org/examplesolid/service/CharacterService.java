package org.examplesolid.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.examplesolid.client.IRickAndMortyAPI;
import org.examplesolid.model.api.response.CharacterResponse;
import org.examplesolid.model.dto.CharacterDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService implements ICharacters {

    private final IRickAndMortyAPI clientAPI;

    @Override
    public List<CharacterDTO> getCharacters() {
        Optional<CharacterResponse> response = Optional.ofNullable(clientAPI.getRickAndMortyCharacters().block());
        return response.map(characterResponse -> characterResponse.results()
                .stream().map(CharacterDTO::new)
                .sorted(Comparator.comparing(CharacterDTO::getName))
                .toList()).orElseGet(List::of);
    }
}
