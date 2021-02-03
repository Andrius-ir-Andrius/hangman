package lt.andriaus.hangman.kata;

public class Arge {
    public static int nbYear(int p0, double percent, int aug, int p) {
        int counter = 0;
        int population = p0;
        while (p > population) {
            population *= (1 + percent / 100);
            population += aug;
            counter++;
        }
        return counter;
    }
}
