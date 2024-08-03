package org.examplesolid.infrastructure.mapper;

import org.examplesolid.domain.model.dto.Character;
import org.examplesolid.domain.model.dto.CharacterSimple;
import org.examplesolid.domain.model.api.CharacterAPI;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.domain.port.mapper.ICharacterMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper implements ICharacterMapper {

    private final ModelMapper mapper;

    @Autowired
    public CharacterMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Character entityToDomain(CharacterEntity characterEntity) {
        return mapper.map(characterEntity, Character.class);
    }

    @Override
    public CharacterEntity domainToEntity(Character character) {
        return mapper.map(character, CharacterEntity.class);
    }

    @Override
    public CharacterSimple apiToSimple(CharacterAPI characterAPI) {
        return mapper.map(characterAPI,CharacterSimple.class);
    }

    @Override
    public CharacterEntity apiToEntity(CharacterAPI characterAPI) {
        return mapper.map(characterAPI, CharacterEntity.class);
    }
}
