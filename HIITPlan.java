public class HIITPlan implements FitnessPlan {
    private final String planName = "HIIT Plan";
    private final int minimumDuration = 90; // in minutes
    private final String requiredFitnessLevel = "Advanced";
    private final String healthGoal = "Improve Cardiovascular Health";

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