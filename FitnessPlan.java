public interface FitnessPlan {
    String getPlanName();
    int getMinimumDuration();
    String getRequiredFitnessLevel();
    String getHealthGoal();
    int calculateAdditionalMinutes(String currentFitnessLevel);

}
