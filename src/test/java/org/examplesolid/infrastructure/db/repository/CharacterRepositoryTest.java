package org.examplesolid.infrastructure.db.repository;

import org.assertj.core.api.Condition;
import org.examplesolid.infrastructure.db.entity.CharacterEntity;
import org.examplesolid.infrastructure.db.repository.jpa.CharacterJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.show-sql=true"
})
@Import(CharacterRepository.class)
class CharacterRepositoryTest {

    @Autowired
    private CharacterRepository repository;
    @Autowired
    private CharacterJpaRepository characterJpaRepository;

    @Test
    void givenIncompleteCharacter_whenPersist_thenThrowException() {
        //*Arrange
        CharacterEntity entity = new CharacterEntity();
        //* Act & Assert
        assertThatThrownBy(() -> repository.save(entity)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenListCharacters_whenPersistAll_thenSaveCorrectly() {
        //* Arrange
        CharacterEntity character1 = new CharacterEntity();
        CharacterEntity character2 = new CharacterEntity();
        character1.setName("testName1");
        character2.setName("testName2");
        List<CharacterEntity> characters = List.of(character1, character2);
        //* Act
        List<CharacterEntity> persisted = repository.saveAll(characters);
        //* Arrange
        Condition<CharacterEntity> uuidNotNull = new Condition<>(c -> c.getUuid() != null, "Entity persisted correctly");
        IntStream.range(0, characters.size()).forEach(i -> {
            assertThat(persisted.get(i))
                    .as("The UUID is not Null")
                    .has(uuidNotNull)
                    .as("Name correctly saved")
                    .hasFieldOrPropertyWithValue("name", characters.get(i).getName());
        });
    }

    @Test
    void givenListCharactersWithNameRepeated_whenPersistAll_thenThrowException() {
        //*Arrange
        String name = "testName";
        CharacterEntity character1 = new CharacterEntity();
        CharacterEntity character2 = new CharacterEntity();
        character1.setName(name);
        character2.setName(name);
        List<CharacterEntity> characters = List.of(character1, character2);
        //* Act & Assert
        assertThatThrownBy(() -> repository.saveAll(characters))
                .as("Don't persist characters with same name when persist multiple characters")
                .withFailMessage("The character was persisted")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void givenNameOfCharacter_whenSearchInDbAndFoundOne_thenReturnCharacter() throws NameNotFoundException {
        //* Arrange
        CharacterEntity entity = new CharacterEntity();
        entity.setName("ivan");
        CharacterEntity persisted = repository.save(entity);
        //* Act
        CharacterEntity characterFound = repository.findByName(entity.getName());
        //* Assert
        assertThat(characterFound)
                .as("Validate that found the same character persisted")
                .withFailMessage("Character not found")
                .hasFieldOrPropertyWithValue("name", "ivan")
                .hasFieldOrPropertyWithValue("uuid", persisted.getUuid());
    }

    @Test
    void givenNameOfCharacter_whenSearchInDbAndNotFoundAny_thenThrowNameNotFoundException() {
        //* Act & Assert
        assertThatThrownBy(() -> repository.findByName("ivan"))
                .as("Throw NameNotFoundException when not found a character")
                .isInstanceOf(NameNotFoundException.class);
    }

    @Test
    void findAllCharacters_whenDbIsNotEmpty_thenReturnPageWithAllCharacter() {
        //* Arrange
        Pageable pageable = PageRequest.of(0, 50);
        CharacterEntity character1 = new CharacterEntity();
        CharacterEntity character2 = new CharacterEntity();
        character1.setName("testName1");
        character2.setName("testName2");
        repository.saveAll(List.of(character1, character2));
        //* Act
        Page<CharacterEntity> found = repository.findAll(pageable);
        //* Assert
        assertThat(found)
                .as("Find Characters of Db")
                .withFailMessage("Didn't find characters in the db")
                .isNotEmpty();
    }

    @Test
    void findAllCharacters_whenDbIEmpty_thenReturnPageEmpty() {
        //* Arrange
        Pageable pageable = PageRequest.of(0, 50);
        //* Act
        Page<CharacterEntity> found = repository.findAll(pageable);
        //* Assert
        assertThat(found)
                .as("Find Characters in Db when Db is empty")
                .isEmpty();
    }
}