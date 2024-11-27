import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FitnessPlanMain {
    private static User loggedInUser;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        List<FitnessPlan> plans = new ArrayList<>();
        plans.add(new CardioPlan());
        plans.add(new StrengthPlan());
        plans.add(new FlexibilityPlan());
        plans.add(new HIITPlan());
        plans.add(new YogaPlan());

        Scanner scanner = new Scanner(System.in);

        System.out.println("========= Fitness Plan Recommendation System =========");

        // loop
        while (loggedInUser == null) {
            System.out.println("1 - Login");
            System.out.println("2 - Create Account");
            System.out.println("3 - Log out");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                login(scanner);
            } else if (choice.equals("2")) {
                createAccount(scanner);
            } else if (choice.equals("3")) {
                System.out.println("Log out successful");
                scanner.close();
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        // Display Message
        System.out.println("\nLogin successful!");
        System.out.println("Welcome, " + loggedInUser.getUsername() + "!");
        System.out.println("Phone: " + loggedInUser.getPhone());

        // Get user fitness level using numeric input
        String userFitnessLevel = getUserFitnessLevel(scanner);

        // Ask for medical conditions with validation for "yes/no"
        String hasMedicalCondition = getYesOrNoInput(scanner, "Do you have any medical conditions? (yes/no)");

        String medicalCondition = "None";
        if (hasMedicalCondition.equals("yes")) {
            medicalCondition = getUserMedicalCondition(scanner);
        }

        // Generate checksum for the user's fitness level and medical condition
        String fitnessLevelChecksum = ChecksumUtil.generateChecksum(userFitnessLevel);
        String medicalConditionChecksum = ChecksumUtil.generateChecksum(medicalCondition);

        // Simulate storing the checksum (in a real app, store in the database)
        System.out.println("Stored Fitness Level Checksum: " + fitnessLevelChecksum);
        System.out.println("Stored Medical Condition Checksum: " + medicalConditionChecksum);

        // Verify integrity of the data before proceeding (just for demonstration)
        verifyDataIntegrity(userFitnessLevel, medicalCondition, fitnessLevelChecksum, medicalConditionChecksum);

        // Display suggested fitness plans and their required weekly exercise time
        System.out.println("\n========= Suggested Fitness Plans =========");
        for (FitnessPlan plan : plans) {
            if (userFitnessLevel.equalsIgnoreCase(plan.getRequiredFitnessLevel())) {
                int additionalMinutes = plan.calculateAdditionalMinutes(userFitnessLevel);
                int totalWeeklyMinutes = plan.getMinimumDuration() * 5; // Assuming 5 days a week
                System.out.println("-------------------------------------------");
                System.out.println("Fitness Plan: " + plan.getPlanName());
                System.out.println("  - Minimum Duration: "
                        + plan.getMinimumDuration() + " minutes/day");
                System.out.println(
                        "  - Additional Minutes (based on your fitness level): "
                                + additionalMinutes + " minutes/day");
                System.out.println("  - Total Weekly Time: "
                        + totalWeeklyMinutes + " minutes/week");

                // Additional notes based on medical condition
                if (!medicalCondition.equals("None")) {
                    displayMedicalAdvice(medicalCondition);
                }
            }
        }
        System.out.println("===========================================");

        scanner.close();
    }

    // Method to verify data integrity
    private static void verifyDataIntegrity(String fitnessLevel, String medicalCondition, String storedFitnessChecksum,
            String storedMedicalChecksum) throws NoSuchAlgorithmException {
        boolean fitnessLevelIntegrity = ChecksumUtil.verifyChecksum(fitnessLevel, storedFitnessChecksum);
        boolean medicalConditionIntegrity = ChecksumUtil.verifyChecksum(medicalCondition, storedMedicalChecksum);

        if (fitnessLevelIntegrity && medicalConditionIntegrity) {
            System.out.println("Data integrity verified. The input has not been tampered with.");
        } else {
            System.out.println("Data integrity compromised! The input has been tampered with.");
        }
    }

    // Method to hash the password and generate a checksum
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found!", e);
        }
    }

    // Login method: Hash entered password and compare with stored checksum
    private static void login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Hash the entered password to generate the checksum
        String hashedPassword = hashPassword(password);

        loggedInUser = User.login(username, hashedPassword);
        if (loggedInUser == null) {
            System.out.println("Invalid username or password. Please try again.");
        } else {
            System.out.println("Login successful!");
        }
    }

    // Create account method: Hash password before saving
    private static void createAccount(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your phone number (10 digits): ");
        String phone = scanner.nextLine();
        while (!phone.matches("\\d{10}")) {
            System.out.print("Invalid phone number. Please enter a valid 10 digit phone number: ");
            phone = scanner.nextLine();
        }

        System.out.print("Enter a password (exactly 8 characters): ");
        String password = scanner.nextLine();
        while (password.length() != 8) {
            System.out.print("Invalid password. It must be exactly 8 characters long. Please try again: ");
            password = scanner.nextLine();
        }

        // Hash the password before saving it in the database
        String hashedPassword = hashPassword(password);

        if (User.createAccount(username, phone, hashedPassword)) {
            System.out.println("Account created successfully! You can now log in.");
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
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
                scanner.nextLine(); // Consume newline
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
            scanner.nextLine(); // Consume newline
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
                scanner.nextLine(); // Consume newline
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
                        medicalCondition = scanner.nextLine();
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
                System.out.println(
                        "With a heart condition, it’s important to monitor your heart rate and avoid high-intensity exercises.");
                break;
            case "Joint Issues":
                System.out.println(
                        "Since you have joint issues, avoid high-impact activities that put stress on your knees, hips, and joints.");
                break;
            default:
                System.out.println("Since you have " + medicalCondition
                        + ", please consult with a physician before starting this fitness plan.");
                break;
        }
    }
}
