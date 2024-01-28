package demo.sm.sdk;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.function.Predicate;

/**
 * Single-linked sorted list. This implementation is not synchronized.
 * <p>
 * It allows to store nulls as long as provided comparator is capable of comparing null. This effectively means that
 * nulls are first or last.
 *
 * @param <T> Type of items to be stored
 */
public class SortedLinkedList<T> implements Queue<T> {
    transient private Node first;

    @Getter(onMethod = @__(@Override))
    @Accessors(fluent = true)
    transient int size;
    private final Comparator<T> comparator;

    public SortedLinkedList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Inserts the element at specific position using comparator.
     *
     * @param value element whose presence in this collection is to be ensured
     * @return true if the collection was changed
     */
    @Override
    public boolean add(T value) {
        if (first == null) {
            first = new Node(value);
        } else {
            Node current = first;
            Node previous = null;
            while (true) {
                int comparisonResult = comparator.compare(value, current.value);
                if (comparisonResult > 0) {//new is greater than current, move on
                    if (current.next == null) {
                        current.next = new Node(value);
                        break;
                    } else {
                        previous = current;
                        current = current.next;
                    }
                } else if (comparisonResult == 0) {//new is equal to current
                    current.next = new Node(value, current.next);
                    break;
                } else {//assume previous is less (or is null) but current is greater
                    if (previous == null) {
                        first = new Node(value, current);
                    } else {
                        previous.next = new Node(value, current);
                    }
                    break;
                }
            }

        }
        size++;
        return true;
    }

    /**
     * Inserts the element at specific position using comparator. This is effectively the same as `add(T t)`.
     *
     * @param value element whose presence in this collection is to be ensured
     * @return true if the collection was changed
     */
    @Override
    public boolean offer(T t) {
        return add(t);
    }

    /**
     * Retrieves and removes first element of this collection or throws exception if collection is empty.
     * @return first element of this collection
     *
     * @throws NoSuchElementException if this queue is empty
     */
    @Override
    public T remove() {
        if (first == null) {
            throw new NoSuchElementException("List is empty");
        }
        T data = first.value;
        first = first.next;
        size--;
        return data;
    }

    /**
     * Retrieves and removes first element of this collection or null if the collection is empty.
     * @return first element of this collection
     */
    @Override
    public T poll() {
        if (first == null) {
            return null;
        }
        T data = first.value;
        first = first.next;
        size--;
        return data;
    }

    /**
     * Retrieves but does not remove first element of this collection or throws exception if collection is empty.
     * @return first element of this collection
     *
     * @throws NoSuchElementException if this queue is empty
     */
    @Override
    public T element() {
        if (first == null) {
            throw new NoSuchElementException("List is empty");
        }
        return first.value;
    }

    /**
     * Retrieves first element without removing it  null if collection is empty.
     * @return first element of this collection or null if collection is empty
     *
     */
    @Override
    public T peek() {
        if (first == null) {
            return null;
        }
        return first.value;
    }

    /**
     * Removes first occurrence of the specified element.
     * @param o element to be removed from this list, if present
     * @return true if element was removed, false if not present
     */
    @Override
    public boolean remove(Object o) {
        return removeIf(it -> Objects.equals(it.value, o), true);
    }

    /**
     * Checks whether all elements of c are contained in this collection
     * @param c collection to be checked for containment in this collection
     * @return returns true if all elements of c are contained in this collection
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    /**
     * Adds all elements of c to this collection
     * @param c collection containing elements to be added to this collection
     * @return returns true if this collection was modified
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        c.forEach(this::add);

        return true;
    }

    /**
     * Removes all elements of c from this collection
     * @param c collection containing elements to be removed from this collection
     * @return returns true if this collection was modified
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        //note that we cannot use anyMatch due to short-circuiting
        return c.stream().map(this::remove).filter(it -> it).count() > 0;
    }

    /**
     * Retains only elements of c in this collection. Elements of this collection
     * not contained in c will be removed.
     * @param c collection containing elements to be retained in this collection
     * @return true if this collection was modified
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return removeIf(it -> !c.contains(it.value), false);
    }

    /**
     * Removes all elements from this collection
     */
    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * Checks whether this collection contains zero elements.
     * @return true if this collection contains no element
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * Checks whether this collection contains element o
     * @param o element whose presence in this collection is to be tested
     * @return true, if this collection contains element o
     */
    @Override
    public boolean contains(Object o) {
        Node current = first;
        while(current != null) {
            if (Objects.equals(current.value, o)){
                return true;
            } else {
                current = current.next;
            }
        }
        return false;
    }

    /**
     * Creates a new iterator over elements in this collection
     * @return an sorted iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new SortedListIterator();
    }

    /**
     * Returns an array containing all elements in this collection in order defined by comparator.
     * @return sorted array
     */
    @Override
    public Object[] toArray() {
        if (first == null) {
            return new Object[0];
        } else {
            Object[] result = new Object[size];
            collectToArray(result);
            return result;
        }
    }

    /**
     * Returns an array containing all elements in this collection in order defined by comparator.
     * @param a the array into which the elements of this collection are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return sorted array
     * @param <E> target array; if the array is not big enough, new array of the same type will be created
     */
    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            a = (E[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }
        Object[] result = a;
        collectToArray(result);
        return a;
    }


    private void collectToArray(Object[] a) {
        Node current = first;
        int i = 0;
        while (current != null) {
            a[i++] = current.value;
            current = current.next;
        }
    }

    private boolean removeIf(Predicate<Node> condition, boolean stopAtFirst) {
        Node current = first;
        Node previous = null;
        boolean removed = false;
        while(current != null) {
            if (condition.test(current)){
                if (previous == null) {
                    first = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                removed = true;
                if (stopAtFirst) {
                    break;
                }
            } else {
                previous = current;
            }
            current = current.next;
        }
        return removed;
    }

    private class Node {
        private Node next;
        private final T value;

        public Node(T value) {
            this(value, null);
        }

        public Node(T value, Node next) {
            this.next = next;
            this.value = value;
        }

    }

    private class SortedListIterator implements Iterator<T> {
        private Node lastReturned;

        /*
        Keep track of "previous" elements during iteration, so we do not need to traverse one more time when element is
        removed (as implementation is single-linked).
        "previous" is used to unlink element being removed.
         */
        private Node previous;
        private Node next;
        private boolean canRemove;

        public SortedListIterator() {
            this.next = first;
            this.lastReturned = null;
            this.previous = null;
            this.canRemove = false;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (lastReturned != null) {
                previous = lastReturned;
            }

            lastReturned = next;
            next = next.next;
            canRemove = true;
            return lastReturned.value;
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Remove operation cannot be called at this time.");
            }

            if (lastReturned == first) {
                first = next;
            } else {
                previous.next = next;
            }
            size--;
            lastReturned = null;
            canRemove = false;
        }
    }

}
