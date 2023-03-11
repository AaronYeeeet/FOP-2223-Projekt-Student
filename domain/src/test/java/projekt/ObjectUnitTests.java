package projekt;

import java.util.function.Function;

import static org.tudalgo.algoutils.student.Student.crash;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectUnitTests<T> {

    private final Function<Integer, T> testObjectFactory;
    private final Function<T, String> toString;

    private T[] testObjects;
    private T[] testObjectsReferenceEquality;
    private T[] testObjectsContentEquality;

    public ObjectUnitTests(Function<Integer, T> testObjectFactory, Function<T, String> toString) {
        this.testObjectFactory = testObjectFactory;
        this.toString = toString;
    }

    @SuppressWarnings("unchecked")
    public void initialize(int testObjectCount) {
        testObjects = (T[]) new Object[testObjectCount];
        testObjectsReferenceEquality = (T[]) new Object[testObjectCount];
        testObjectsContentEquality = (T[]) new Object[testObjectCount];

        for (int i = 0; i < testObjectCount; i++) {
            T obj = testObjectFactory.apply(i);
            testObjects[i] = obj;
            testObjectsReferenceEquality[i] = testObjects[i];
            do {
                testObjectsContentEquality[i] = testObjectFactory.apply(i);
            } while (testObjectsContentEquality[i].equals(testObjects[i]));
        }
    }

    public void testEquals() {
        for (int i = 0; i < testObjects.length; i++) {
            assertEquals(testObjects[i], testObjectsContentEquality[i]);
            assertEquals(testObjects[i], testObjectsReferenceEquality[i]);
            assertEquals(testObjectsContentEquality[i], testObjectsReferenceEquality[i]);
            for (int j = 0; j < testObjects.length; j++) {
                if (j != i){
                    assertNotEquals(testObjects[i], testObjects[j]);
                }
            }
        }
    }

    public void testHashCode() {
        for (int i = 0; i < testObjects.length; i++) {
            assertEquals(testObjects[i].hashCode(), testObjectsContentEquality[i].hashCode());
            assertEquals(testObjects[i].hashCode(), testObjectsReferenceEquality[i].hashCode());
            assertEquals(testObjectsContentEquality[i].hashCode(), testObjectsReferenceEquality[i].hashCode());
            for (int j = 0; j < testObjects.length; j++) {
                if (j != i){
                    assertNotEquals(testObjects[i].hashCode(), testObjects[j].hashCode());
                }
            }
        }
    }

    public void testToString() {
        for (int i = 0; i < testObjects.length; i++) {
            assertEquals(testObjects[i].toString(), toString.apply(testObjects[i]));
        }
    }
}
