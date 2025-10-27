package driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String[][] items;

        System.out.print("Enter the number of rows: ");
        int rows = scnr.nextInt();

        System.out.print("Enter the number of columns: ");
        int columns = scnr.nextInt();

        System.out.print("Enter the file name: ");
        String filename = scnr.next();

        scnr.close();

        items = new String[rows][columns];

        try {
            readData(items, filename);
            processData(items);
        } catch (FileNotFoundException e) {
            // Part A: catch rethrown FileNotFoundException
            System.out.println("File Exception: Cannot process data");
        }
    }

    // Part A – FileNotFoundException handling
    public static void readData(String[][] items, String filename) throws FileNotFoundException {
        try {
            FileInputStream fileStream = new FileInputStream("files/" + filename);
            Scanner fileInput = new Scanner(fileStream);
            String[] tokens;

            for (int i = 0; fileInput.hasNext(); i++) {
                tokens = fileInput.nextLine().split(",");
                for (int j = 0; j < tokens.length; j++) {
                    items[i][j] = tokens[j];
                }
            }

            fileInput.close();
        } catch (FileNotFoundException e) {
            // Print and rethrow exception
            System.out.println(e);
            throw e;
        }
    }

    // Part B – Exception handling while processing data
    public static void processData(String[][] items) {
        ArrayList<String> firstnames = new ArrayList<>();
        ArrayList<String> lastnames = new ArrayList<>();
        ArrayList<Double> averages = new ArrayList<>();
        ArrayList<Integer> digits = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            try {
                String first = null;
                String last = null;
                Double average = null;

                try {
                    first = items[i][0];
                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    // Missing first name
                    first = null;
                }

                try {
                    last = items[i][1];
                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    // Missing last name
                    last = null;
                }

                firstnames.add(first);
                lastnames.add(last);

                double sum = 0;
                int count = 0;

                for (int j = 2; j < items[i].length; j++) {
                    try {
                        if (items[i][j] != null) {
                            for (int k = 0; k < items[i][j].length(); k++) {
                                if (Character.isDigit(items[i][j].charAt(k))) {
                                    digits.add(Integer.parseInt(String.valueOf(items[i][j].charAt(k))));
                                }
                            }
                            sum += Double.parseDouble(items[i][j]);
                            count++;
                        }
                    } catch (NumberFormatException e) {
                        // https://docs.oracle.com/javase/8/docs/api/java/lang/NumberFormatException.html
                        // Skip invalid numbers
                    } catch (NullPointerException e) {
                        // Missing value, skip
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // Out-of-range index
                    }
                }

                try {
                    if (count > 0) {
                        average = sum / count;
                    } else {
                        average = null;
                    }
                } catch (ArithmeticException e) {
                    // https://docs.oracle.com/javase/8/docs/api/java/lang/ArithmeticException.html
                    average = null;
                }

                averages.add(average);
            } catch (Exception e) {
                // General catch for unforeseen errors
                averages.add(null);
            }
        }

        // Final output
        System.out.println(firstnames);
        System.out.println(lastnames);
        System.out.println(averages);
        System.out.println(digits);
    }
}
