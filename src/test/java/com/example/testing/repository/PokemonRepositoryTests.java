package com.example.testing.repository;

import com.example.testing.model.Pokemon;
import com.example.testing.util.LoadData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
//@ActiveProfiles()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureTestDatabase( connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {

    @Autowired
    PokemonRepository pokemonRepository;

    @Test
    @DisplayName("Test save method")
    public void savePokemonTest() {
        //Arrange
        Pokemon pokemon = LoadData.createSamplePokemon();

        //Act
        Pokemon result = pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertNotNull(result.getId());
    }

    @Test
    public void itShouldCheckIfPokemonDoesNotExists() {
        Long id = 2L;

        Pokemon result = pokemonRepository.findById(id).orElse(null);

        Assertions.assertNull(result);
    }

    @Test
    public void itShouldCheckIfPokemonExists() {
        Pokemon pokemon = LoadData.createSamplePokemon();

        Pokemon pokemonSaved = pokemonRepository.save(pokemon);
        Pokemon result = pokemonRepository.findById(pokemonSaved.getId()).orElse(null);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updatePokemonTest() {
        Pokemon pokemon = LoadData.createSamplePokemon();
        pokemonRepository.save(pokemon);

        Pokemon pokemonSaved =  pokemonRepository.findById(pokemon.getId()).orElse(null);
        pokemonSaved.setType("Prueba");
        pokemonSaved.setLevel(10);

        Pokemon updatePokemon = pokemonRepository.save(pokemonSaved);

        Assertions.assertNotNull(updatePokemon.getType());
        Assertions.assertEquals(updatePokemon.getId(), 1);
        Assertions.assertEquals(updatePokemon.getType(), "Prueba");
        Assertions.assertEquals(updatePokemon.getLevel(), 10);
    }

    @Test
    public void deletePokemonById() {
        Pokemon pokemon = LoadData.createSamplePokemon();
        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());

        Optional<Pokemon> pokemonResult = pokemonRepository.findById(pokemon.getId());

        Assertions.assertNull(pokemonResult);
    }

    @Test
    public void findByTypePokemonSuccess() {
        Pokemon pokemon = LoadData.createSamplePokemon();
        pokemonRepository.save(pokemon);

        Pokemon result = pokemonRepository.findByType(pokemon.getType()).orElse(null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(pokemon, result);
    }

    @Test
    public void findByTypePokemonFailure() {
        Pokemon pokemon = LoadData.createSamplePokemon();

        Pokemon result = pokemonRepository.findByType(pokemon.getType()).orElse(null);

        Assertions.assertNull(result);
        Assertions.assertNotEquals(pokemon, result);
    }

}
