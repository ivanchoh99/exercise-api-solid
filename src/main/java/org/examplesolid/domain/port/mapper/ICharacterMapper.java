package org.examplesolid.domain.port.mapper;

import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.dto.CharacterInfoResponse;
import org.examplesolid.domain.model.dto.CharacterResponse;
import org.examplesolid.domain.model.entity.CharacterEntity;

public interface ICharacterMapper {
    CharacterResponse entityToDomain(CharacterEntity characterEntity);
    CharacterInfoResponse apiToSimple(CharacterAPI characterAPI);
    CharacterEntity apiToEntity(CharacterAPI characterAPI);
}
