package Lab5;

import javax.crypto.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Lab5 {
    public static void encrypt(SecretKey key) throws Exception {
        FileOutputStream msgOut = new FileOutputStream("src/Lab5/resources/encryptedMsg.txt");

        byte[] message = Files.readAllBytes(Paths.get("src/Lab5/resources/msg.txt"));

        Cipher desCipher = Cipher.getInstance("DES");

        desCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedMsg = desCipher.doFinal(message);

        msgOut.write(encryptedMsg);
        msgOut.flush();

        System.out.println("Message was successfully encrypted!");
        msgOut.close();
    }

    public static void decrypt(SecretKey key) throws Exception {

        FileOutputStream msgOut = new FileOutputStream("src/Lab5/resources/decryptedMsg.txt");

        byte[] encryptedMessage = Files.readAllBytes(Paths.get("src/Lab5/resources/encryptedMsg.txt"));

        Cipher desCipher = Cipher.getInstance("DES");

        desCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedMsg = desCipher.doFinal(encryptedMessage);

        msgOut.write(decryptedMsg);
        msgOut.flush();

        System.out.println("Message was successfully decrypted!");
        msgOut.close();
    }

    public static void main(String[] args) {
        try {
            SecretKey key = KeyGenerator.getInstance("DES").generateKey();
            encrypt(key);
            decrypt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
