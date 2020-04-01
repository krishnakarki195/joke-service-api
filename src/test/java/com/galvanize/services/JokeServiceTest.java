package com.galvanize.services;

import com.galvanize.entities.Joke;
import com.galvanize.entities.JokeCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class JokeServiceTest {

    @Autowired
    JokeService jokeService;

    @Test
    public void findAllTest(){
        assertEquals(new ArrayList<Joke>(), jokeService.getAll());
    }

    @Test
    public void saveTest(){
        Joke expected = new Joke("Haha !", JokeCategory.KIDJOKES);
        Joke actual = jokeService.save(expected);
        assertEquals(expected.getJoke(), actual.getJoke());
        assertThrows(RuntimeException.class, ()-> jokeService.save(null));
    }

    @Test
    public void findByIdTest(){
        Joke joke = new Joke("Haha !", JokeCategory.KIDJOKES);
        Joke expected = jokeService.save(joke);
        Joke actual = jokeService.findById(expected.getId());
        assertEquals(expected.getId(),actual.getId());
        assertThrows(RuntimeException.class, ()-> jokeService.findById(100L));
    }

    @Test
    public void updateTest(){
        Joke joke = new Joke("Haha !", JokeCategory.KIDJOKES);
        Joke savedJoke = jokeService.save(joke);
        String updatedJoke = "Huhu !";
        Joke expected = jokeService.updateByJoke(savedJoke.getId(), updatedJoke);
        assertEquals(expected.getJoke(), updatedJoke);
        assertThrows(RuntimeException.class, ()-> jokeService.updateByJoke(savedJoke.getId(),""));
        assertThrows(RuntimeException.class, ()-> jokeService.updateByJoke(100L,"Huhu !"));

    }

    @Test
    public void deleteTest(){
        Joke joke = new Joke("Haha !", JokeCategory.KIDJOKES);
        Joke savedJoke = jokeService.save(joke);
        assertEquals(true,  jokeService.deleteById(savedJoke.getId()));
        assertThrows(RuntimeException.class, ()-> jokeService.findById(savedJoke.getId()));
    }

    @Test
    public void getAllByJokeCategoryTest(){
        Joke joke1 = new Joke("Kid joke1 !", JokeCategory.KIDJOKES);
        Joke joke2 = new Joke("Kid joke2 !", JokeCategory.KIDJOKES);
        Joke joke3 = new Joke("Mom joke1 !", JokeCategory.MOMJOKES);
        Joke joke4 = new Joke("Mon joke2 !", JokeCategory.MOMJOKES);
        List<Joke> jokes = Arrays.asList(joke1,joke2,joke3,joke4);

        jokes.forEach((joke)-> jokeService.save(joke) );

        assertEquals(2,  jokeService.getAllByCategory(JokeCategory.MOMJOKES).size());
        assertEquals(2,  jokeService.getAllByCategory(JokeCategory.KIDJOKES).size());

    }

    @Test
    public void getRandomJokeTest(){
        Joke joke1 = new Joke("Kid joke1 !", JokeCategory.KIDJOKES);
        Joke joke2 = new Joke("Kid joke2 !", JokeCategory.KIDJOKES);
        Joke joke3 = new Joke("Mom joke1 !", JokeCategory.MOMJOKES);
        Joke joke4 = new Joke("Mon joke2 !", JokeCategory.MOMJOKES);
        List<Joke> jokes = Arrays.asList(joke1,joke2,joke3,joke4);

        jokes.forEach((joke)-> jokeService.save(joke) );

        assertEquals(4, jokeService.getAll().size());

        assertEquals(JokeCategory.KIDJOKES, jokeService.getRandomJoke(JokeCategory.KIDJOKES).getJokeCategory());
        assertEquals(JokeCategory.MOMJOKES, jokeService.getRandomJoke(JokeCategory.MOMJOKES).getJokeCategory());

    }



}