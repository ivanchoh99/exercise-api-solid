package org.examplesolid.application.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.examplesolid.application.util.Constants.SEPARATOR_CONCAT_FACT;

@ExtendWith(MockitoExtension.class)
class FactFactUtilityTest {
    @Test
    void whenGetAStringOfFunFacts_thenReturnAList() {
        //* Arrange
        String funFact1 = "funFact1";
        String funFact2 = "funFact2";
        String funFactsString = funFact1 + SEPARATOR_CONCAT_FACT + funFact2;
        //* Act
        List<String> funFactsList = StringUtility.stringToList(funFactsString);
        //* Assert
        assertThat(funFactsList)
                .as("Validate that list is correctly and complete")
                .withFailMessage("The list only have:  %s", funFactsList.toString())
                .contains(funFact1, funFact2)
                .as("Validate that list is not empty")
                .withFailMessage("The list is empty")
                .isNotEmpty();
    }

    @Test
    void givenNullString_whenGetAStringOfFunFacts_thenReturnEmptyList() {
        //* Arrange
        String funFactsString = null;
        //* Act
        List<String> funFactsList = StringUtility.stringToList(funFactsString);
        //* Assert
        assertThat(funFactsList)
                .as("Validate that list is empty")
                .withFailMessage("The list is not empty, have %d elements: %s",
                        funFactsList.size(), funFactsList.toString())
                .isEmpty();
    }

    @Test
    void givenBlankString_whenGetAStringOfFunFacts_thenReturnEmptyList() {
        //* Arrange
        String funFactsString = "     ";
        //* Act
        List<String> funFactsList = StringUtility.stringToList(funFactsString);
        //* Assert
        assertThat(funFactsList)
                .as("Validate that list is empty")
                .withFailMessage("The list is not empty, have %d elements: %s",
                        funFactsList.size(), funFactsList.toString())
                .isEmpty();
    }

    @Test
    void givenEmptyString_whenGetAStringOfFunFacts_thenReturnEmptyList() {
        //* Arrange
        String funFactsString = "";
        //* Act
        List<String> funFactsList = StringUtility.stringToList(funFactsString);
        //* Assert
        assertThat(funFactsList)
                .as("Validate that list is empty")
                .withFailMessage("The list is not empty, have %d elements: %s",
                        funFactsList.size(), funFactsList.toString())
                .isEmpty();
    }

    @Test
    void whenGetAListOfFunFacts_thenReturnAString() {
        //* Arrange
        String funFact1 = "funFact1";
        String funFact2 = "funFact2";
        List<String> funFactsList = List.of(funFact1, funFact2);
        String expectedFunFactsString = funFact1 + SEPARATOR_CONCAT_FACT + funFact2;
        //* Act
        String funFactsString = StringUtility.listToString(funFactsList);
        //* Assert
        assertThat(funFactsString)
                .as("validate the correctly formating of fun facts in the string")
                .withFailMessage("The string is not like expected: %s \n    Expected: %s ", funFactsString, expectedFunFactsString)
                .isEqualTo(expectedFunFactsString);
    }

    @Test
    void givenNullList_whenGetAListOfFunFacts_thenReturnBlankString() {
        //* Arrange
        List<String> funFactsList = null;
        //* Act
        String funFactsString = StringUtility.listToString(funFactsList);
        //* Assert
        assertThat(funFactsString)
                .as("Get a empty String")
                .withFailMessage("The string is not empty")
                .isEmpty();
    }

    @Test
    void givenEmptyList_whenGetAListOfFunFacts_thenReturnBlankString() {
        //* Arrange
        List<String> funFactsList = List.of();
        //* Act
        String funFactsString = StringUtility.listToString(funFactsList);
        //* Assert
        assertThat(funFactsString)
                .as("Get a empty String")
                .withFailMessage("The string is not empty")
                .isEmpty();
    }
}