package Lab2;

import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String encryptedMessage = "";

        System.out.print("Введіть повідомлення: ");
        String message = scan.nextLine();
        System.out.print("Введіть ключ: ");
        String key = scan.nextLine();

        for(int i = 0; i < message.length() % key.length(); i++)
            message += message.charAt(i);

        for(int i = 0; i < message.length(); i += key.length()) {
            char[] buffer = new char[key.length()];

            for(int j = 0; j < buffer.length; j++) {
                buffer[Character.getNumericValue(key.charAt(j)) - 1] = message.charAt(i + j);
            }

            encryptedMessage += new String(buffer);
        }

        System.out.println("Закодоване повідомлення: " + encryptedMessage);
    }
}
