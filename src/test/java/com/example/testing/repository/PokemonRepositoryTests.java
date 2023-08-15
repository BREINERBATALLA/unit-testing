package com.example.testing.repository;

import com.example.testing.model.Pokemon;
import com.example.testing.util.LoadData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase( connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTests {

    @Autowired
    PokemonRepository pokemonRepository;

    @Test
    @DisplayName("Test save method")
    public void savePokemonTest() {
        //Arrange
        Pokemon pokemon = LoadData.getPokemon();

        //Act
        Pokemon result = pokemonRepository.save(pokemon);

        //Assert
        Assertions.assertNotNull(result);
    }

}
