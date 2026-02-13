import java.util.Scanner;

public class CaesarCipher {

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isUpperCase(character)) {
                char ch = (char) (((int) character + shift - 65) % 26 + 65);
                result.append(ch);
            } else if (Character.isLowerCase(character)) {
                char ch = (char) (((int) character + shift - 97) % 26 + 97);
                result.append(ch);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - shift);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the text to be encrypted:");
        String text = scanner.nextLine();

        System.out.println("Enter the shift value:");
        int shift = scanner.nextInt();

        String encrypted = encrypt(text, shift);
        String decrypted = decrypt(encrypted, shift);

        System.out.println("Original Text: " + text);
        System.out.println("Encrypted Text: " + encrypted);
        System.out.println("Decrypted Text: " + decrypted);
    }
}
