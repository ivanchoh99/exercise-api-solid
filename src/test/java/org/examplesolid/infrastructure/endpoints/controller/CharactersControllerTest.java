package org.examplesolid.infrastructure.endpoints.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.examplesolid.application.service.CharacterService;
import org.examplesolid.infrastructure.endpoints.dto.request.FactRequest;
import org.examplesolid.infrastructure.endpoints.dto.response.CharacterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.naming.NameNotFoundException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharactersController.class)
class CharactersControllerTest {

    private static final String BASE_URL = "/api/characters";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CharacterService service;

    @Test
    void getCharacters() throws Exception {
        //* Arrange
        CharacterResponse character1 = new CharacterResponse();
        CharacterResponse character2 = new CharacterResponse();
        character1.setName("Rick Sanchez");
        character2.setName("Morty Smith");
        List<CharacterResponse> characters = List.of(character1, character2);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "100");
        when(service.getSortedCharacters(any(Pageable.class))).thenReturn(characters);
        //*Act & Assert
        mockMvc.perform(get(BASE_URL)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rick Sanchez"))  // Verifica el nombre del primer personaje
                .andExpect(jsonPath("$[1].name").value("Morty Smith"));
    }

    @Test
    void postAddFact() throws Exception {
        //* Arrange
        String nameCharacter = "Rick";
        String newFact = "newFact";
        FactRequest factRequest = new FactRequest(newFact, nameCharacter);
        CharacterResponse characterResponse = new CharacterResponse();
        characterResponse.setName(nameCharacter);
        characterResponse.setFacts(List.of("firstFact", factRequest.funFact()));
        String factRequestJson = objectMapper.writeValueAsString(factRequest);
        when(service.addFact(nameCharacter, newFact)).thenReturn(characterResponse);
        //* Act & Assert
        mockMvc.perform(post(BASE_URL.concat("/fact"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nameCharacter))
                .andExpect(jsonPath("$.facts[0]").value("firstFact"))
                .andExpect(jsonPath("$.facts[1]").value(newFact));
    }

    @Test
    void postAddFact_AndNotFoundCharacter() throws Exception {
        //* Arrange
        String nameCharacter = "Rick";
        String newFact = "newFact";
        FactRequest factRequest = new FactRequest(newFact, nameCharacter);
        String factRequestJson = objectMapper.writeValueAsString(factRequest);
        when(service.addFact(nameCharacter, newFact)).thenThrow(new NameNotFoundException("character with name " + nameCharacter + " not found"));
        //* Act & Assert
        mockMvc.perform(post(BASE_URL.concat("/fact"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("character with name " + nameCharacter + " not found"));
    }
}