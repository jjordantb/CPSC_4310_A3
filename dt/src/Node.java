
import java.util.ArrayList;
import java.util.List;

public class Node {

    private final Feature feature;
    private final Label label;
    private final List<Node> children;

    private Node(Feature feature, Label label) {
        this.feature = feature;
        this.label = label;
        this.children = new ArrayList<>();
    }

    private Node(Feature feature) {
        this(feature, null);
    }

    public static Node newNode(final Feature feature) {
        return new Node(feature);
    }

    public static Node newLeafNode(final Label label) {
        return new Node(null, label);
    }

    public void addChild(final Node node) {
        this.children.add(node);
    }

    public List<Node> getChildren() {
        return children;
    }

    public Label getLabel() {
        return label;
    }

    public boolean isLeaf() {
        return this.label != null;
    }

    public Feature getFeature() {
        return feature;
    }
}
