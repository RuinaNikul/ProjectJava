package org.example;

public class AdultSeries extends Series {
    private int minAge;

    public AdultSeries(String title, String director, int episodes, String genre) {
        super(title, director, episodes, genre);
        this.minAge = 18; // Устанавливаем значение по умолчанию
    }

    public int getMinAge() {
        return this.minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Минимальный возраст: " + this.minAge + " лет");
    }

    @Override
    public String toString() {
        String baseInfo = super.toString();
        return "(AdultSeries)/ " + baseInfo + "/ Минимальный возраст: " + this.minAge + " лет";
    }
}
