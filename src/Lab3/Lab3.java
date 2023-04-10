package Lab3;

import java.math.BigDecimal;
import java.util.*;

public class Lab3 {
    private static final double POSITIVE_ZERO = 0d;
    private String codeMessage;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private Map<Character, Double> alphabetPercentage = new LinkedHashMap<Character, Double>();
    private Map<Character, Double> alphabetFromMessagePercentage = new LinkedHashMap<Character, Double>();

    private void enterAlphabet() {
        alphabetPercentage.put('a', 7.25);
        alphabetPercentage.put('b', 1.25);
        alphabetPercentage.put('c', 3.5);
        alphabetPercentage.put('d', 4.25);
        alphabetPercentage.put('e', 12.75);
        alphabetPercentage.put('f', 3.0);
        alphabetPercentage.put('g', 2.0);
        alphabetPercentage.put('h', 3.5);
        alphabetPercentage.put('i', 7.75);
        alphabetPercentage.put('j', 0.25);
        alphabetPercentage.put('k', 0.5);
        alphabetPercentage.put('l', 3.75);
        alphabetPercentage.put('m', 2.75);
        alphabetPercentage.put('n', 7.75);
        alphabetPercentage.put('o', 7.5);
        alphabetPercentage.put('p', 2.75);
        alphabetPercentage.put('q', 0.5);
        alphabetPercentage.put('r', 8.5);
        alphabetPercentage.put('s', 6.0);
        alphabetPercentage.put('t', 9.25);
        alphabetPercentage.put('u', 3.0);
        alphabetPercentage.put('v', 1.5);
        alphabetPercentage.put('w', 1.5);
        alphabetPercentage.put('x', 0.5);
        alphabetPercentage.put('y', 2.25);
        alphabetPercentage.put('z', 0.25);
    }

    public void inputMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть повідомлення для декодування: ");
        codeMessage = scanner.nextLine();
    }

    private void inputMessageAlphabet() {
        codeMessage = codeMessage.toLowerCase();
        char charactrer;
        int index;
        int numberOfChar;
        double percent;
        for (int i = 0; i < 26; i++) {
            index = 0;
            numberOfChar = 0;
            charactrer = alphabet.charAt(i);
            do {
                index = codeMessage.indexOf(charactrer, index);
                if (index != -1) {
                    numberOfChar++;
                    index++;
                }
            } while (index != -1);
            percent = numberOfChar * 100 / (double) codeMessage.length();
            percent = round(percent, 2);
            alphabetFromMessagePercentage.put(alphabet.charAt(i), percent);
        }
    }

    public static double round(double x, int scale) {
        return round(x, scale, BigDecimal.ROUND_HALF_UP);
    }
    public static double round(double x, int scale, int roundingMethod) {
        try {
            final double rounded = (new BigDecimal(Double.toString(x))
                    .setScale(scale, roundingMethod))
                    .doubleValue();
            return rounded == POSITIVE_ZERO ? POSITIVE_ZERO * x : rounded;
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;
            } else {
                return Double.NaN;
            }
        }
    }

    private void countStepAndDecode() {

        Double[] idealArray = new Double[26];
        Double[] idealArraySorted = new Double[26];
        Double[] myArray = new Double[26];
        Double[] myArraySorted = new Double[26];

        alphabetPercentage.values().toArray(idealArray);
        alphabetPercentage.values().toArray(idealArraySorted);
        Arrays.sort(idealArraySorted, Comparator.reverseOrder());

        alphabetFromMessagePercentage.values().toArray(myArraySorted);
        Arrays.sort(myArraySorted, Comparator.reverseOrder());

        int idealIndex = Arrays.asList(idealArray).indexOf(idealArraySorted[0]);

        int step = 0;
        boolean foundStep = false;
        int counter;

        for (int i = 0; i < 7 && !foundStep; i++) {
            alphabetFromMessagePercentage.values().toArray(myArray);
            for (int delete = 0; delete < i; delete++) {
                myArray[Arrays.asList(myArray).indexOf(myArraySorted[delete])] = null;
            }
            counter = 0;
            int index = Arrays.asList(myArray).indexOf(myArraySorted[i]);
            step = Math.abs(index - idealIndex);
            alphabetFromMessagePercentage.values().toArray(myArray);
            for (int j = 0; j < 7; j++) {
                if (j != i) {
                    int elementIndex = Arrays.asList(myArray).indexOf(myArraySorted[j]);
                    myArray[elementIndex] = null;
                    elementIndex -= step;
                    if (elementIndex < 0) {
                        elementIndex += 26;
                    }
                    if (idealArray[elementIndex] > 7.0) {
                        counter++;
                    }
                }
            }
            if (counter >= 4) {
                foundStep = true;
            }
        }
        System.out.println("Декодоване повідомлення: " + decode(step));
    }

    private String decode(int offset) {
        String message = "";
        int realIndex;
        for (int i = 0; i < codeMessage.length(); i++) {
            realIndex = (alphabet.indexOf(codeMessage.charAt(i))) - offset;
            if (realIndex < 0) {
                realIndex += 26;
            }
            message += alphabet.charAt(realIndex);
        }

        return message;
    }

    public static void main(String[] args) {
        Lab3 lab = new Lab3();

        lab.enterAlphabet();
        lab.inputMessage();
        lab.inputMessageAlphabet();
        lab.countStepAndDecode();
    }
}
