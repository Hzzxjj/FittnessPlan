public class CardioPlan implements FitnessPlan {
    private final String planName = "Cardio Plan";
    private final int minimumDuration = 150; // in minutes
    private final String requiredFitnessLevel = "Beginner";
    private final String healthGoal = "Weight Loss";

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
