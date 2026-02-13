import java.util.*;

public class PlayfairCipher {
    private char[][] matrix = new char[5][5];
    private String key;

    public PlayfairCipher(String key) {
        this.key = key;
        generateKeyMatrix();
    }

    private void generateKeyMatrix() {
        String combinedKey = prepareText(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ");
        Set<Character> set = new LinkedHashSet<>();
        for (char c : combinedKey.toCharArray()) {
            set.add(c);
        }
        Iterator<Character> iterator = set.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (iterator.hasNext()) {
                    matrix[i][j] = iterator.next();
                }
            }
        }
    }

    private String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');
        StringBuilder prepared = new StringBuilder(text);
        for (int i = 0; i < prepared.length() - 1; i += 2) {
            if (prepared.charAt(i) == prepared.charAt(i + 1)) {
                prepared.insert(i + 1, 'X');
            }
        }
        if (prepared.length() % 2 != 0) {
            prepared.append('X');
        }
        return prepared.toString();
    }

    private int[] findPosition(char letter) {
        int[] position = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == letter) {
                    position[0] = i;
                    position[1] = j;
                    break;
                }
            }
        }
        return position;
    }

    private String processText(String text, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        text = prepareText(text);
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) {
                result.append(matrix[posA[0]][(posA[1] + (encrypt ? 1 : 4)) % 5]);
                result.append(matrix[posB[0]][(posB[1] + (encrypt ? 1 : 4)) % 5]);
            } else if (posA[1] == posB[1]) {
                result.append(matrix[(posA[0] + (encrypt ? 1 : 4)) % 5][posA[1]]);
                result.append(matrix[(posB[0] + (encrypt ? 1 : 4)) % 5][posB[1]]);
            } else {
                result.append(matrix[posA[0]][posB[1]]);
                result.append(matrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }

    public String encrypt(String text) {
        return processText(text, true);
    }

    public String decrypt(String text) {
        return processText(text, false);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the key for Playfair Cipher:");
        String key = scanner.nextLine();
        PlayfairCipher cipher = new PlayfairCipher(key);

        System.out.println("Enter the text to be encrypted:");
        String text = scanner.nextLine();

        String encrypted = cipher.encrypt(text);
        String decrypted = cipher.decrypt(encrypted);

        System.out.println("Original Text: " + text);
        System.out.println("Encrypted Text: " + encrypted);
        System.out.println("Decrypted Text: " + decrypted);

        scanner.close();
    }
}
