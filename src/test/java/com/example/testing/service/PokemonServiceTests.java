package com.example.testing.service;


import com.example.testing.dto.PokemonResponseDto;
import com.example.testing.model.Pokemon;
import com.example.testing.repository.PokemonRepository;
import com.example.testing.service.impl.PokemonServiceImpl;
import com.example.testing.util.LoadData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testFindAllPokemons () {
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
    void testSavePokemon() {
        Pokemon pokemon = LoadData.createSamplePokemon();
        //if we use dto, at here, I need to pass object dto with the pokemon's value.
        Mockito.when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        //when repo.save(pokemon).thenReturn(pokemon)
        //PokemonDto result = pokemonService.create(pokemonDto)
        Pokemon result = pokemonService.create(pokemon);

        verify(pokemonRepository).save(pokemon);
        Assertions.assertEquals(pokemon, result);
    }

    @Test
    void testDeleteByIdPokemonFound() {
        Long idPokemon = 2L;
        Pokemon pokemon = LoadData.createSamplePokemon();

        when(pokemonRepository.findById(idPokemon)).thenReturn(Optional.of(pokemon));

        pokemonService.deleteById(idPokemon);

        verify(pokemonRepository).deleteById(anyLong()); //Verify if mockito method was called with anyLong parameter.
    }

    @Test
    void  testDeleteByIdNotExistingPokemon() {
        Long idPokemon = 2L;

        when(pokemonRepository.findById(idPokemon)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.deleteById(idPokemon);
        });

        verify(pokemonRepository, never()).deleteById(anyLong()); // verify if mockito repository never execute deleteByIdMethod with anuLong parameter.
    }

    @Test
    void testUpdatePokemon() {
        // Arrange
        Long pokemonId = 1L;
        Pokemon originalPokemon = LoadData.createSamplePokemon();

        Pokemon updatedPokemon = LoadData.createSamplePokemon();
        updatedPokemon.setName("Updated Name");


        when(pokemonRepository.save(updatedPokemon)).thenReturn(updatedPokemon);
        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(originalPokemon));

        // Act
        Pokemon result = pokemonService.update(updatedPokemon, 1L);

        // Assert
        Assertions.assertEquals("Updated Name", result.getName());
        verify(pokemonRepository).findById(1L);
        verify(pokemonRepository).save(updatedPokemon);
    }

    @Test
    public void PokemonService_findAllWithPagination_ReturnPokemonResponseDto(){
        Page<Pokemon> pokemons = Mockito.mock(Page.class);
    //podria no mockearlo--> Page<Pokemon>

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponseDto responseDto = pokemonService.findAllWithPagination(2, 2);

        verify(pokemonRepository).findAll(Mockito.any(Pageable.class));
        Assertions.assertNotNull(responseDto);

    }

}
