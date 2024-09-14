package org.examplesolid.infrastructure.api.model;

import java.util.List;

public record ApiResponse(Object info, List<CharacterApi> results) {
}
