import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Caesar Cipher");
            System.out.println("2. Playfair Cipher");
            System.out.println("3. Hill Cipher");
            System.out.println("4. RSA Cipher");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Call Caesar Cipher methods
                    CaesarCipherMenu();
                    break;
                case 2:
                    // Call Playfair Cipher methods
                    PlayfairCipherMenu();
                    break;
                case 3:
                    // Call Hill Cipher methods
                    HillCipherMenu();
                    break;
                case 4:
                    // Call RSA Cipher methods
                    RSACipherMenu();
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    public static void CaesarCipherMenu() {
        // Implement Caesar Cipher functionality
        System.out.println("Caesar Cipher selected.");
        // Add your Caesar Cipher methods here
    }

    public static void PlayfairCipherMenu() {
        // Implement Playfair Cipher functionality
        System.out.println("Playfair Cipher selected.");
        // Add your Playfair Cipher methods here
    }

    public static void HillCipherMenu() {
        // Implement Hill Cipher functionality
        System.out.println("Hill Cipher selected.");
        // Add your Hill Cipher methods here
    }

    public static void RSACipherMenu() {
        // Implement RSA Cipher functionality
        System.out.println("RSA Cipher selected.");
        // Add your RSA Cipher methods here
    }
}

