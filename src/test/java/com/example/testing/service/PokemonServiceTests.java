package com.example.testing.service;


import com.example.testing.model.Pokemon;
import com.example.testing.repository.PokemonRepository;
import com.example.testing.service.impl.PokemonServiceImpl;
import com.example.testing.util.LoadData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) // inicia y termina los mocks antes y despues de cada ejecución de un método QTest.
public class PokemonServiceTests {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;
  /*
    @BeforeEach
    void setUp () {
        //sin @Mock ni @InjectMocks
        pokemonRepository = Mockito.mock(PokemonRepository.class);
        pokemonService = new PokemonServiceImpl(pokemonRepository);
    }
   */

    @Test
    //@Disabled
    void getAllPokemons () {
        //Given
        List<Pokemon> pokemonList = LoadData.createSampleListPokemons();

        //When
        Mockito.when(pokemonRepository.findAll()).thenReturn(pokemonList);
        List<Pokemon> result = pokemonService.findAll();

        //Then
        verify(pokemonRepository).findAll();
        Assertions.assertEquals(pokemonList, result);
    }

    @Test
    void addPokemon() {
        Pokemon pokemon = LoadData.createSamplePokemon();

        Mockito.when(pokemonRepository.save(pokemon)).thenReturn(pokemon);

        Pokemon result = pokemonService.create(pokemon);

        verify(pokemonRepository).save(pokemon);
        Assertions.assertEquals(pokemon, result);

    }

    @Test
    void deleteById() {
        Pokemon pokemon = LoadData.createSamplePokemon();
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));

        pokemonService.deleteById(pokemon.getId());

        verify(pokemonRepository).deleteById(anyLong()); //Verify if mockito method was called with anyLong parameter.
    }

    @Test
    void deleteByIdPokemonNotFound() {
        Pokemon pokemon = LoadData.createSamplePokemon();
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.deleteById(pokemon.getId());
        });

        verify(pokemonRepository, never()).deleteById(anyLong()); // verify if mockito repository never execute deleteByIdMethod with anuLong parameter.
    }
}
