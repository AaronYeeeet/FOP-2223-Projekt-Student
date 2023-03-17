package projekt;

import java.util.function.Function;

import static org.tudalgo.algoutils.student.Student.crash;
import static org.junit.jupiter.api.Assertions.*;


public class ComparableUnitTests<T extends Comparable<? super T>> {

    private final Function<Integer, T> testObjectFactory;

    private T[] testObjects;

    public ComparableUnitTests(Function<Integer, T> testObjectFactory) {
        this.testObjectFactory = testObjectFactory;
    }

    @SuppressWarnings("unchecked")
    public void initialize(int testObjectCount) {
        testObjects = (T[]) new Comparable<?>[testObjectCount];
        for (int i = 0; i < testObjectCount; i++) {
            testObjects[i] = testObjectFactory.apply(i);
        }
    }

    public void testBiggerThen() {                          // das ist ziemlich Scheiße gelöst H12.1 2. Abschnitt
        for (int i = 0; i < testObjects.length; i++) {      // analog testLessThan auch Scheiße
            for (int j = i - 1; j >= 0; j--) {
                int k = testObjects[i].compareTo(testObjects[j]);
                if (k > 0) k = 1;
                assertEquals(k, 1);
            }
        }
    }

    @SuppressWarnings("EqualsWithItself")
    public void testAsBigAs() {
        for (int i = 0; i < testObjects.length; i++) {
            assertEquals(testObjects[i].compareTo(testObjects[i]), 0);

        }
    }

    public void testLessThen() {                            // analog
        for (int i = 0; i < testObjects.length; i++) {
            for (int j = i + 1; j < testObjects.length; j++) {
                int k = testObjects[i].compareTo(testObjects[j]);
                if (k < 0) k = -1;
                assertEquals(k, -1);
            }
        }
    }
}
