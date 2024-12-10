package org.example;

import lombok.Getter;
import lombok.Setter;

public class Series {
    @Setter
    @Getter
    private String title;
    @Getter
    private String director;
    @Getter
    private int episodes;
    @Getter
    private String genre;

    public Series(String title, String director, int episodes, String genre) {
        this.title = title;
        this.director = director;
        this.episodes = episodes;
        this.genre = genre;
    }


    public void setEpisodes(int episodes) {
        if (episodes > 0) {
            this.episodes = episodes;
        } else {
            System.out.println("Количество эпизодов должно быть положительным.");
        }
    }

    public void printInfo() {
        System.out.println("Название: " + this.title);
        System.out.println("Режиссер: " + this.director);
        System.out.println("Эпизоды: " + this.episodes);
        System.out.println("Жанр: " + this.genre);
    }

    public int getMinAge() {
        return 16;
    }

    @Override
    public String toString() {
        return "Название: " + this.title +
                "/ Режиссер: " + this.director +
                "/ Эпизоды: " + this.episodes +
                "/ Жанр: " + this.genre;
    }
}
