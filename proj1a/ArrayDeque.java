/**
 * a deque implementation using array.
 * @param <T> the object type of this deque
 * @author le
 */
public class ArrayDeque<T> {

    /*
    invariants:
    1. first item is returned by t[(nextFirst + 1) % (arrayLength()]
    2. last item is returned by t[(nextLast - 1) % (arrayLength()]
    3. size = ((nextLast - nextFirst) % arrayLength() -1
    4. user only have access to index number < ((nextLast - nextFirst) % arrayLength()
    5. the size of array t do not less than 8
    6. the list start at (nextFirst + 1) % (arrayLength()
     */
    private int size;
    private T[] t;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque() {
        t = (T[]) new Object[8];
        nextFirst = -1 % arrayLength();
        nextLast = 0;
        size = 0;
    }

    //return the length of the array
    private int arrayLength() {
        return t.length;
    }

    /** return the index of first list value in the array */
    private int first() {
        return (nextFirst + 1) % arrayLength();
    }

    /** return the index of last list value in the array */
    private int last() {
        return (nextLast - 1) % arrayLength();
    }

    /**
     * resize the array t to allow more items stored in
     * @param length the length of array t changing to
     */
    private void resizeArray(int length) {
        if (arrayLength() == length) {
            return;
        }

        T[] newArray = (T[]) new Object[length];
        if (length > arrayLength()) {
            if (first() < last()) {
                //when the list don't 'turn' in the array
                System.arraycopy(t, first(), newArray, last(), size);
            } else {
                //when the list 'turn'
                //frontlength is size of first part of list before 'turn'
                int frontLength = arrayLength() - first();
                int endLength = size - frontLength; //second part length of the list
                System.arraycopy(t, first(), newArray, first(), frontLength);
                System.arraycopy(t, 0, newArray, arrayLength(), endLength);
            }
        } else if (length < arrayLength()) {
            System.arraycopy(t, nextFirst + 1, newArray, (nextFirst + 1) % length, size);

            nextFirst = (nextFirst + 1) % length;
            nextLast = (nextFirst + size + 1) % length;
        }
        t = newArray;

    }

    /**
     * add item to the first position of the deque.
     * @param item the item added to the first of deque
     */
    public void addFirst(T item) {
        if (size >= arrayLength()) {
            resizeArray(t.length * 2);
        }
        t[nextFirst] = item;
        nextFirst = (nextFirst - 1) % arrayLength();
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
        nextLast = (nextLast + 1) % arrayLength();
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
            System.out.println(t[i % arrayLength()] + " ");
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
        if (size >= 16 && size < 0.25 * arrayLength()) {
            resizeArray(t.length / 2);
        }
        int removeIndex = (nextFirst + 1) % arrayLength();
        T first = t[removeIndex];
        nextFirst += 1;
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
        if (size >= 16 && size < 0.25 * t.length) {
            resizeArray(t.length / 2);
        }
        int removeIndex = (nextLast - 1) % arrayLength();
        T last = t[removeIndex];
        nextLast -= 1;
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
        return t[(first() + index) % arrayLength()];
    }
}
