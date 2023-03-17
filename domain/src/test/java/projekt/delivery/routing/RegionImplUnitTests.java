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
            RegionImpl region = new RegionImpl();
            NodeImpl node1 = new NodeImpl(region, null, new Location(i,i),null);
            NodeImpl node2 = new NodeImpl(region, null, new Location(i+1,i-1),null);
            EdgeImpl edge = new EdgeImpl(region,null,node1.getLocation(),node2.getLocation(),i);
            region.putNode(node1);
            region.putNode(node2);
            region.putEdge(edge);
            return region;
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

        NodeImpl nodeA = new NodeImpl(testObject, "Node A", new Location(0,0), new HashSet<>());
        NodeImpl nodeB = new NodeImpl(testObject, "Node B", new Location(1,0), new HashSet<>());
        NodeImpl nodeC = new NodeImpl(testObject, "Node C", new Location(0,1), new HashSet<>());
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

        RegionImpl falseRegion = new RegionImpl();
        NodeImpl nodeFalse = new NodeImpl(falseRegion, "Region False", new Location(0,0), new HashSet<>());
        assertThrowsExactly(IllegalArgumentException.class, () -> testObject.putNode(nodeFalse));
    }

    @Test
    public void testEdges() {
        RegionImpl testObject = new RegionImpl();

        NodeImpl nodeA = new NodeImpl(testObject, "Node A", new Location(0,0), new HashSet<>());
        NodeImpl nodeB = new NodeImpl(testObject, "Node B", new Location(1,0), new HashSet<>());
        NodeImpl nodeC = new NodeImpl(testObject, "Node C", new Location(2,1), new HashSet<>());
        testObject.putNode(nodeA);
        testObject.putNode(nodeB);
        testObject.putNode(nodeC);

        EdgeImpl aa = new EdgeImpl(testObject, "Edge AA", nodeA.getLocation(), nodeA.getLocation(), 0);
        EdgeImpl ab = new EdgeImpl(testObject, "Edge AB", nodeA.getLocation(), nodeB.getLocation(), 0);
        EdgeImpl bc = new EdgeImpl(testObject, "Edge BC", nodeB.getLocation(), nodeC.getLocation(), 0);
        testObject.putEdge(aa);
        testObject.putEdge(ab);
        testObject.putEdge(bc);


        assertEquals(testObject.getEdge(nodeA.getLocation(), nodeA.getLocation()), aa);
        assertEquals(testObject.getEdge(nodeA.getLocation(), nodeB.getLocation()), ab);
        assertEquals(testObject.getEdge(nodeB.getLocation(), nodeC.getLocation()), bc);

        assertTrue(testObject.getEdges().contains(aa));
        assertTrue(testObject.getEdges().contains(ab));
        assertTrue(testObject.getEdges().contains(bc));

        RegionImpl falseRegion = new RegionImpl();
        EdgeImpl falseEdgeEdge = new EdgeImpl(falseRegion, "Kante false", nodeB.getLocation(), nodeC.getLocation(), 0);
        assertThrowsExactly(IllegalArgumentException.class, () -> testObject.putEdge(falseEdgeEdge));

        NodeImpl falseNode = new NodeImpl(falseRegion, "Region A", new Location(1,1), new HashSet<>());
        EdgeImpl falseFirstNodeEdge = new EdgeImpl(testObject, "Erste Kante false", falseNode.getLocation(), nodeC.getLocation(), 0);
        assertThrowsExactly(IllegalArgumentException.class, () -> testObject.putEdge(falseFirstNodeEdge));

        EdgeImpl falseSecondNodeEdge = new EdgeImpl(testObject, "Zweite Kante false", nodeA.getLocation(), falseNode.getLocation(), 0);
        assertThrowsExactly(IllegalArgumentException.class, () -> testObject.putEdge(falseSecondNodeEdge));

        assertNull(testObject.getEdge(new Location(69, 420), new Location(1337, 187)));
    }
}
