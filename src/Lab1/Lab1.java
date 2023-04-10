package Lab1;

import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args) {
        final int STEP = 3;
        final String ALPHABET = "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя";
        final Scanner scan = new Scanner(System.in);
        String encryptedMessage = "";

        System.out.print("Введіть повідомлення: ");
        String message = scan.nextLine();

        for(int i = 0; i < message.length(); i++) {
            if(!ALPHABET.contains(Character.toString(message.toLowerCase().charAt(i)))) {
                encryptedMessage += message.charAt(i);
                continue;
            }

            int index = ALPHABET.indexOf(message.toLowerCase().charAt(i));
            index += STEP;
            index %= 33;
            encryptedMessage += ALPHABET.charAt(index);
        }

        System.out.println("Закодоване повідомлення: " + encryptedMessage);
    }
}
