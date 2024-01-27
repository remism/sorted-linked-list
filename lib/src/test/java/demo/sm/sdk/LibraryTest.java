package demo.sm.sdk;

import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.Locale;

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

    @Test
    void testStringListIsOrdered() {
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

        assertThat(list.getSize()).isEqualTo(10);
        assertThat(list.toArray()).containsExactly( "9", "A", "c", "C", "č", "Č", "r", "R", "ř", "Ř");
    }

}
