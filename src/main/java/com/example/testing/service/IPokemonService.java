package com.example.testing.service;

import com.example.testing.dto.PokemonResponseDto;
import com.example.testing.model.Pokemon;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPokemonService {

    Pokemon create(Pokemon pokemon);

    void deleteById(Long id);

    Pokemon update(Pokemon pokemon, Long id);

    List<Pokemon> findAll();

    PokemonResponseDto findAllWithPagination(int pageNumber, int pageSize);
}
