import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FitnessPlanMain {
    public static void main(String[] args) {
        List<FitnessPlan> plans = new ArrayList<>();
        plans.add(new CardioPlan());
        plans.add(new StrengthPlan());
        plans.add(new FlexibilityPlan());
        plans.add(new HIITPlan());
        plans.add(new YogaPlan());

        Scanner scanner = new Scanner(System.in);

        // Get user fitness level using numeric input
        System.out.println("========= Fitness Plan Recommendation System =========");
        String userFitnessLevel = getUserFitnessLevel(scanner);

        // Ask for medical conditions with validation for "yes/no"
        String hasMedicalCondition = getYesOrNoInput(scanner, "Do you have any medical conditions? (yes/no)");

        String medicalCondition = "None";
        if (hasMedicalCondition.equals("yes")) {
            medicalCondition = getUserMedicalCondition(scanner);
        }

        // Display suggested fitness plans and their required weekly exercise time
        System.out.println("\n========= Suggested Fitness Plans =========");
        for (FitnessPlan plan : plans) {
            if (userFitnessLevel.equalsIgnoreCase(plan.getRequiredFitnessLevel())) {
                int additionalMinutes = plan.calculateAdditionalMinutes(userFitnessLevel);
                int totalWeeklyMinutes = plan.getMinimumDuration() * 5; // Assuming 5 days a week
                System.out.println("-------------------------------------------");
                System.out.println("Fitness Plan: " + plan.getPlanName());
                System.out.println("  - Minimum Duration: " + plan.getMinimumDuration() + " minutes/day");
                System.out.println("  - Additional Minutes (based on your fitness level): " + additionalMinutes + " minutes/day");
                System.out.println("  - Total Weekly Time: " + totalWeeklyMinutes + " minutes/week");

                // Additional notes based on medical condition
                if (!medicalCondition.equals("None")) {
                    displayMedicalAdvice(medicalCondition);
                }
            }
        }
        System.out.println("===========================================");

        scanner.close();
    }

    // Method to get user fitness level with input validation
    private static String getUserFitnessLevel(Scanner scanner) {
        String fitnessLevel = null;
        while (fitnessLevel == null) {
            try {
                System.out.println("Select your fitness level:");
                System.out.println("1 - Beginner");
                System.out.println("2 - Intermediate");
                System.out.println("3 - Advanced");
                System.out.println("======================================================");
                int fitnessLevelChoice = scanner.nextInt();
                switch (fitnessLevelChoice) {
                    case 1:
                        fitnessLevel = "Beginner";
                        break;
                    case 2:
                        fitnessLevel = "Intermediate";
                        break;
                    case 3:
                        fitnessLevel = "Advanced";
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return fitnessLevel;
    }

    // Method to validate yes/no input
    private static String getYesOrNoInput(Scanner scanner, String question) {
        String input = null;
        while (input == null) {
            System.out.println(question);
            String userInput = scanner.next().trim().toLowerCase();
            if (userInput.equals("yes") || userInput.equals("no")) {
                input = userInput;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
        return input;
    }

    // Method to get user medical condition with input validation
    private static String getUserMedicalCondition(Scanner scanner) {
        String medicalCondition = null;
        while (medicalCondition == null) {
            try {
                System.out.println("\nPlease specify your medical condition:");
                System.out.println("1 - Asthma");
                System.out.println("2 - Heart Condition");
                System.out.println("3 - Joint Issues");
                System.out.println("4 - Other");
                System.out.println("======================================================");

                int medicalConditionChoice = scanner.nextInt();
                switch (medicalConditionChoice) {
                    case 1:
                        medicalCondition = "Asthma";
                        break;
                    case 2:
                        medicalCondition = "Heart Condition";
                        break;
                    case 3:
                        medicalCondition = "Joint Issues";
                        break;
                    case 4:
                        System.out.println("Please describe your condition:");
                        medicalCondition = scanner.next();
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return medicalCondition;
    }

    // Method to display medical advice based on the user's condition
    private static void displayMedicalAdvice(String medicalCondition) {
        System.out.print("  - Note: ");
        switch (medicalCondition) {
            case "Asthma":
                System.out.println("As you have asthma, avoid over-exertion, especially in cold or dry conditions.");
                break;
            case "Heart Condition":
                System.out.println("With a heart condition, it’s important to monitor your heart rate and avoid high-intensity exercises.");
                break;
            case "Joint Issues":
                System.out.println("Since you have joint issues, avoid high-impact activities that put stress on your knees, hips, and joints.");
                break;
            default:
                System.out.println("Since you have " + medicalCondition + ", please consult with a physician before starting this fitness plan.");
                break;
        }
    }
}
