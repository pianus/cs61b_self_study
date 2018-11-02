/**
 * A double-ended-query implementation using linked list (deque).
 * @param <T> desired reference type stored in deque
 * @author le
 */
public class LinkedListDeque<T> {

    private static class ItemNode<T> {

        public T item;
        public ItemNode<T> previous;
        public ItemNode<T> next;

        public ItemNode(T i, ItemNode p, ItemNode n) {
            item = i;
            previous = p;
            next = n;
        }
    }

    private int size;
    private ItemNode sentinel;

    /**
     * initialize linkedListDeque with a sentinel ItemNode.
     * the sentinel node's previous and next node is itself when initialized
     */
    /*
    Invariant:
    1. size is the length of LinkedListDeque.
    2. sentinel.next is the first item in the LinkedListDeque.
    3. sentinel.previous is the last item in the LinkedLIstDeque.
     */
    public LinkedListDeque() {
        sentinel = new ItemNode<Integer>(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;

        size = 0;
    }

    /**
     * add a item to the front of deque.
     * @param item the item added to the front of deque
     */
    public void addFirst(T item) {
        ItemNode first = new ItemNode(item, sentinel, sentinel.next);
        sentinel.next.previous = first;
        sentinel.next = first;

        size += 1;
    }

    /**
     * add a item to the last of deque.
     * @param item the item added to the last of deque
     */
    public void addLast(T item) {
        ItemNode last = new ItemNode(item, sentinel.previous, sentinel);
        sentinel.previous.next = last;
        sentinel.previous = last;
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
        ItemNode current = sentinel;
        for (int i = 0; i < size; i++) {
            current = current.next;
            System.out.print(current.item + " ");
        }
    }

    /**
     * remove the first ItemNode in the deque, and return the first Item.
     * @return first item in the deque
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        ItemNode<T> first = sentinel.next;
        sentinel.next.previous = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return first.item;
    }

    /**
     * remove the last ItemNode in the deque, and return the first Item.
     * @return last item in the deque
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        ItemNode<T> last = sentinel.previous;
        sentinel.previous.next = sentinel;
        sentinel.previous = sentinel.previous.previous;
        size -= 1;
        return last.item;
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
        ItemNode<T> current = sentinel;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }
}
