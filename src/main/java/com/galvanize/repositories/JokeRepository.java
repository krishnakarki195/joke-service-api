package com.galvanize.repositories;

import com.galvanize.entities.Joke;
import com.galvanize.entities.JokeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {

    public List<Joke> getAllByJokeCategory(JokeCategory jokeCategory);

    @Query(value = "select * from jokes j where joke_category like ?1 order by RAND() limit 1;", nativeQuery = true)
    public Joke getRandom(String jokeCategory);

}
