/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fittnessplan;



// "DP Least privilege" didnt use java.util.*; using specific libraries

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author hanan
 */

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
        System.out.println("Select your fitness level:");
        System.out.println("1 - Beginner");
        System.out.println("2 - Intermediate");
        System.out.println("3 - Advanced");
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

        // Ask for additional information
        System.out.println("Do you have any medical conditions? (yes/no)");
        String medicalCondition = scanner.next();

        // Display suggested fitness plans and their required weekly exercise time
        System.out.println("\nSuggested Fitness Plans:");
        for (FitnessPlan plan : plans) {
            if (userFitnessLevel.equalsIgnoreCase(plan.getRequiredFitnessLevel())) {
                int additionalMinutes = plan.calculateAdditionalMinutes(userFitnessLevel);
                int totalWeeklyMinutes = plan.getMinimumDuration() * 5; // Assuming 5 days a week
                System.out.println("Fitness Plan: " + plan.getPlanName() +
                                   ", Minimum Duration: " + plan.getMinimumDuration() +
                                   " minutes, Additional Minutes: " + additionalMinutes +
                                   ", Total Weekly Time: " + totalWeeklyMinutes + " minutes");

                // Additional notes based on medical condition
                if (medicalCondition.equalsIgnoreCase("yes")) {
                    System.out.println("Note: Please consult with a physician before starting any new exercise program.");
                }
            }
        }
        
        scanner.close();
    }
}