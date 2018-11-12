/**
 * a deque implementation using array.
 * @param <T> the object type of this deque
 * @author le
 */
public class ArrayDeque<T> implements Deque<T> {

    /*
    invariants:
    1. first item is returned by t[(nextFirst + 1) % (t.length]
    2. last item is returned by t[(nextLast - 1) % (t.length]
    3. size = ((nextLast - nextFirst) % t.length -1
    4. user only have access to index number < ((nextLast - nextFirst) % t.length
    5. the size of array t do not less than 8
    6. the list start at (nextFirst + 1) % (t.length
     */
    private int size;
    private T[] t;
    private int nextFirst;
    private int nextLast;

    /** return the index of first list value in the array. */
    private int first() {
        return Math.floorMod((nextFirst + 1), t.length);
    }


    public ArrayDeque() {
        t = (T[]) new Object[8];
        nextFirst = Math.floorMod(-1, t.length);
        nextLast = 0;
        size = 0;
    }

    /**
     * resize the array t to allow more items stored in.
     * @param length the length of array t changing to
     */
    private void resizeArray(int length) {
        if (t.length == length) {
            return;
        }

        T[] newArray = (T[]) new Object[length];
        //this method only works with current class, doesn't impliment every single case
        if (length > t.length) {
            //only when array is full, the array needs to increase size
            //a this time, nextFirst and nextLast are adjacent
            int frontLength = t.length - nextFirst - 1;
            int endLength = size - frontLength; //second part length of the list
            System.arraycopy(t, first(), newArray, 0, frontLength);
            System.arraycopy(t, 0, newArray, frontLength, endLength);
            nextFirst = length - 1;
            nextLast = size;
        } else {
            if (nextFirst < nextLast) {
                System.arraycopy(t, first(), newArray, 0, size);
                nextFirst = length - 1;
                nextLast = size;
            } else {
                int frontLength = t.length - nextFirst - 1;
                int endLength = size - frontLength; //second part length of the list
                System.arraycopy(t, first(), newArray, 0, frontLength);
                System.arraycopy(t, 0, newArray, frontLength, endLength);
                nextFirst = length - 1;
                nextLast = size;
            }

        }
        t = newArray;

    }

    /**
     * add item to the first position of the deque.
     * @param item the item added to the first of deque
     */
    public void addFirst(T item) {
        if (size >= t.length) {
            resizeArray(t.length * 2);
        }
        t[nextFirst] = item;
        nextFirst = Math.floorMod((nextFirst - 1), t.length);
        size += 1;
    }

    /**
     * add a item to the last of deque.
     * @param item the item added to the last of deque
     */
    public void addLast(T item) {
        if (size >= t.length) {
            resizeArray(t.length * 2);
        }
        t[nextLast] = item;
        nextLast = Math.floorMod((nextLast + 1), t.length);
        size += 1;
    }

    /**
     * return if the deque is an empty list.
     * @return boolean value of whether the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the current size of the deque.
     * @return size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * print out the human readable deque.
     */
    public void printDeque() {
        for (int i = nextFirst + 1; i < nextFirst + 1 + size; i++) {
            System.out.println(t[Math.floorMod(i, t.length)] + " ");
        }
    }

    /**
     * remove the first ItemNode in the deque, and return the first Item.
     * @return first item in the deque
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (t.length >= 16 && size <= 0.25 * t.length) {
            resizeArray(t.length / 2);
        }
        int removeIndex = Math.floorMod((nextFirst + 1), t.length);
        T first = t[removeIndex];
        nextFirst = Math.floorMod(nextFirst + 1, t.length);
        size -= 1;
        t[removeIndex] = null;
        return first;
    }

    /**
     * remove the last ItemNode in the deque, and return the first Item.
     * @return last item in the deque
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (t.length >= 16 && size <= 0.25 * t.length) {
            resizeArray(t.length / 2);
        }
        int removeIndex = Math.floorMod((nextLast - 1), t.length);
        T last = t[removeIndex];
        nextLast = Math.floorMod(nextLast - 1, t.length);
        size -= 1;
        t[removeIndex] = null;
        return last;
    }

    /**
     * get the item at the locaton index, if index out of the range of deque,
     *  return null.
     * @param index the location of desired item
     * @return item at location index
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return t[(first() + index) % t.length];
    }
}
