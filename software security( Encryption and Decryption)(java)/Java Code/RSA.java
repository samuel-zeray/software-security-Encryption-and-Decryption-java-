import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RSA {

    private static BigInteger p, q, n, phi, e, d;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get prime numbers p and q from user
        System.out.println("Enter prime number p:");
        p = scanner.nextBigInteger();
        System.out.println("Enter prime number q:");
        q = scanner.nextBigInteger();

        // Step 2: Calculate n and phi(n)
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Step 3: Choose e, where 1 < e < phi(n) and gcd(e, phi(n)) = 1
        System.out.println("Choose encryption key e (must be coprime with phi(n)):");
        e = scanner.nextBigInteger();
        while (!isValidEncryptionKey(e, phi)) {
            System.out.println("Encryption key e is not valid. Please choose another:");
            e = scanner.nextBigInteger();
        }

        // Step 4: Calculate d, the modular multiplicative inverse of e mod phi(n)
        d = e.modInverse(phi);

        // Display public and private keys
        System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");

        // Encryption and Decryption
        System.out.println("\nEnter plaintext to be encrypted:");
        scanner.nextLine(); // Consume newline
        String plaintext = scanner.nextLine();
        BigInteger encrypted = encrypt(plaintext);
        System.out.println("Encrypted Message (Ciphertext): " + encrypted);

        System.out.println("\nEnter ciphertext to be decrypted:");
        BigInteger ciphertext = scanner.nextBigInteger();
        String decrypted = decrypt(ciphertext);
        System.out.println("Decrypted Message (Plaintext): " + decrypted);

        scanner.close();
    }

    // Encryption: C = (M^e) mod n
    public static BigInteger encrypt(String plaintext) {
        BigInteger m = new BigInteger(plaintext.getBytes(StandardCharsets.UTF_8)); // Encode plaintext to bytes
        return m.modPow(e, n);
    }

    // Decryption: M = (C^d) mod n
    public static String decrypt(BigInteger ciphertext) {
        BigInteger decrypted = ciphertext.modPow(d, n);
        byte[] bytes = decrypted.toByteArray();

        // Remove leading zero byte if present
        if (bytes.length > 1 && bytes[0] == 0) {
            byte[] temp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, temp, 0, temp.length);
            bytes = temp;
        }

        return new String(bytes, StandardCharsets.UTF_8); // Decode byt3es to plaintext
    }

    // Check if e is valid encryption key (1 < e < phi(n) and gcd(e, phi(n)) = 1)
    public static boolean isValidEncryptionKey(BigInteger e, BigInteger phi) {
        return e.compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0 && e.gcd(phi).equals(BigInteger.ONE);
    }
}
