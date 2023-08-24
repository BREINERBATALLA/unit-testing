package com.example.testing.service.impl;

import com.example.testing.dto.PokemonResponseDto;
import com.example.testing.model.Pokemon;
import com.example.testing.repository.PokemonRepository;
import com.example.testing.service.IPokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements IPokemonService {

    final PokemonRepository pokemonRepository;

    public Pokemon create(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    public void deleteById(Long id) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(id);

        if(! pokemon.isPresent() ) {
            throw new IllegalArgumentException("Don't exits");
        }

        pokemonRepository.deleteById(id);
    }

    public Pokemon update(Pokemon pokemon, Long id) {
        return pokemonRepository.findById(id)
                .map(pokemonUpdated -> {
                    pokemonUpdated.setName(pokemon.getName());
                    pokemonUpdated.setType(pokemon.getType());
                    return pokemonRepository.save(pokemonUpdated);
                }).orElse(null);
    }

    public List<Pokemon> findAll() {
        return pokemonRepository.findAll();
    }

    public PokemonResponseDto findAllWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);

        PokemonResponseDto pokemonResponseDto = PokemonResponseDto.builder()
                .content(pokemons.getContent())
                .pageNumber(pokemons.getNumber())
                .pageSize(pokemons.getSize())
                .totalElements(pokemons.getTotalElements())
                .totalPages(pokemons.getTotalPages())
                .isLast(pokemons.isLast())
                .build();

        return pokemonResponseDto;
    }

}
