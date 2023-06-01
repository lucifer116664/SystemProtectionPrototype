package Lab4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Lab4 {
    final static long HEAD_SIZE = 120;
    static int textSize = 0;

    public static void main(String[] args) throws IOException {
        encode();
        decode();
        System.out.println("Done.");
    }

    private static void encode() {
        System.out.println("Encoding started...");
        try (FileInputStream img = new FileInputStream("src/Lab4/resources/NULP.bmp");
             FileInputStream msg = new FileInputStream("src/Lab4/resources/Message.txt");
             FileOutputStream out = new FileOutputStream("src/Lab4/resources/EncodedImage.bmp");) {

            int c, mb;
            byte clearBit1 = (byte) 0xFE; // 254; // 11111110

            for (int i = 1; i <= HEAD_SIZE; i++) out.write(img.read());

            while ((mb = msg.read()) != -1) {
                for (int bit = 7; bit >= 0; bit--) {
                    c = img.read() & clearBit1;
                    c = (c | ((mb >> bit) & 1));
                    out.write(c);
                }
                textSize++;
            }
            for (int bit = 7; bit >= 0; bit--) {
                out.write(img.read());
            }
            while ((c = img.read()) != -1) out.write(c);
            System.out.println("Encoding was successfully finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void decode() {
        System.out.println("Decoding started...");
        try(FileInputStream img = new FileInputStream("src/Lab4/resources/EncodedImage.bmp");
            FileOutputStream out = new FileOutputStream("src/Lab4/resources/DecodedMessage.txt")) {

            for (int i = 1; i <= HEAD_SIZE; i++)
                img.read();

            byte[] result = new byte[textSize];
            for (int i = 0; i < textSize; i++) {
                for (int bit = 0; bit <= 7; bit++) {
                    result[i] = (byte) ((result[i] << 1) | (img.read() & 1));
                }
                out.write(result[i]);
            }
            System.out.println("Decoding was successfully finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
