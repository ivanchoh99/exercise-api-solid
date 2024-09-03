package org.examplesolid.application.model.api;

import java.util.List;

public record ApiResponse(Object info, List<CharacterApi> results) {
}
