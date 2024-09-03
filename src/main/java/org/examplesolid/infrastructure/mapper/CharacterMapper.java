package org.examplesolid.infrastructure.mapper;

import lombok.extern.slf4j.Slf4j;
import org.examplesolid.application.model.api.CharacterApi;
import org.examplesolid.application.model.response.CharacterResponse;
import org.examplesolid.application.util.StringUtility;
import org.examplesolid.domain.model.Character;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.entity.EpisodeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CharacterMapper {

    private Integer episodeUrlToNumber(String url) {
        String numberString = url.replace("https://rickandmortyapi.com/api/episode/", "");
        return Integer.parseInt(numberString);
    }

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
        Set<Integer> episodesNumber = entity.getEpisodes().stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet());
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
        Set<Integer> numberEpisodes = characterApi.getEpisode().stream().map(this::episodeUrlToNumber).collect(Collectors.toSet());
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
        Set<Integer> episodes = entity.getEpisodes().stream().map(EpisodeEntity::getNumberEpisode).collect(Collectors.toSet());
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