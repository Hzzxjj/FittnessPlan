/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fittnessplan;

/**
 *
 * @author hanan
 */
public class StrengthPlan implements FitnessPlan {
    private final String planName = "Strength Plan";
    private final int minimumDuration = 120; // in minutes
    private final String requiredFitnessLevel = "Intermediate";
    private final String healthGoal = "Muscle Building";

    @Override
    public String getPlanName() {
        return planName;
    }

    @Override
    public int getMinimumDuration() {
        return minimumDuration;
    }

    @Override
    public String getRequiredFitnessLevel() {
        return requiredFitnessLevel;
    }

    @Override
    public String getHealthGoal() {
        return healthGoal;
    }

    @Override
    public int calculateAdditionalMinutes(String currentFitnessLevel) {
        switch (currentFitnessLevel) {
            case "Beginner": return 30;
            case "Intermediate": return 20;
            case "Advanced": return 10;
            default: throw new IllegalArgumentException("Invalid fitness level");
        }
    }
}
