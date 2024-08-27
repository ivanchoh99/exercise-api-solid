package org.examplesolid.domain.port.service;

import org.examplesolid.domain.model.dto.CharacterInfoResponse;
import org.examplesolid.domain.model.dto.CharacterResponse;
import org.springframework.data.domain.Pageable;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface ICharacter {
    List<CharacterInfoResponse> getSortedCharacters();

    List<CharacterResponse> getAllByPagination(Pageable pageable);

    void saveCharactersFromApi();

    CharacterResponse addFunFact(String nameCharacter, String funFact) throws NameNotFoundException;
}
