import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {
    @Test
    public void addFirstTest() {
        // zero to one, one to more
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(4);
        assertThat(L.toList()).containsExactly(4).inOrder();
        L.addFirst(5);
        L.addFirst(7);
        assertThat(L.toList()).containsExactly(7, 5, 4).inOrder();
    }

    @Test
    public void removeAndAddFirstTest() {
        // Change after remove operations
        // removeFirst
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(4);
        L.addFirst(5);
        L.removeFirst();
        L.removeFirst();
        L.addFirst(6);
        L.addFirst(7);
        assertThat(L.toList()).containsExactly(7, 6).inOrder();

        // removeLast
        Deque61B<Integer> LL = new ArrayDeque61B<>();
        LL.addFirst(4);
        LL.addFirst(5);
        LL.removeLast();
        LL.removeLast();
        LL.addFirst(6);
        LL.addFirst(7);
        assertThat(LL.toList()).containsExactly(7, 6).inOrder();
    }

    @Test
    public void addLastTest() {
        // zero to one, one to more
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addLast(4);
        assertThat(L.toList()).containsExactly(4).inOrder();
        L.addLast(5);
        L.addLast(7);
        assertThat(L.toList()).containsExactly(4, 5, 7).inOrder();
    }

    @Test
    public void removeAndAddLastTest() {
        // Change after remove operations
        // removeFirst
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addLast(4);
        L.addLast(5);
        L.removeFirst();
        L.removeFirst();
        L.addLast(6);
        L.addLast(7);
        assertThat(L.toList()).containsExactly(6, 7).inOrder();

        // removeLast
        Deque61B<Integer> LL = new ArrayDeque61B<>();
        LL.addLast(4);
        LL.addLast(5);
        LL.removeLast();
        LL.removeLast();
        LL.addLast(6);
        LL.addLast(7);
        assertThat(LL.toList()).containsExactly(6, 7).inOrder();
    }

    @Test
    public void getTest() {
        // get from empty, get idx out of boundary, normal
        Deque61B<Integer> L = new ArrayDeque61B<>();
        assertThat(L.get(0)).isEqualTo(null);

        L.addFirst(3);
        L.addLast(1);
        L.addLast(5);
        L.addFirst(7); // [7, 3, 1, 5]

        assertThat(L.get(-100)).isEqualTo(null);
        assertThat(L.get(-1)).isEqualTo(null);
        assertThat(L.get(0)).isEqualTo(7);
        assertThat(L.get(3)).isEqualTo(5);
        assertThat(L.get(4)).isEqualTo(null);
        assertThat(L.get(200)).isEqualTo(null);
    }

    @Test
    public void getTestCircular() {
        // get from empty, get idx out of boundary, normal
        // For firstTag = 3, lastTag = 4
        // Just a test for circular array implementation, the
        // concrete position of the tag is not a big deal
        Deque61B<Integer> L = new ArrayDeque61B<>();

        L.addFirst(3);
        L.addFirst(1);
        L.addFirst(5);
        L.addFirst(7);
        L.addFirst(8); // at the end of the array
        assertThat(L.get(0)).isEqualTo(8);

        Deque61B<Integer> LL = new ArrayDeque61B<>();

        LL.addLast(3);
        LL.addLast(1);
        LL.addLast(5);
        LL.addLast(7);
        LL.addLast(8); // at the front of the array
        assertThat(LL.get(4)).isEqualTo(8);
    }

    @Test
    public void isEmptyTest() {
        // empty, not empty
        Deque61B<Integer> L = new ArrayDeque61B<>();
        Deque61B<Integer> L1 = new ArrayDeque61B<>();
        assertThat(L.isEmpty()).isTrue();

        L.addFirst(3);
        assertThat(L.isEmpty()).isFalse();
        L1.addLast(1);
        assertThat(L1.isEmpty()).isFalse();

        L.addLast(5);
        L.addFirst(7); // [7, 3, 5]
        assertThat(L.isEmpty()).isFalse();
    }

    @Test
    public void sizeTest() {
        // empty, not empty(grow)
        Deque61B<Integer> L = new ArrayDeque61B<>();
        Deque61B<Integer> L1 = new ArrayDeque61B<>();
        assertThat(L.size()).isEqualTo(0);

        L.addFirst(3);
        assertThat(L.size()).isEqualTo(1);
        L1.addLast(1);
        assertThat(L1.size()).isEqualTo(1);

        L.addLast(5);
        L.addFirst(7); // [7, 3, 5]
        assertThat(L.size()).isEqualTo(3);
    }

    @Test
    public void removeAndSizeTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        Deque61B<Integer> LL = new ArrayDeque61B<>();
        assertThat(L.size()).isEqualTo(0);

        // remove to empty then check size work
        L.addFirst(3);
        L.addLast(5);
        L.addFirst(7); // [7, 3, 5]
        L.removeLast();
        L.removeLast();
        L.removeFirst();
        assertThat(L.size()).isEqualTo(0);
        assertThat(L.toList()).isEmpty();
        L.addFirst(3);
        L.addLast(5);
        assertThat(L.size()).isEqualTo(2);

        // remove from empty and check size work
        LL.removeFirst();
        assertThat(LL.size()).isEqualTo(0);
        LL.addLast(8);
        LL.addFirst(9);
        assertThat(LL.size()).isEqualTo(2);
    }

    @Test
    public void toListTest() {
        // empty, not empty(grow)
        Deque61B<Integer> L = new ArrayDeque61B<>();
        Deque61B<Integer> LL = new ArrayDeque61B<>();
        assertThat(L.toList()).isEmpty();

        L.addFirst(3);
        assertThat(L.toList()).containsExactly(3).inOrder();

        LL.addLast(3);
        assertThat(LL.toList()).containsExactly(3).inOrder();

        L.addLast(5);
        L.addFirst(7); // [7, 3, 5]
        assertThat(L.toList()).containsExactly(7, 3, 5).inOrder();
        LL.addLast(5);
        LL.addFirst(7); // [7, 3, 5]
        assertThat(LL.toList()).containsExactly(7, 3, 5).inOrder();
    }

    @Test
    public void toListTestCircular() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(3);
        L.addFirst(4);
        L.addFirst(5);
        L.addFirst(6);
        L.addFirst(7);
        assertThat(L.toList()).containsExactly(7, 6, 5, 4, 3).inOrder();
    }

    @Test
    public void removeFirstTest() {
        // remove from empty, addFirst -> removeFirst to zero
        // addLast -> removeFirst to zero
        Deque61B<Integer> L = new ArrayDeque61B<>();
        assertThat(L.removeFirst()).isEqualTo(null);
        assertThat(L.toList()).isEmpty();

        L.addFirst(4);
        assertThat(L.removeFirst()).isEqualTo(4);
        assertThat(L.toList()).isEmpty();

        L.addLast(4);
        assertThat(L.removeFirst()).isEqualTo(4);
        assertThat(L.toList()).isEmpty();

        // remove first to one
        L.addFirst(4);
        L.addFirst(5);
        L.addLast(6);
        L.addLast(7); // [5, 4, 6, 7]
        assertThat(L.removeFirst()).isEqualTo(5);
        assertThat(L.removeFirst()).isEqualTo(4);
        assertThat(L.removeFirst()).isEqualTo(6); // remove second to last element
        assertThat(L.toList()).containsExactly(7).inOrder();
    }

    @Test
    public void removeLastTest() {
        // remove from empty, addFirst -> removeFirst to zero
        // addLast -> removeFirst to zero
        Deque61B<Integer> L = new ArrayDeque61B<>();
        assertThat(L.removeLast()).isEqualTo(null);
        assertThat(L.toList()).isEmpty();

        L.addFirst(4);
        assertThat(L.removeLast()).isEqualTo(4);
        assertThat(L.toList()).isEmpty();

        L.addLast(4);
        assertThat(L.removeLast()).isEqualTo(4);
        assertThat(L.toList()).isEmpty();

        // remove last to one
        L.addFirst(4);
        L.addFirst(5);
        L.addLast(6);
        L.addLast(7); // [5, 4, 6, 7]
        assertThat(L.removeLast()).isEqualTo(7);
        assertThat(L.removeLast()).isEqualTo(6);
        assertThat(L.removeLast()).isEqualTo(4); // remove second to last element
        assertThat(L.toList()).containsExactly(5).inOrder();
    }

    @Test
    public void resizeUpTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        Deque61B<Integer> L1 = new ArrayDeque61B<>();
        // Verify triggered by addFirst
        int count = 12;
        for (int i = 0; i <= count; i++) {
            L.addFirst(i);
        }
        assertThat(L.toList()).containsExactly(12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        // Verify triggered by addLast
        for (int i = 0; i <= count; i++) {
            L1.addLast(i);
        }
        assertThat(L1.toList()).containsExactly( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }

    @Test
    public void resizeUpAndDownTest() {
        // Has been resize up and down
        Deque61B<Integer> L = new ArrayDeque61B<>();
        // Verify triggered by removeFirst
        int count = 10;
        for (int i = 0; i < count; i++) {
            L.addFirst(i);
        } // 9-0, len 16
        for (int i = 0; i < count / 2; i++) {
            L.removeFirst();
        } // 4-0, len16
        L.removeFirst();
        L.removeFirst();
        assertThat(L.toList()).containsExactly( 2, 1, 0);

        Deque61B<Integer> L1 = new ArrayDeque61B<>();
        // Verify triggered by removeLast
        for (int i = 0; i < count; i++) {
            L1.addFirst(i);
        } // 9-0, len 16
        for (int i = 0; i < count / 2; i++) {
            L1.removeLast();
        } // 9-5, len 16
        L1.removeLast();
        L1.removeLast();
        assertThat(L1.toList()).containsExactly(9, 8, 7);
    }

}
