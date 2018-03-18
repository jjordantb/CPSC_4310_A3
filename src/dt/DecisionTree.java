package dt;

import dt.data.DataSample;
import dt.feature.Feature;
import dt.label.Label;
import dt.purity.GiniPurity;
import dt.purity.PurityCalculator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DecisionTree {

    private Node root;

    private final double purityPercentage = 0.90;
    private final int maxDepth = 25;
    private final PurityCalculator purityCalculator = new GiniPurity();

    public Node getRoot() {
        return root;
    }

    public void train(final List<DataSample> dataSamples, final List<Feature> features) {
        this.root = this.growTree(dataSamples, features, 1);
    }

    public Label classify(final DataSample dataSample) {
        Node node = this.getRoot();
        while (!node.isLeaf()) {
            if (dataSample.has(node.getFeature())) {
                node = node.getChildren().get(0);
            } else {
                node = node.getChildren().get(1);
            }
        }
        return node.getLabel();
    }

    public Node growTree(final List<DataSample> dataSamples, final List<Feature> features, int currentDepth) {

        Label current;
        if((current = this.getLabel(dataSamples)) != null) {
            return Node.newLeafNode(current);
        }

        boolean shouldStop = features.isEmpty() || currentDepth >= maxDepth;
        if (shouldStop) {
            return Node.newLeafNode(this.getMajorityLabel(dataSamples));
        }

        Feature bestSplit = this.getBest(dataSamples, features);
        List<List<DataSample>> splitData = bestSplit.split(dataSamples);

        List<Feature> newFeatures = features.stream().filter(f -> !f.equals(bestSplit)).collect(Collectors.toList());
        Node node = Node.newNode(bestSplit);
        for (List<DataSample> samples : splitData) {
            if (samples.isEmpty()) {
                node.addChild(Node.newLeafNode(getMajorityLabel(dataSamples)));
            } else {
                node.addChild(this.growTree(samples, newFeatures, currentDepth + 1));
            }
        }
        return node;
    }

    private Feature getBest(final List<DataSample> dataSamples, List<Feature> features) {
        double impurity = 1;
        Feature best = null;
        for (Feature f : features) {
            List<List<DataSample>> splitData = f.split(dataSamples);
            double purity = splitData.parallelStream().filter(list -> !list.isEmpty())
                    .mapToDouble(this.purityCalculator::calcFor).average().getAsDouble();
            if (purity < impurity) {
                best = f;
                impurity = purity;
            }
        }
        return best;
    }

    private Label getMajorityLabel(final List<DataSample> dataSamples) {
        return dataSamples.parallelStream().collect(Collectors.groupingBy(DataSample::getLabel, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    private Label getLabel(final List<DataSample> dataSamples) {
        Map<Label, Long> labelCount = dataSamples.parallelStream().collect(
                Collectors.groupingBy(DataSample::getLabel, Collectors.counting()));
        long count = dataSamples.size();
        for (Label label : labelCount.keySet()) {
            long numLabels = labelCount.get(label);
            if (((double)numLabels) / ((double)count) >= this.purityPercentage) {
                return label;
            }
        }
        return null;
    }

}
