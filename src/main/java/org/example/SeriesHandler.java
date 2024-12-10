package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeriesHandler {

    public SeriesHandler() {
    }

    public static void writeFile(List<Series> seriesList, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Series series : seriesList) {
                writer.write(series.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public static List<Series> readFile(String path) {
        List<Series> seriesList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                seriesList.add(parseSeries(line));
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return seriesList;
    }

    private static Series parseSeries(String line) {
        if (line.startsWith("(AdultSeries)")) {
            return parseAdultSeries(line);
        } else {
            return parseRegularSeries(line);
        }
    }

    private static AdultSeries parseAdultSeries(String line) {
        String[] parts = line.split("/ ");
        String title = parts[1].split(": ")[1];
        String director = parts[2].split(": ")[1];
        int episodes = Integer.parseInt(parts[3].split(": ")[1]);
        String genre = parts[4].split(": ")[1];
        int minAge = Integer.parseInt(parts[5].split(": ")[1].split(" ")[0]);

        AdultSeries series = new AdultSeries(title, director, episodes, genre);
        series.setMinAge(minAge);
        return series;
    }

    private static Series parseRegularSeries(String line) {
        String[] parts = line.split("/ ");
        String title = parts[0].split(": ")[1];
        String director = parts[1].split(": ")[1];
        int episodes = Integer.parseInt(parts[2].split(": ")[1]);
        String genre = parts[3].split(": ")[1];

        return new Series(title, director, episodes, genre);
    }

    public static List<Series> generateSeries(int count) {
        List<Series> seriesList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            if (random.nextBoolean()) {
                seriesList.add(generateRandomSeries());
            } else {
                seriesList.add(generateRandomAdultSeries());
            }
        }
        return seriesList;
    }

    private static Series generateRandomSeries() {
        String[] titles = {"Breaking Bad", "Game of Thrones", "Stranger Things", "The Office", "Friends"};
        String[] directors = {"Vince Gilligan", "David Benioff", "The Duffer Brothers", "Greg Daniels", "Marta Kauffman"};
        String[] genres = {"Drama", "Fantasy", "Science Fiction", "Comedy", "Slice of Life"};

        String title = randomFromArray(titles);
        String director = randomFromArray(directors);
        String genre = randomFromArray(genres);
        int episodes = 10 + new Random().nextInt(11);
        return new Series(title, director, episodes, genre);
    }

    private static AdultSeries generateRandomAdultSeries() {
        String[] titles = {"Adult Series 1", "Adult Series 2", "Adult Series 3"};
        String[] directors = {"Director A", "Director B", "Director C"};
        String[] genres = {"Drama", "Romance"};

        String title = randomFromArray(titles);
        String director = randomFromArray(directors);
        String genre = randomFromArray(genres);
        int episodes = 8 + new Random().nextInt(6);
        int minAge = 18 + new Random().nextInt(3);
        AdultSeries series = new AdultSeries(title, director, episodes, genre);
        series.setMinAge(minAge);
        return series;
    }

    private static String randomFromArray(String[] array) {
        return array[new Random().nextInt(array.length)];
    }
}
