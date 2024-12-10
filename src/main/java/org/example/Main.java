package org.example;

import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // Подключение к базе данных и создание таблицы сериалов
            DataBase.connectDB();
            DataBase.createTableSeries();

            // Генерация 30 случайных сериалов
            var genSeries = SeriesHandler.generateSeries(30);
            for (Series series : genSeries) {
                DataBase.addSeries(series);
            }

            // Получение всех сериалов из базы данных
            List<Series> seriesList = DataBase.getAllSeries();

            // Подсчет количества сериалов по жанрам
            Map<String, Integer> genreCounts = new HashMap<>();
            for (Series series : seriesList) {
                genreCounts.put(series.getGenre(), genreCounts.getOrDefault(series.getGenre(), 0) + 1);
            }

            // Отображение графика
            SwingUtilities.invokeLater(() -> {
                Graph graph = new Graph(genreCounts); // Использование Graph для отображения данных
                graph.setSize(800, 600);
                graph.setLocationRelativeTo(null);
                graph.setVisible(true);
            });

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            // Закрытие соединения с базой данных
            DataBase.closeDB();
        }
    }
}
