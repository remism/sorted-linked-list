package demo.sm.sdk;

import lombok.Getter;

import java.util.Comparator;

public class SortedLinkedList<T> {
    transient private Node first;

    @Getter
    transient int size;
    private final Comparator<T> comparator;

    public SortedLinkedList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

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


    public Object[] toArray() {
        if (first == null) {
            return new Object[0];
        } else {
            Object[] result = new Object[size];
            Node current = first;
            int i = 0;
            while (current != null) {
                result[i++] = current.value;
                current = current.next;
            }
            return result;
        }
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

}
