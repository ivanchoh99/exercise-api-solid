package org.examplesolid.infrastructure.endpoints.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FactRequest(
        @NotNull @NotBlank
        String funFact,
        @NotNull @NotBlank
        String nameCharacter) {
}
