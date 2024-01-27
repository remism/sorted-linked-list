package demo.sm.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryTest {

    @Test
    void testListIsOrdered() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertEquals(0, list.getSize());
        assertArrayEquals(new Object[0], list.toArray());


        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

        assertEquals(6, list.getSize());
        assertArrayEquals(new Object[]{1, 3, 3, 4, 5, 6}, list.toArray());
    }
}
