import java.util.NoSuchElementException;

public class Combination {
    private boolean[] combination;

    public Combination(int n) {
        combination = new boolean[n];
    }

    public boolean[] next() {
        /* If there isn't next combination, throw an exception */
        if (!hasNext()) throw new NoSuchElementException();
        /* Hold onto the copy of the current combination */
        boolean[] combinationToReturn = combination.clone();
        /* If current combination contains only "true"s, there isn't next combination. */
        if (!containsFalse()) combination = null;
        else prepareNext(); /* Else, prepare the next combination */
        /* Return the current combination */
        return combinationToReturn;
    }

    public boolean hasNext() {
        return combination != null;
    }

    private void prepareNext() {
        /* Declare an int to hold current index, and initialize it to the last index of the combination list */
        int index = combination.length - 1;
        /* Iterate over the combination list from the end */
        while (index >= 0) {
            /* If the current index is false, change it to true and return from the method */
            if (!combination[index]) {
                combination[index] = true;
                return;
            }
            /* If the current index is true, change it to false and continue to the next index */
            else {
                combination[index] = false;
                index--;
            }
        }
    }

    private boolean containsFalse() {
        for (boolean choice : combination) {
            if (!choice) return true;
        }
        return false;
    }
}
