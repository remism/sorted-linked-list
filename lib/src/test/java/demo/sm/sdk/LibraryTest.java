package demo.sm.sdk;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class LibraryTest {



    @Test
    void testIntegerListIsOrdered() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertThat(list.getSize()).isEqualTo(0);
        assertThat(list.toArray()).containsExactly();


        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

        assertThat(list.getSize()).isEqualTo(6);
        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6);
    }

    @Test
    void testListContainsNull() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(SortedLinkedList::compareIntegersWithNullsLast);

        list.add(5);
        list.add(6);
        list.add(null);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);
        list.add(null);

        assertThat(list.getSize()).isEqualTo(8);
        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6, null, null);
    }

}
