package nl.quintor.app.exception;

public class SortingMethodNotValidException extends Exception {
    public SortingMethodNotValidException(String s) {
        super("Sorting method is not valid, only ASC or DESC is valid, method tried: " + s);
    }
}
