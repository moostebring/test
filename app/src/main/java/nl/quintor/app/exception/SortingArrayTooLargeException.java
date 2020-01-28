package nl.quintor.app.exception;

public class SortingArrayTooLargeException extends Exception {
    public SortingArrayTooLargeException(String arrayLength) {
        super("Sorting array is too large. It may only contain 1 or 2 elements. Amount of elements present when Exception occurred: " + arrayLength);
    }
}
