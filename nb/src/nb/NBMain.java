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
 * @author Parametric on 3/19/2018
 * @project CPSC_4310_A3
 */
public class NBMain {

    public static void main(String[] args) throws IOException {
        final Classifier<Double, Double> classifier = new BayesClassifier<>();

        final Reader reader = Files.newBufferedReader(Paths.get("res/seeds.csv"));
        final CSVReader csv = new CSVReader(reader);

        int count = 0;
        String[] next;
        while ((next = csv.readNext()) != null) {
            if (!next[0].equals("\uFEFFarea")) {
                final Double[] doubles = new Double[next.length - 1];
                for (int i = 0; i < doubles.length; i++) {
                    doubles[i] = Double.valueOf(next[i]);
                }
                if (count % 2 == 0) { // training on even indexes
                    classifier.learn(Double.valueOf(next[7]), Arrays.asList(doubles));
                }
                count++;
            }
        }

        test(classifier);
    }

    private static void test(final Classifier<Double, Double> classifier) throws IOException {
        final Reader reader = Files.newBufferedReader(Paths.get("res/seeds.csv"));
        final CSVReader csv = new CSVReader(reader);

        int success = 0, total = 0;
        int count = 0;
        String[] next;
        while ((next = csv.readNext()) != null) {
            if (!next[0].equals("\uFEFFarea")) {
                final Double[] doubles = new Double[next.length - 1];
                for (int i = 0; i < doubles.length; i++) {
                    doubles[i] = Double.valueOf(next[i]);
                }
                if (count % 2 != 0) { // testing on odd indexes
                    double val;
                    System.out.println("Guess: " + (val = classifier.classify(Arrays.asList(doubles)).getCategory()) + " Actual: " + next[7]);
                    if (val == Double.valueOf(next[7])) {
                        success++;
                    }
                    total++;
                }
                count++;
            }
        }
        System.out.println(success + "/" + total + " are correct");
    }

}
