package com.galvanize.controllers;

import com.galvanize.entities.Joke;
import com.galvanize.entities.JokeCategory;
import com.galvanize.services.JokeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/jokes")
public class JokeController {

    JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @PostMapping
    public Joke save(@RequestBody Joke joke){
        return jokeService.save(joke);
    }

    @GetMapping("/{id}")
    public Joke findById(@PathVariable("id") Long id){
       return jokeService.findById(id);
    }

    @GetMapping()
    public ResponseEntity<List<Joke>> findAll(){
        List<Joke> jokes = jokeService.getAll();
        if(jokes.isEmpty()){
            return ResponseEntity.noContent().header("errorMsg", "No jokes found").build();
        }
        return ResponseEntity.ok(jokeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        try{
            Joke joke = jokeService.findById(id);
            return ResponseEntity.ok(jokeService.deleteById(id));
        }catch (Exception e){
            return ResponseEntity.noContent().header("errorMsg", "No joke found").build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Joke> updateJokeById(@PathVariable("id") Long id, @RequestParam String joke){
        try{
            return ResponseEntity.ok(jokeService.updateByJoke(id,joke));
        }catch (Exception e){
            return ResponseEntity.noContent().header("errorMsg",
                    "Joke not found or new joke shouldn't be empty").build();
        }
    }

    @GetMapping("/category/{jokeCategory}")
    public ResponseEntity<List<Joke>> getAllByJokeCategory(@PathVariable("jokeCategory") JokeCategory jokeCategory){
        List<Joke> jokes = jokeService.getAllByCategory(jokeCategory);
        if(jokes.isEmpty()){
            return ResponseEntity.noContent().header("errorMsg", "No jokes found").build();
        }
        return ResponseEntity.ok(jokeService.getAllByCategory(jokeCategory));
    }

    @GetMapping("/{jokeCategory}/random")
    public ResponseEntity<Joke> getRandomByJokeCategory(@PathVariable("jokeCategory") JokeCategory jokeCategory){
        try{
            Joke joke = jokeService.getRandomJoke(jokeCategory);
            return ResponseEntity.ok(joke);
        }catch(Exception ex){
            return ResponseEntity.noContent().header("errorMsg", "No joke found").build();
        }
    }

}
