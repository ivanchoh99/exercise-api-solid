package org.examplesolid.infrastructure.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewFunFactRequest(
        @NotNull @NotBlank
        String funFact,
        @NotNull @NotBlank
        String nameCharacter) {
}
