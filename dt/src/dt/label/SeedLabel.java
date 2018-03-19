package dt.label;

public class SeedLabel extends Label {

    private final double value;

    public SeedLabel(double value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return String.valueOf(this.value);
    }

    @Override
    public String toString() {
        return "SeedLabel@[" + this.getName() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == getClass()) {
            if (this == o) {
                return true;
            }
            return o instanceof Double && (Double) o == this.value;
        }
        return false;
    }

}
