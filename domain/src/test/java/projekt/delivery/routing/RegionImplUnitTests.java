package projekt.delivery.routing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import projekt.ComparableUnitTests;
import projekt.ObjectUnitTests;
import projekt.base.Location;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.tudalgo.algoutils.student.Student.crash;
import static org.junit.jupiter.api.Assertions.*;


public class RegionImplUnitTests {

    private static ObjectUnitTests<RegionImpl> objectUnitTests;

    @BeforeAll
    public static void initialize() {
        Function<Integer, RegionImpl> testObjectFactory = i -> {
            int x = i % 10;
            int y = i % 7;
            // knoten + kanten ????
            return new RegionImpl();
        };


        objectUnitTests = new ObjectUnitTests<>(testObjectFactory, null);

        objectUnitTests.initialize(10);
    }

    @Test
    public void testEquals() {
        objectUnitTests.testEquals();
    }

    @Test
    public void testHashCode() {
        objectUnitTests.testHashCode();
    }

    @Test
    public void testNodes() {

        RegionImpl testObject = new RegionImpl();

        NodeImpl nodeA = new NodeImpl(testObject, "Region A", new Location(0,0), new HashSet<>());
        NodeImpl nodeB = new NodeImpl(testObject, "Region A", new Location(1,0), new HashSet<>());
        NodeImpl nodeC = new NodeImpl(testObject, "Region A", new Location(0,1), new HashSet<>());
        testObject.putNode(nodeA);
        testObject.putNode(nodeB);
        testObject.putNode(nodeC);

        assertTrue(testObject.getNodes().contains(nodeA));
        assertTrue(testObject.getNodes().contains(nodeB));
        assertTrue(testObject.getNodes().contains(nodeC));

        assertEquals(testObject.getNode(new Location(0,0)), nodeA);
        assertEquals(testObject.getNode(new Location(1,0)), nodeB);
        assertEquals(testObject.getNode(new Location(0,1)), nodeC);

        assertNull(testObject.getNode(new Location(1,1)));

        RegionImpl fasleRegion = new RegionImpl();
        NodeImpl nodeFalse = new NodeImpl(fasleRegion, "Region False", new Location(0,0), new HashSet<>());
        assertThrowsExactly(IllegalArgumentException.class, () -> testObject.putNode(nodeFalse));
    }

    @Test
    public void testEdges() {
        RegionImpl testObject = new RegionImpl();

        NodeImpl nodeA = new NodeImpl(testObject, "Region A", new Location(0,0), new HashSet<>());
        NodeImpl nodeB = new NodeImpl(testObject, "Region A", new Location(1,0), new HashSet<>());
        NodeImpl nodeC = new NodeImpl(testObject, "Region A", new Location(0,1), new HashSet<>());
        testObject.putNode(nodeA);
        testObject.putNode(nodeB);
        testObject.putNode(nodeC);

        EdgeImpl aa = new EdgeImpl(testObject, "Kante A", nodeA.getLocation(), nodeA.getLocation(), 0);
        EdgeImpl ab = new EdgeImpl(testObject, "Kante A", nodeA.getLocation(), nodeB.getLocation(), 0);
        EdgeImpl bc = new EdgeImpl(testObject, "Kante A", nodeB.getLocation(), nodeC.getLocation(), 0);
        testObject.putEdge(aa);
        testObject.putEdge(ab);
        testObject.putEdge(bc);


        assertEquals(testObject.getEdge(nodeA.getLocation(), nodeA.getLocation()), aa);
        assertEquals(testObject.getEdge(nodeA.getLocation(), nodeB.getLocation()), ab);
        assertEquals(testObject.getEdge(nodeB.getLocation(), nodeC.getLocation()), bc);

        assertTrue(testObject.getEdges().contains(aa));
        assertTrue(testObject.getEdges().contains(ab));
        assertTrue(testObject.getEdges().contains(bc));

        RegionImpl fasleRegion = new RegionImpl();
        EdgeImpl falseRegion = new EdgeImpl(fasleRegion, "Kante false", nodeB.getLocation(), nodeC.getLocation(), 0);
        assertThrowsExactly(IllegalArgumentException.class, () -> testObject.putEdge(falseRegion));

        NodeImpl nodeFalse = new NodeImpl(fasleRegion, "Region A", new Location(5,5), new HashSet<>());
        EdgeImpl falseNode = new EdgeImpl(testObject, "Kante false", nodeFalse.getLocation(), nodeC.getLocation(), 0);
        assertThrowsExactly(IllegalArgumentException.class, () -> testObject.putEdge(falseNode));

        EdgeImpl falseBoth = new EdgeImpl(fasleRegion, "Alles false", nodeFalse.getLocation(), nodeC.getLocation(), 0);

        assertNull(testObject.getEdge(new Location(69, 420), new Location(1337, 187)));
    }
}
