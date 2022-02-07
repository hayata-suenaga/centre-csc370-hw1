import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    final private static String INPUT_FILE_PATH = "./out/production/hw_1/knap_input.txt";
    final private static String OUTPUT_FILE_PATH = "./output.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        /* Get the max weight for the knapsack from the standard input (command line) */
        final int maxWeight = getMaxWeight();
        /* Get the list of items available from the file input */
        List<Item> items = getItemsFromFile();
        /* Get the optimal solution */
        Result result = getResult(items, maxWeight);
        /* Write the solution to the file */
        writeToFile(OUTPUT_FILE_PATH, result.toString());
    }

    private static int getMaxWeight() {
        /* Instantiate a scanner to read from the command line */
        Scanner scanner = new Scanner(System.in);
        /* Prompt the user to type the max weight */
        System.out.print("Max weight for knapsack: ");
        /* Read, parse, and return the max weight */
        return Integer.parseInt(scanner.next());
    }

    private static List<Item> getItemsFromFile() throws FileNotFoundException {
        /* Open the file */
        File file = new File(INPUT_FILE_PATH);
        /* Initialize scanner to read from the opened file */
        Scanner scanner = new Scanner(file);

        /* Declare a list to store items */
        List<Item> items = new ArrayList<>();

        /* Read the file line by line */
        while (scanner.hasNextLine()) {
            /* Get the first line of an item */
            String line = scanner.nextLine();
            /* Ignore blank line at the end of the file, if any */
            if (line.isBlank()) break;
            /* Get the item name */
            String name = line;
            /* Get the item weight */
            int weight = Integer.parseInt(scanner.nextLine());
            /* Get the item value */
            int value = Integer.parseInt(scanner.nextLine());
            /* Instantiate an Item object and append it to the list of items */
            items.add(new Item(name, weight, value));
        }

        /* Return the list of items */
        return items;
    }

    private static void writeToFile(String filePath, String content) throws IOException {
        FileWriter writer = new FileWriter(OUTPUT_FILE_PATH);
        writer.write(content);
        writer.close();
    }

    private static Result getResult(List<Item> items, int maxWeight) {
        /* Declare and initialize the combination array. All elements are initialized to false */
        Combination combination = new Combination(items.size());
        /* Store info on the optimal combination */
        Result optimal = null;
        /* Keep looping while the combination is not all "true"s */
        while (combination.hasNext()) {
            /* Get the analysis of the combination */
            Result result = analyzeCombination(items, combination.next(), maxWeight);
            /* If the combination is illegal, don't consider the result */
            if (result == null) continue;
            /* Compare the sum of values with the current max */
            if (optimal == null || result.value() > optimal.value()) {
                optimal = result;
            }
        }
        /* Return the max value, weight and combination */
        return optimal;
    }

    private static Result analyzeCombination(List<Item> items, boolean[] combination, int maxWeight) {
        int valueSum = 0;
        int weightSum = 0;

        for (int i = 0; i < combination.length; i++) {
            if (!combination[i]) continue;
            Item item = items.get(i);
            valueSum += item.value();
            weightSum += item.weight();
        }
        /* If the total weight of items picked up exceeds the max weight, return null */
        if (weightSum > maxWeight) return null;
        return new Result(valueSum, weightSum, combination);
    }
}
