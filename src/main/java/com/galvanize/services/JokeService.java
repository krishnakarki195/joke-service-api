package com.galvanize.services;

import com.galvanize.entities.Joke;
import com.galvanize.entities.JokeCategory;
import com.galvanize.repositories.JokeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JokeService {

    JokeRepository jokeRepository;

    public JokeService(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public List<Joke> getAll() {
        return jokeRepository.findAll();
    }


    public Joke save(Joke joke) {
        if(joke != null){
            return jokeRepository.save(joke);
        }else{
            throw new RuntimeException("Joke shouldn't be null");
        }
    }

    public Joke findById(Long id) {
        if(jokeRepository.findById(id).isPresent()){
            return jokeRepository.findById(id).get();
        }else{
            throw new RuntimeException("Joke not found on this id:" + id);
        }
    }

    public Joke updateByJoke(Long id, String updatedJoke) {
        if(updatedJoke.equals("")){
            throw new RuntimeException("You can not updated joke as empty");
        }else{
            if(jokeRepository.findById(id).isPresent()){
                Joke joke = jokeRepository.findById(id).get();
                joke.setJoke(updatedJoke);
                return joke;
            }else{
                throw new RuntimeException("Joke not found on this id:" + id);
            }
        }
    }

    public boolean deleteById(Long id) {
        if(jokeRepository.findById(id).isPresent()){
            jokeRepository.deleteById(id);
            return !jokeRepository.existsById(id);
        }else{
            throw new RuntimeException("Joke not found on this id:" + id);
        }
    }

    public List<Joke> getAllByCategory(JokeCategory jokeCategory){
        return jokeRepository.getAllByJokeCategory(jokeCategory);
    }

}
