package demo.sm.sdk;

import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.*;

import static org.assertj.core.api.Assertions.*;


class SortedLinkedListTest {

    @Test
    void testIntegerListIsSorted() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        assertThat(list).hasSize(0);
        assertThat(list).isEmpty();
        assertThat(list.toArray()).containsExactly();


        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

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
    void testRemove() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Comparator.nullsLast(Integer::compareTo));

        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(null);
        list.add(null);
        list.add(1);
        list.add(3);

        assertThat(list.removeAll(Arrays.asList(8, 9, 10))).isFalse();
        assertThat(list.removeAll(Arrays.asList(null, 4, 3))).isTrue();
        assertThat(list.toArray()).containsExactly(1, 3, 5, 6, null);
        list.add(3);
        list.add(4);
        list.add(null);

        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6, null, null);
        assertThat(list.remove(1)).isTrue();
        assertThat(list.toArray()).containsExactly(3, 3, 4, 5, 6, null, null);
        assertThat(list.remove(6)).isTrue();
        assertThat(list.toArray()).containsExactly(3, 3, 4, 5, null, null);
        assertThat(list.remove(3)).isTrue();
        assertThat(list.toArray()).containsExactly(3, 4, 5, null, null);
        assertThat(list.remove(null)).isTrue();
        assertThat(list.toArray()).containsExactly(3, 4, 5, null);
        assertThat(list.remove(4)).isTrue();
        assertThat(list.toArray()).containsExactly(3, 5, null);
        assertThat(list.remove(5)).isTrue();
        assertThat(list.toArray()).containsExactly(3, null);
        assertThat(list.remove(null)).isTrue();
        assertThat(list.toArray()).containsExactly(3);
        assertThat(list.remove(5)).isFalse();
        assertThat(list.remove(3)).isTrue();
        assertThat(list.toArray()).containsExactly();
        assertThat(list).isEmpty();
    }

    @Test
    void testContains() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Comparator.nullsLast(Integer::compareTo));

        list.add(5);
        list.add(6);
        list.add(null);
        list.add(3);

        assertThat(list.contains(null)).isTrue();
        assertThat(list.contains(5)).isTrue();
        assertThat(list.contains(6)).isTrue();
        assertThat(list.contains(3)).isTrue();
        assertThat(list.contains(10)).isFalse();

        assertThat(list.containsAll(Arrays.asList(5, 6))).isTrue();
        assertThat(list.containsAll(Arrays.asList(null, 3, 5, 6))).isTrue();

        assertThat(list.containsAll(Arrays.asList(null, 3, 5, 6, 8))).isFalse();

    }

    @Test
    void testRetainAll() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Comparator.nullsLast(Integer::compareTo));

        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(null);
        list.add(null);
        list.add(1);
        list.add(3);

        assertThat(list.retainAll(Arrays.asList(null, 1, 3, 4, 5, 6))).isFalse();
        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6, null, null);
        assertThat(list.retainAll(Arrays.asList(3, null, 4))).isTrue();
        assertThat(list.toArray()).containsExactly(3, 3, 4, null, null);
    }


    @Test
    void testAddAll() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Comparator.nullsLast(Integer::compareTo));

        assertThat(list.addAll(Collections.emptyList())).isFalse();

        assertThat(list.addAll(Arrays.asList(null, 5, 6, 3))).isTrue();
        assertThat(list.toArray()).containsExactly(3, 5, 6, null);

    }

    @Test
    void testClear() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Integer::compare);

        list.add(5);
        list.add(6);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);

        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6);
        list.clear();
        assertThat(list).isEmpty();
        assertThat(list).size().isEqualTo(0);
        assertThat(list.toArray()).containsExactly();
    }


    @Test
    void testListContainsNull() {
        SortedLinkedList<Integer> list = new SortedLinkedList<>(Comparator.nullsLast(Integer::compareTo));

        list.add(5);
        list.add(6);
        list.add(null);
        list.add(3);
        list.add(4);
        list.add(1);
        list.add(3);
        list.add(null);

        assertThat(list).hasSize(8);
        assertThat(list.toArray()).containsExactly(1, 3, 3, 4, 5, 6, null, null);
    }

    @Test
    void testStringListIsSorted() {
        Collator collator = Collator.getInstance(Locale.forLanguageTag("cs_CZ"));
        SortedLinkedList<String> list = new SortedLinkedList<>(collator::compare);

        list.add("C");
        list.add("c");
        list.add("A");
        list.add("Č");
        list.add("ř");
        list.add("Ř");
        list.add("r");
        list.add("9");
        list.add("R");
        list.add("č");

        assertThat(list).hasSize(10);
        assertThat(list.toArray()).containsExactly("9", "A", "c", "C", "č", "Č", "r", "R", "ř", "Ř");
    }

}
