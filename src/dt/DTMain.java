package dt;

import au.com.bytecode.opencsv.CSVReader;
import dt.data.DataSample;
import dt.data.SeedDataSample;
import dt.feature.Feature;
import dt.feature.P;
import dt.feature.PredicateFeature;
import dt.label.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Parametric on 3/18/2018
 * @project CPSC_4310_A3
 */
public class DTMain {

    public static void main(String[] args) throws IOException {
        List<DataSample> dataSamples = read();
        List<Feature> features = defineFeatures();
        DecisionTree tree = new DecisionTree();
        tree.train(dataSamples, features);

        double correctCount = 0;
        final List<DataSample> test = read();
        for (DataSample sample : test) {
            final Label classy;
            System.out.println("Guess: " + (classy = tree.classify(sample)) + " Actual: " + sample.getLabel());
            if (sample.getLabel().getName().equals(classy.getName())) {
                correctCount++;
            }
        }
        System.out.println("Correctly guessed " + ((int)correctCount) + " of " + test.size() + " which is " + ((int)((correctCount / test.size()) * 100D)) + "%");
    }

    private static List<DataSample> read() throws IOException {
        final List<DataSample> dataSamples = new ArrayList<>();
        final Reader reader = Files.newBufferedReader(Paths.get("res/seeds.csv"));
        final CSVReader csv = new CSVReader(reader);

        String[] headers = null;

        String[] next;
        while ((next = csv.readNext()) != null) {
            if (headers == null) {
                headers = next;
                headers[0] = headers[0].substring(1, headers[0].length());
                continue;
            }
            final Object[] doubles = new Object[next.length];
            for (int i = 0; i < next.length; i++) {
                doubles[i] = Double.valueOf(next[i]);
            }
            final SeedDataSample dataSample = new SeedDataSample("seed_id", headers, doubles);
            dataSamples.add(dataSample);
        }
        return dataSamples;
    }

    private static List<Feature> defineFeatures() {
        final List<Feature> features = new ArrayList<>();

        features.add(PredicateFeature.create("seed_id", P.isEqual(1)));
        features.add(PredicateFeature.create("seed_id", P.isEqual(2)));
        features.add(PredicateFeature.create("seed_id", P.isEqual(3)));

        features.add(PredicateFeature.create("area", P.betweenD(10D, 11D)));
        features.add(PredicateFeature.create("area", P.betweenD(11D, 12D)));
        features.add(PredicateFeature.create("area", P.betweenD(12D, 13D)));
        features.add(PredicateFeature.create("area", P.betweenD(13D, 14D)));
        features.add(PredicateFeature.create("area", P.betweenD(14D, 15D)));
        features.add(PredicateFeature.create("area", P.betweenD(15D, 16D)));
        features.add(PredicateFeature.create("area", P.betweenD(17D, 18D)));
        features.add(PredicateFeature.create("area", P.betweenD(18D, 19D)));
        features.add(PredicateFeature.create("area", P.betweenD(20D, 21D)));
        features.add(PredicateFeature.create("area", P.betweenD(21D, 22D)));

        features.add(PredicateFeature.create("perimeter", P.betweenD(12D, 13D)));
        features.add(PredicateFeature.create("perimeter", P.betweenD(13D, 14D)));
        features.add(PredicateFeature.create("perimeter", P.betweenD(14D, 15D)));
        features.add(PredicateFeature.create("perimeter", P.betweenD(15D, 16D)));
        features.add(PredicateFeature.create("perimeter", P.betweenD(16D, 17D)));
        features.add(PredicateFeature.create("perimeter", P.betweenD(17D, 18D)));

        features.add(PredicateFeature.create("compactness", P.betweenD(0.80, 0.82)));
        features.add(PredicateFeature.create("compactness", P.betweenD(0.82, 0.84)));
        features.add(PredicateFeature.create("compactness", P.betweenD(0.84, 0.86)));
        features.add(PredicateFeature.create("compactness", P.betweenD(0.86, 0.88)));
        features.add(PredicateFeature.create("compactness", P.betweenD(0.88, 0.90)));
        features.add(PredicateFeature.create("compactness", P.betweenD(0.90, 0.92)));

        features.add(PredicateFeature.create("length", P.betweenD(4.5, 5)));
        features.add(PredicateFeature.create("length", P.betweenD(5, 5.5)));
        features.add(PredicateFeature.create("length", P.betweenD(5.5, 6)));
        features.add(PredicateFeature.create("length", P.betweenD(6, 6.5)));
        features.add(PredicateFeature.create("length", P.betweenD(6.5, 7)));
        features.add(PredicateFeature.create("length", P.betweenD(7, 7.5)));

        features.add(PredicateFeature.create("width", P.betweenD(2, 2.25)));
        features.add(PredicateFeature.create("width", P.betweenD(2.25, 2.5)));
        features.add(PredicateFeature.create("width", P.betweenD(2.5, 2.75)));
        features.add(PredicateFeature.create("width", P.betweenD(2.75, 3)));
        features.add(PredicateFeature.create("width", P.betweenD(3, 3.25)));
        features.add(PredicateFeature.create("width", P.betweenD(3.25, 3.5)));
        features.add(PredicateFeature.create("width", P.betweenD(3.5, 3.75)));
        features.add(PredicateFeature.create("width", P.betweenD(3.75, 4)));
        features.add(PredicateFeature.create("width", P.betweenD(4, 4.25)));
        features.add(PredicateFeature.create("width", P.betweenD(4.25, 4.5)));
        features.add(PredicateFeature.create("width", P.betweenD(4.5, 4.75)));
        features.add(PredicateFeature.create("width", P.betweenD(4.75, 5)));
        features.add(PredicateFeature.create("width", P.betweenD(5, 5.25)));

        features.add(PredicateFeature.create("asym", P.betweenD(0, 0.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(0.5, 1)));
        features.add(PredicateFeature.create("asym", P.betweenD(1, 1.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(1.5, 2)));
        features.add(PredicateFeature.create("asym", P.betweenD(2, 2.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(2.5, 3)));
        features.add(PredicateFeature.create("asym", P.betweenD(3, 3.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(3.5, 4)));
        features.add(PredicateFeature.create("asym", P.betweenD(4, 4.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(4.5, 5)));
        features.add(PredicateFeature.create("asym", P.betweenD(5, 5.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(5.5, 6)));
        features.add(PredicateFeature.create("asym", P.betweenD(6, 6.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(6.5, 7)));
        features.add(PredicateFeature.create("asym", P.betweenD(7, 7.5)));
        features.add(PredicateFeature.create("asym", P.betweenD(7.5, 8)));

        features.add(PredicateFeature.create("lengthk", P.betweenD(2.5, 3)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(3, 3.5)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(3.5, 4)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(4, 4.5)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(4.5, 5)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(5, 5.5)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(5.5, 6)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(6, 6.5)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(6.5, 7)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(7, 7.5)));
        features.add(PredicateFeature.create("lengthk", P.betweenD(7.5, 8)));

        return features;
    }

}
