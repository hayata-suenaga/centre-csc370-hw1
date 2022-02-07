public record Result(int value, int weight, boolean[] combination) {
    @Override
    public String toString() {
        return convertCombinationToString(combination) + "\n" + weight + "\n" + value;
    }

    private String convertCombinationToString(boolean[] combination) {
        String intString = "";
        for (boolean choice : combination) {
            intString += choice ? "1" : "0";
        }
        return intString;
    }
}
