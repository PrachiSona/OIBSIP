import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Random random = new Random();
        int number = random.nextInt(100) + 1;

        Scanner scanner = new Scanner(System.in);

        int score = 0;
        int attempts = 0;

        System.out.println("Guess the number between 1 and 100:");
        int guess;
        do {
            guess = scanner.nextInt();
            attempts++;

            if (guess < number) {
                System.out.println("Too low! Try again!");
            } else if (guess > number) {
                System.out.println("Too high! Try again!");
            }

            if (guess == number) {
                score = 10 * (11 - attempts);
                System.out.println("Congratulations! You guessed the number correctly in " + attempts + " attempts. Your score is: " + score);
            }

        } while (guess != number && attempts < 10);

        if (guess != number) {
            System.out.println("Sorry, you did not guess the number correctly. The correct number was: " + number + ". Your score is: " + score);
        }
    }
}