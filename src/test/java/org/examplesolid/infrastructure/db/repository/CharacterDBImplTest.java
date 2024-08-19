package org.examplesolid.infrastructure.db.repository;

import org.assertj.core.api.Assertions;
import org.examplesolid.domain.model.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.repository.impl.CharacterDBImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.IOException;
import java.util.List;

@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.jpa.hibernate.ddl-auto=create-drop"})
class CharacterDBImplTest {

    @SpyBean
    private CharacterDBImpl repository;

    //? Persist characters from the api in the DataBase
    @Test
    void whenPersistCharacter_ThenPersistedCorrectly() {
        //* Arrange
        CharacterEntity character1 = new CharacterEntity("character1", List.of("1", "2", "3"));
        //* Act
        CharacterEntity persisted = repository.save(character1);
        //* Assert
        Assertions.assertThat(persisted)
                .as("Check if the persisted is a instance of CharacterEntity")
                .isInstanceOf(CharacterEntity.class)
                .as("Check character don't have null fields")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void whenPersistMultipleCharactersWithEpisodes_ThenPersistedCorrectly() {
        //* Arrange
        CharacterEntity character1 = new CharacterEntity("character1", List.of("1", "2", "3"));
        CharacterEntity character2 = new CharacterEntity("character2", List.of("2", "6", "7"));
        CharacterEntity character3 = new CharacterEntity("character3", List.of("1", "5"));
        List<CharacterEntity> characters = List.of(character1, character2, character3);
        //* Act
        List<CharacterEntity> persisted = repository.saveAll(characters);
        //* Assert
        Assertions.assertThat(persisted).
                as("Check that the result of persisted is not empty")
                .isNotEmpty()
                .as("All characters with their respective episodes was persisted correctly")
                .extracting(character -> character.getEpisodes().iterator().next().getNumber()).isNotNull();
    }

    @Test
    void whenNameCharacterIsRepeat_ThenThrowException() {
        //* Arrange
        CharacterEntity character1 = new CharacterEntity("character1", List.of("1", "2", "3"));
        CharacterEntity character2 = new CharacterEntity("character1", List.of("1", "2", "3"));
        List<CharacterEntity> toPersist = List.of(character1, character2);
        //* Act & Assert
        Assertions.assertThatExceptionOfType(IOException.class).isThrownBy(() -> repository.saveAll(toPersist));
    }

    @Test
    void whenNameCharacterIsNull_ThenThrowException() {
    }

    @Test
    void whenPersistCharacterCorrectly_ThenReturnCharacterEntityWithUUID() {
    }
}