package org.examplesolid.domain.model.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Episode {
    private UUID uuid;
    private String url;
}
