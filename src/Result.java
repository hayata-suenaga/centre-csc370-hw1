import java.util.Arrays;

public record Result(int value, int weight, boolean[] combination) {
    @Override
    public String toString() {
        return combination.toString() + "\n" + weight + "\n" + value;
    }
}
