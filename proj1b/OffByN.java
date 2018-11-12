public class OffByN implements CharacterComparator {
    private int number;
    public OffByN(int N) {
        number = N;
    }

    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (Math.abs(diff) == number) {
            return true;
        }
        return false;
    }
}
