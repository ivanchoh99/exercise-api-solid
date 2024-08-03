package org.examplesolid.domain.model.api;

import java.util.List;

public record CharacterResponse(Info info, List<CharacterAPI> results) {
}
