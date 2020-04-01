package com.galvanize.entities;

import javax.persistence.*;

@Entity
@Table(name = "jokes")
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="joke")
    private String joke;

    @Column(name = "joke_category")
    @Enumerated(EnumType.STRING)
    private JokeCategory jokeCategory;

    public Joke(){}

    public Joke(String joke, JokeCategory jokeCategory) {
        this.joke = joke;
        this.jokeCategory = jokeCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public JokeCategory getJokeCategory() {
        return jokeCategory;
    }

    public void setJokeCategory(JokeCategory jokeCategory) {
        this.jokeCategory = jokeCategory;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", joke='" + joke + '\'' +
                ", jokeCategory=" + jokeCategory +
                '}';
    }

}
