package acquire;

public class Hint implements HintInterface{
    private static String[] possibleHits = {"Hint 1", "Hint 2", "Hint 3"};

    protected Hint() {
    }
    protected String getHint() {
        return possibleHits[0];
    }
    protected String getHint(int index) {
        return possibleHits[index];
    }
}
