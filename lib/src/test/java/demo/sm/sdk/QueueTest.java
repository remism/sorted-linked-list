package demo.sm.sdk;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QueueTest {

    @Test
    void testQueueOffer() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        list.offer(5);
        list.offer(6);
        list.offer(3);
        list.offer(4);
        list.offer(1);
        list.offer(3);

        assertThat(list).hasSize(6);
        assertThat(list).isNotEmpty();


        Integer[] expected = {1, 3, 3, 4, 5, 6};
        Object[] expectedObjects = expected;
        assertThat(list.toArray()).containsExactly(expectedObjects);
        assertThat(list.toArray(new Integer[0])).containsExactly(expected);
        assertThat(list.toArray(new Integer[6])).containsExactly(expected);
        assertThat(list.toArray(new Integer[8])).containsExactly(Arrays.copyOf(expected, 8));
    }

    @Test
    void testQueueRemove() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertThatThrownBy(list::remove).isInstanceOf(NoSuchElementException.class);

        list.offer(5);
        list.offer(6);
        list.offer(3);
        list.offer(4);
        list.offer(1);
        list.offer(3);

        assertThat(list.remove()).isEqualTo(1);
        assertThat(list.toArray()).containsExactly(3, 3, 4, 5, 6);
    }

    @Test
    void testQueuePoll() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertThat(list.poll()).isNull();

        list.offer(5);
        list.offer(6);
        list.offer(3);
        list.offer(4);
        list.offer(1);
        list.offer(3);

        assertThat(list.poll()).isEqualTo(1);
        assertThat(list.toArray()).containsExactly(3, 3, 4, 5, 6);
    }

    @Test
    void testQueueElement() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertThatThrownBy(list::element).isInstanceOf(NoSuchElementException.class);

        list.offer(5);
        list.offer(6);
        list.offer(3);
        list.offer(4);
        list.offer(1);
        list.offer(3);

        assertThat(list.element()).isEqualTo(1);
        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6);
    }

    @Test
    void testQueuePeek() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertThat(list.peek()).isNull();

        list.offer(5);
        list.offer(6);
        list.offer(3);
        list.offer(4);
        list.offer(1);
        list.offer(3);

        assertThat(list.peek()).isEqualTo(1);
        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6);
    }
}
