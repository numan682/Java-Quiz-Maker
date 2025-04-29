import java.util.ArrayList;
import java.util.Scanner;

public class QuizMaker {

    private static ArrayList<Question> quiz = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- Quiz Maker ---");
            System.out.println("1. Create a new question");
            System.out.println("2. Take the quiz");
            System.out.println("3. View all questions");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    createQuestion();
                    break;
                case 2:
                    takeQuiz();
                    break;
                case 3:
                    viewAllQuestions();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void createQuestion() {
        System.out.println("\n--- Create New Question ---");
        
        System.out.print("Enter the question: ");
        String questionText = readLine();
        
        System.out.print("How many answer choices? (2-5): ");
        int numChoices = getIntInput(2, 5);
        
        ArrayList<String> choices = new ArrayList<>();
        for (int i = 0; i < numChoices; i++) {
            System.out.print("Enter choice " + (i + 1) + ": ");
            choices.add(readLine());
        }
        
        System.out.print("Which choice is correct? (1-" + numChoices + "): ");
        int correctAnswer = getIntInput(1, numChoices);
        
        Question question = new Question(questionText, choices, correctAnswer - 1);
        quiz.add(question);
        
        System.out.println("Question added successfully!");
    }

    private static void takeQuiz() {
        if (quiz.isEmpty()) {
            System.out.println("No questions available. Please create some questions first.");
            return;
        }
        
        System.out.println("\n--- Take the Quiz ---");
        System.out.println("There are " + quiz.size() + " questions. Good luck!\n");
        
        int score = 0;
        
        for (int i = 0; i < quiz.size(); i++) {
            Question question = quiz.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            
            ArrayList<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ". " + choices.get(j));
            }
            
            System.out.print("Your answer (1-" + choices.size() + "): ");
            int userAnswer = getIntInput(1, choices.size()) - 1;
            
            if (userAnswer == question.getCorrectAnswerIndex()) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer was: " + 
                    (question.getCorrectAnswerIndex() + 1) + "\n");
            }
        }
        
        System.out.println("Quiz complete! Your score: " + score + "/" + quiz.size());
        System.out.printf("Percentage: %.1f%%\n", (score * 100.0 / quiz.size()));
    }

    private static void viewAllQuestions() {
        if (quiz.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }
        
        System.out.println("\n--- All Questions ---");
        for (int i = 0; i < quiz.size(); i++) {
            Question question = quiz.get(i);
            System.out.println((i + 1) + ". " + question.getQuestionText());
            
            ArrayList<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                String marker = (j == question.getCorrectAnswerIndex()) ? "✓" : " ";
                System.out.println("   [" + marker + "] " + (j + 1) + ". " + choices.get(j));
            }
            System.out.println();
        }
    }

    private static String readLine() {
        String line = scanner.nextLine();
        while (line.trim().isEmpty()) {
            line = scanner.nextLine();
        }
        return line;
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine();
        return num;
    }

    private static int getIntInput(int min, int max) {
        int input;
        do {
            input = getIntInput();
            if (input < min || input > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (input < min || input > max);
        return input;
    }

    static class Question {
        private String questionText;
        private ArrayList<String> choices;
        private int correctAnswerIndex;

        public Question(String questionText, ArrayList<String> choices, int correctAnswerIndex) {
            this.questionText = questionText;
            this.choices = choices;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getQuestionText() {
            return questionText;
        }

        public ArrayList<String> getChoices() {
            return choices;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }
}
