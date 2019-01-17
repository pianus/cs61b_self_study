package byog.Core;
import java.util.Iterator;

public class StringIterator implements Iterator<Character> {
    String s;
    int cur;

    public StringIterator(String s) {
        this.s = s;
        this.cur = 0;
    }

    @Override
    public boolean hasNext() {
        return s.length() > this.cur;
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            throw new NullPointerException();
        }
        cur += 1;
        return s.charAt(cur-1);

    }

}
