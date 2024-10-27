import java.util.ArrayList;
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
        System.out.println("Select your fitness level:");
        System.out.println("1 - Beginner");
        System.out.println("2 - Intermediate");
        System.out.println("3 - Advanced");
        System.out.println("======================================================");
        int fitnessLevelChoice = scanner.nextInt();
        String userFitnessLevel;

        switch (fitnessLevelChoice) {
            case 1:
                userFitnessLevel = "Beginner";
                break;
            case 2:
                userFitnessLevel = "Intermediate";
                break;
            case 3:
                userFitnessLevel = "Advanced";
                break;
            default:
                System.out.println("Invalid choice. Please restart the program and select a valid option.");
                scanner.close();
                return; // Exit the program if the input is invalid
        }

        // Ask for medical conditions
        System.out.println("\nDo you have any medical conditions? (yes/no)");
        String hasMedicalCondition = scanner.next();

        String medicalCondition = "None";
        if (hasMedicalCondition.equalsIgnoreCase("yes")) {
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
                    medicalCondition = scanner.next(); // This can be extended to accept full input
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to 'None'.");
                    medicalCondition = "None";
            }
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
        }
        System.out.println("===========================================");

        scanner.close();
    }
}
