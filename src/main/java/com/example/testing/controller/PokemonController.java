package com.example.testing.controller;

import com.example.testing.model.Pokemon;
import com.example.testing.service.IPokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class PokemonController {

    final private IPokemonService pokemonService;

    @GetMapping()
    public ResponseEntity<List<Pokemon>>  findAll() {
        return new ResponseEntity<>( pokemonService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Pokemon> create(@RequestBody Pokemon pokemon) {
        return new ResponseEntity<>(pokemonService.create(pokemon), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete( @PathVariable(name = "id") Long idPokemon ) {
        pokemonService.deleteById(idPokemon);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
