package com.example.testing.util;

import com.example.testing.model.Pokemon;

import java.util.List;

public abstract class LoadData {

    public static Pokemon getPokemon() {
        return Pokemon.builder()
                .id(1L)
                .level(10)
                .name("Brei")
                .build();
    }

    public static List<Pokemon> getPokemons() {
        return List.of(
                Pokemon.builder().id(1L).name("Brei1").level(5).build(),
                Pokemon.builder().id(2L).name("Brei2").level(10).build()
        );
    }
}
