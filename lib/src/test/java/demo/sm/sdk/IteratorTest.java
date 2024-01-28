package demo.sm.sdk;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IteratorTest {
    @Test
    void testIteratorLoop() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

        LinkedList<Object> iteratorResult = new LinkedList<>();

        for (Integer i : list) {
            iteratorResult.add(i);
        }
        assertThat(iteratorResult.toArray()).containsExactly(1, 3, 3, 4, 5, 6);
    }

    @Test
    void testIteratorRemoveMultiple() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

        list.removeIf(item -> Objects.equals(item, 3));//both "3" should be removed
        assertThat(list.toArray()).containsExactly(1, 4, 5, 6);
    }

    @Test
    void testIteratorIllegalRemove() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        Iterator<Integer> iterator = list.iterator();
        assertThatThrownBy(iterator::remove).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testIteratorNoSuchElement() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertThatThrownBy(() -> list.iterator().next()).isInstanceOf(NoSuchElementException.class);

        list.add(5);

        Iterator<Integer> iterator = list.iterator();

        assertThat(iterator.next()).isEqualTo(5);


        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);

    }

    @Test
    void testIteratorRemoveFirst() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);
        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

        list.removeIf(item -> Objects.equals(item, 1));
        assertThat(list.toArray()).containsExactly(3, 3, 4, 5, 6);
    }

    @Test
    void testIteratorRemoveLast() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);
        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);


        list.removeIf(item -> Objects.equals(item, 6));
        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5);

        list.removeIf(item -> true);
        assertThat(list).isEmpty();
        assertThat(list.toArray()).containsExactly();
    }

    @Test
    void testIteratorRemoveAll() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);
        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

        list.removeIf(item -> true);
        assertThat(list).isEmpty();
        assertThat(list.toArray()).containsExactly();
    }
}
