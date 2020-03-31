package com.galvanize.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entities.Joke;
import com.galvanize.entities.JokeCategory;
import com.galvanize.services.JokeService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class JokeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    JokeService jokeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveJokeApiTest() throws Exception {
        String url = "/api/jokes";
        Joke joke = new Joke("Haha !", JokeCategory.MOMJOKES);
        joke.setId(1L);

        when(jokeService.save(ArgumentMatchers.any(Joke.class))).thenReturn(joke);

        mvc.perform(post(url).content(objectMapper.writeValueAsString(joke))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(joke.getId()))
                .andExpect((jsonPath("$.joke").value(joke.getJoke())))
                .andDo(print());
    }

    @Test
    public void findJokeByIdApiTest() throws Exception {
        String url = "/api/jokes/1";
        Joke joke = new Joke("Haha !", JokeCategory.MOMJOKES);
        joke.setId(1L);

        when(jokeService.findById(ArgumentMatchers.any(Long.class))).thenReturn(joke);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(joke.getId()))
                .andExpect((jsonPath("$.joke").value(joke.getJoke())))
                .andDo(print());
    }

    @Test
    public void findAllJokeApiTest() throws Exception {
        String url = "/api/jokes";
        Joke joke1 = new Joke("Haha !", JokeCategory.MOMJOKES);
        joke1.setId(1L);
        Joke joke2 = new Joke("Huhu !", JokeCategory.KIDJOKES);
        joke2.setId(2L);

        when(jokeService.getAll()).thenReturn(Arrays.asList(joke1,joke2));

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andDo(print());
    }

    @Test
    public void deleteJokeByIdApiTest() throws Exception {
        String url = "/api/jokes/1";

        when(jokeService.deleteById(ArgumentMatchers.any(Long.class))).thenReturn(Boolean.valueOf("true"));

        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateJokeApiTest() throws Exception {
        String url = "/api/jokes/1";
        Joke joke = new Joke("Haha !", JokeCategory.MOMJOKES);
        joke.setId(1L);
        joke.setJoke("Hehe !");

        String new_joke = "{\"joke\":\"Hehe !\"}";

        when(jokeService.updateByJoke(ArgumentMatchers.any(Long.class),ArgumentMatchers.any(String.class))).thenReturn(joke);

        mvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("joke",new_joke))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(joke.getId()))
                .andExpect(jsonPath("$.joke").value(joke.getJoke()))
                .andDo(print());
    }

    @Test
    public void getAllByJokeCategoryApiTest() throws Exception {
        String url = "/api/jokes/category/"+JokeCategory.DADJOKES;

        Joke joke1 = new Joke("Dad joke1 !", JokeCategory.DADJOKES);
        Joke joke2 = new Joke("Dad joke2 !", JokeCategory.DADJOKES);
        Joke joke3 = new Joke("Mom joke1 !", JokeCategory.MOMJOKES);
        Joke joke4 = new Joke("Mom joke2 !", JokeCategory.MOMJOKES);

        List<Joke> dadJokes = Arrays.asList(joke1,joke2);
        List<Joke> momJokes = Arrays.asList(joke3,joke4);

        when(jokeService.getAllByCategory(ArgumentMatchers.any(JokeCategory.class))).thenReturn(dadJokes);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }


}