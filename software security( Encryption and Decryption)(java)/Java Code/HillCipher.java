import java.util.Scanner;

public class HillCipher {

    private static int[][] keyMatrix;
    private static int matrixSize;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the size of the key matrix (n for nxn):");
        matrixSize = scanner.nextInt();
        keyMatrix = new int[matrixSize][matrixSize];

        System.out.println("Enter the key matrix values row by row:");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        
        }

        int determinant = determinant(keyMatrix, matrixSize);
        if (determinant == 0) {
            throw new IllegalArgumentException("The matrix is not invertible.");
        }
        scanner.nextLine();  // Consume the newline

        System.out.println("Enter the plaintext to be encrypted:");
        String plaintext = scanner.nextLine();
        plaintext = prepareText(plaintext);

        String encryptedText = encrypt(plaintext);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }

    private static String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        while (text.length() % matrixSize != 0) {
            text += 'X';  // Padding with 'X' if text length is not a multiple of matrixSize
        }
        return text;
    }

    private static String encrypt(String plaintext) {
        int[] textVector = new int[matrixSize];
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += matrixSize) {
            for (int j = 0; j < matrixSize; j++) {
                textVector[j] = plaintext.charAt(i + j) - 'A';
            }
            int[] resultVector = matrixVectorMultiply(keyMatrix, textVector);
            for (int j = 0; j < matrixSize; j++) {
                ciphertext.append((char) ((resultVector[j] % 26) + 'A'));
            }
        }

        return ciphertext.toString();
    }

    private static String decrypt(String ciphertext) {
        int[] textVector = new int[matrixSize];
        StringBuilder plaintext = new StringBuilder();
        int[][] inverseKeyMatrix = invertMatrix(keyMatrix);

        for (int i = 0; i < ciphertext.length(); i += matrixSize) {
            for (int j = 0; j < matrixSize; j++) {
                textVector[j] = ciphertext.charAt(i + j) - 'A';
            }
            int[] resultVector = matrixVectorMultiply(inverseKeyMatrix, textVector);
            for (int j = 0; j < matrixSize; j++) {
                plaintext.append((char) ((resultVector[j] % 26 + 26) % 26 + 'A'));
            }
        }

        return plaintext.toString();
    }

    private static int[] matrixVectorMultiply(int[][] matrix, int[] vector) {
        int[] result = new int[matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            result[i] = 0;
            for (int j = 0; j < matrixSize; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    private static int[][] invertMatrix(int[][] matrix) {
        int[][] adjugate = new int[matrixSize][matrixSize];
        int[][] inverse = new int[matrixSize][matrixSize];
        int determinant = determinant(matrix, matrixSize);
        int inverseDeterminant = modInverse(determinant, 26);

        if (determinant == 0 || inverseDeterminant == -1) {
            throw new IllegalArgumentException("The matrix is not invertible.");
        }

        adjugate = adjugate(matrix);

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                inverse[i][j] = adjugate[i][j] * inverseDeterminant % 26;
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26;
                }
            }
        }

        return inverse;
    }

    private static int determinant(int[][] matrix, int n) {
        int det = 0;
        if (n == 1) {
            return matrix[0][0];
        }
        int[][] subMatrix = new int[n][n];
        int sign = 1;

        for (int x = 0; x < n; x++) {
            getCofactor(matrix, subMatrix, 0, x, n);
            det += sign * matrix[0][x] * determinant(subMatrix, n - 1);
            sign = -sign;
        }

        return det % 26;
    }

    private static void getCofactor(int[][] matrix, int[][] temp, int p, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = matrix[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    private static int[][] adjugate(int[][] matrix) {
        int[][] adj = new int[matrixSize][matrixSize];
        if (matrixSize == 1) {
            adj[0][0] = 1;
            return adj;
        }
        int sign = 1;
        int[][] temp = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                getCofactor(matrix, temp, i, j, matrixSize);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign * determinant(temp, matrixSize - 1)) % 26;
                if (adj[j][i] < 0) {
                    adj[j][i] += 26;
                }
            }
        }
        return adj;
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }
}
