public class OffByOne implements CharacterComparator{
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (Math.abs(diff) == 1) {
            return true;
        }
        return false;
    }
}
