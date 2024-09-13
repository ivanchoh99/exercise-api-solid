package org.examplesolid.application.mapper;

import lombok.extern.slf4j.Slf4j;
import org.examplesolid.application.util.StringUtility;
import org.examplesolid.domain.model.Character;
import org.examplesolid.infrastructure.api.model.CharacterApi;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.entity.EpisodeEntity;
import org.examplesolid.infrastructure.endpoints.dto.response.CharacterResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CharacterMapper {

    private final ModelMapper mapper;

    @Autowired
    public CharacterMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    private final Function<String, Integer> episodeUrlToNumber = url -> Integer.parseInt(url.replace("https://rickandmortyapi.com/api/episode/", ""));

    private final Function<Set<EpisodeEntity>, Set<Integer>> episodeEntitysToSetNumber = episodeEntities -> {
        if (episodeEntities == null) return new HashSet<>();
        return episodeEntities.stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet());
    };

    private Set<EpisodeEntity> setNumberToSetEpisodeEntity(Set<Integer> episodes, CharacterEntity characterEntity) {
        Set<EpisodeEntity> episodeEntities = episodes.stream().map(number -> {
            EpisodeEntity episodeEntity = new EpisodeEntity();
            episodeEntity.setNumberEpisode(number);
            return episodeEntity;
        }).collect(Collectors.toSet());
        episodeEntities.forEach(episodeEntity -> episodeEntity.setCharacter(characterEntity));
        return episodeEntities;
    }

    public CharacterResponse entityToResponse(CharacterEntity entity) {
        Set<Integer> episodesNumber = episodeEntitysToSetNumber.apply(entity.getEpisodes());
        List<String> facts = StringUtility.stringToList(entity.getFacts());
        CharacterResponse response = new CharacterResponse();
        response.setName(entity.getName());
        response.setGender(entity.getGender());
        response.setSpecies(entity.getSpecie());
        response.setLocation(entity.getLocation());
        response.setOrigin(entity.getOrigin());
        response.setStatus(entity.getStatus());
        response.setEpisodes(episodesNumber);
        response.setFacts(facts);
        return response;
    }

    public CharacterEntity apiToEntity(CharacterApi characterApi) {
        CharacterEntity entity = new CharacterEntity();
        Set<Integer> numberEpisodes = characterApi.getEpisode() != null ?
                characterApi.getEpisode().stream().map(episodeUrlToNumber).collect(Collectors.toSet()) :
                Collections.emptySet();
        Set<EpisodeEntity> episodesEntitys = setNumberToSetEpisodeEntity(numberEpisodes, entity);
        entity.setName(characterApi.getName());
        entity.setGender(characterApi.getGender());
        entity.setSpecie(characterApi.getSpecies());
        entity.setLocation(characterApi.getLocation().name());
        entity.setOrigin(characterApi.getOrigin().name());
        entity.setStatus(characterApi.getStatus());
        entity.setEpisodes(episodesEntitys);
        return entity;
    }

    public Character entityToDomain(CharacterEntity entity) {
        Character character = new Character();
        character.setId(entity.getUuid().toString());
        character.setName(entity.getName());
        character.setGender(entity.getGender());
        character.setSpecie(entity.getSpecie());
        character.setLocation(entity.getLocation());
        character.setOrigin(entity.getOrigin());
        character.setStatus(entity.getStatus());
        Set<Integer> episodes = entity.getEpisodes() != null ? entity.getEpisodes().stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet()) : new HashSet<>();
        List<String> facts = StringUtility.stringToList(entity.getFacts());
        character.setEpisodes(episodes);
        character.setFacts(facts);
        return character;
    }

    public CharacterEntity domainToEntity(Character character) {
        CharacterEntity entity = new CharacterEntity();
        entity.setUuid(UUID.fromString(character.getId()));
        entity.setName(character.getName());
        entity.setGender(character.getGender());
        entity.setSpecie(character.getSpecie());
        entity.setLocation(character.getLocation());
        entity.setOrigin(character.getOrigin());
        entity.setStatus(character.getStatus());
        Set<EpisodeEntity> episodes = this.setNumberToSetEpisodeEntity(character.getEpisodes(), entity);
        entity.setEpisodes(episodes);
        entity.setFacts(StringUtility.listToString(character.getFacts()));
        return entity;
    }
}