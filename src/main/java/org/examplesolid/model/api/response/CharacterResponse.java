package org.examplesolid.model.api.response;

import java.util.List;

public record CharacterResponse(Info info, List<CharacterAPI> results) {
}
