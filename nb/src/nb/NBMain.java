package nb;

import au.com.bytecode.opencsv.CSVReader;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author Jordan on 3/19/2018
 * @project CPSC_4310_A3
 */
public class NBMain {

    /*
        Main file, and authored by me, everything in the "de" package is a library
        Loads the data from res/seeds.csv
        I converted the file to CSV using excel and only did so for library usage
     */

    public static void main(String[] args) throws IOException {
        final Classifier<Integer, Double> classifier = new BayesClassifier<>();

        final Reader reader = Files.newBufferedReader(Paths.get("res/seeds.csv"));
        final CSVReader csv = new CSVReader(reader);

        int count = 0;
        String[] next;
        while ((next = csv.readNext()) != null) {
            if (!next[0].equals("\uFEFFarea")) {
                final Integer[] integers = new Integer[next.length - 1];
                for (int i = 0; i < integers.length; i++) {
                    // round down for discrete data, super hacky way of doing it but there is enough variation in the data to allow it
                    integers[i] = Double.valueOf(next[i]).intValue();
                }
                if (count % 6 != 0) { // Every 6th item is testing data
                    classifier.learn(Double.valueOf(next[7]), Arrays.asList(integers));
                }
                count++;
            }
        }
        test(classifier);
    }

    /**
     * Runs the test data on the provided classifier
     * @param classifier
     * @throws IOException
     */
    private static void test(final Classifier<Integer, Double> classifier) throws IOException {
        final Reader reader = Files.newBufferedReader(Paths.get("res/seeds.csv"));
        final CSVReader csv = new CSVReader(reader);

        int trueOne = 0, trueTwo = 0, trueThree = 0;
        int falseOne = 0, falseTwo = 0, falseThree = 0;
        int total = 0;
        int count = 0;
        String[] next;
        while ((next = csv.readNext()) != null) {
            if (!next[0].equals("\uFEFFarea")) {
                final Integer[] integers = new Integer[next.length - 1];
                for (int i = 0; i < integers.length; i++) {
                    // round down for discrete data, super hacky way of doing it but there is enough variation in the data to allow it
                    integers[i] = Double.valueOf(next[i]).intValue();
                }
                if (count % 6 == 0) { // Every 6th item is testing data
                    double val;
                    System.out.println("Guess: " + (int)(val = classifier.classify(Arrays.asList(integers)).getCategory()) + " Actual: " + next[7]);
                    if (val == Double.valueOf(next[7])) {
                        if (val == 1D) {
                            trueOne++;
                        } else if (val == 2D) {
                            trueTwo++;
                        } else if (val == 3D) {
                            trueThree++;
                        }
                    } else {
                        if (val == 1D) {
                            falseOne++;
                        } else if (val == 2D) {
                            falseTwo++;
                        } else if (val == 3D) {
                            falseThree++;
                        }
                    }
                    total++;
                }
                count++;
            }
        }
        System.out.println("--------------------------");
        System.out.println("Naive Bayes Results");
        System.out.println("--------------------------");
        double precOne = ((double)trueOne / (double)(trueOne + falseOne));
        double precTwo = ((double)trueTwo / (double)(trueTwo + falseTwo));
        double precThree = ((double)trueThree / (double)(trueThree + falseThree));
        System.out.println("Precision of 1: " + precOne);
        System.out.println("Precision of 2: " + precTwo);
        System.out.println("Precision of 3: " + precThree);
        System.out.println("Average Precision: " + (precOne + precTwo + precThree) / 3);
        System.out.println("--------------------------");
        double recallOne = (double) trueOne / (trueOne + falseTwo + falseThree);
        double recallTwo = (double) trueTwo / (trueTwo + falseOne + falseThree);
        double recallThree = (double) trueThree / (trueThree + falseOne + falseTwo);
        System.out.println("Recall of 1: " + recallOne);
        System.out.println("Recall of 2: " + recallTwo);
        System.out.println("Recall of 3: " + recallThree);
        System.out.println("Average Recall: " + (recallOne + recallTwo + recallThree) / 3);
        System.out.println("--------------------------");
        System.out.println("Classified " + (trueOne + trueTwo + trueThree) + " of " + total + " correctly");
        System.out.println("--------------------------");
    }
    
}
