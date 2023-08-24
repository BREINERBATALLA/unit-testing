package com.example.testing.dto;

import com.example.testing.model.Pokemon;
import lombok.Builder;

import java.util.List;

@Builder
public record PokemonResponseDto(
        List<Pokemon> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isLast
) {
}
