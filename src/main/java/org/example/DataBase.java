package org.example;

import org.example.Series;
import org.example.AdultSeries;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    public static Connection connection;
    public static Statement statement;

    public DataBase() {
    }

    public static void connectDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        statement = connection.createStatement();
    }

    public static void createTableSeries() throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS series (" +
                "title TEXT, " +
                "director TEXT, " +
                "episodes INTEGER, " +
                "genre TEXT, " +
                "minAge INTEGER)");
    }

    public static void addSeries(Series series) throws SQLException {
        String query = String.format("INSERT INTO series (title, director, episodes, genre, minAge) " +
                        "VALUES ('%s', '%s', %d, '%s', %d)",
                series.getTitle(), series.getDirector(), series.getEpisodes(), series.getGenre(), series.getMinAge());
        statement.execute(query);
    }

    public static ArrayList<Series> getAllSeries() throws SQLException {
        ArrayList<Series> seriesList = new ArrayList<>();
        String query = "SELECT title, director, episodes, genre, minAge FROM series";
        ResultSet resultSet = statement.executeQuery(query);

        try {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String director = resultSet.getString("director");
                int episodes = resultSet.getInt("episodes");
                String genre = resultSet.getString("genre");
                int minAge = resultSet.getInt("minAge");

                // Check if it's an AdultSeries or a regular Series based on the minAge
                if (minAge > 0) {
                    AdultSeries adultSeries = new AdultSeries(title, director, episodes, genre);
                    adultSeries.setMinAge(minAge);
                    seriesList.add(adultSeries);
                } else {
                    Series regularSeries = new Series(title, director, episodes, genre);
                    seriesList.add(regularSeries);
                }
            }
        } catch (Throwable e) {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Throwable suppressed) {
                    e.addSuppressed(suppressed);
                }
            }
            throw e;
        }

        if (resultSet != null) {
            resultSet.close();
        }

        return seriesList;
    }

    public static void closeDB() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}
