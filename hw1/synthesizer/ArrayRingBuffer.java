package synthesizer;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {

    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        if (capacity <= 0) {
            throw new ArrayStoreException();
        }
        this.capacity = capacity;
        this.fillCount = 0;
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
    }

    private class RBIterator<T> implements Iterator<T> {
        private T[] rbArray;
        private int size;
        private int next;
        public RBIterator(ArrayRingBuffer<T> rb) {
            rbArray = rb.rb.clone();
            size = rb.rb.length;
            next = 0;
        }

        @Override
        public boolean hasNext() {
            return next <= size;
        }
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T current = rbArray[next];
            next += 1;
            return current;
        }
    }

    public Iterator<T> iterator() {
        return new RBIterator(this);
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last = (last + 1) % capacity;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T t = rb[first];
        fillCount -= 1;
        first = (first + 1) % capacity;
        return t;
    }
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer is empty");
        }
        return rb[first];
    }
}
