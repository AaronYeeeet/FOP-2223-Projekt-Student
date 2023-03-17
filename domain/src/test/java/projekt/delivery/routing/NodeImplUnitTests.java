package projekt.delivery.routing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import projekt.ComparableUnitTests;
import projekt.ObjectUnitTests;
import projekt.base.Location;

import java.util.HashSet;
import java.util.function.Function;

import static org.tudalgo.algoutils.student.Student.crash;
import static org.junit.jupiter.api.Assertions.*;

public class NodeImplUnitTests {

    private static ComparableUnitTests<NodeImpl> comparableUnitTests;
    private static ObjectUnitTests<NodeImpl> objectUnitTests;
    private static NodeImpl nodeA;
    private static NodeImpl nodeB;
    private static NodeImpl nodeC;
    private static NodeImpl nodeD;

    private static EdgeImpl edgeAA;
    private static EdgeImpl edgeAB;
    private static EdgeImpl edgeBC;

    @BeforeAll
    public static void initialize() {

        RegionImpl testRegion = new RegionImpl();

        RegionImpl otherRegion = new RegionImpl();
        nodeA = new NodeImpl(otherRegion, "Node A", new Location(0,0), new HashSet<>());
        nodeB = new NodeImpl(otherRegion, "Node B", new Location(1,0), new HashSet<>());
        nodeC = new NodeImpl(otherRegion, "Node C", new Location(2,1), new HashSet<>());
        otherRegion.putNode(nodeA);
        otherRegion.putNode(nodeB);
        otherRegion.putNode(nodeC);

        edgeAA = new EdgeImpl(otherRegion, "Edge AA", nodeA.getLocation(), nodeA.getLocation(), 0);
        edgeAB = new EdgeImpl(otherRegion, "Edge AB", nodeA.getLocation(), nodeB.getLocation(), 0);
        edgeBC = new EdgeImpl(otherRegion, "Edge BC", nodeB.getLocation(), nodeC.getLocation(), 0);
        otherRegion.putEdge(edgeAA);
        otherRegion.putEdge(edgeAB);
        otherRegion.putEdge(edgeBC);

        nodeD = new NodeImpl(otherRegion, "Node D", new Location(10, 10), null);
        otherRegion.putNode(nodeD);



        Function<Integer, NodeImpl> testObjectFactory = i -> {
            return new NodeImpl(testRegion, "Node " + i, new Location(i, i), null);
        };

        comparableUnitTests = new ComparableUnitTests<>(testObjectFactory);
        objectUnitTests = new ObjectUnitTests<>(testObjectFactory,NodeImpl::toString);

        comparableUnitTests.initialize(10);
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
    public void testToString() {
        objectUnitTests.testToString();
    }

    @Test
    public void testBiggerThen() {
        comparableUnitTests.testBiggerThen();
    }

    @Test
    public void testAsBigAs() {
        comparableUnitTests.testAsBigAs();
    }

    @Test
    public void testLessThen() {
        comparableUnitTests.testLessThen();
    }

    @Test
    public void testGetEdge() {
        assertEquals(nodeA.getEdge(nodeA), edgeAA);
        /*assertEquals(nodeA.getEdge(nodeB), edgeAB);
        assertNull(nodeA.getEdge(nodeC));
        assertNull(nodeA.getEdge(nodeD));*/
    }

    @Test
    public void testAdjacentNodes() {
        HashSet a = new HashSet<>();
        a.add(nodeA);
        a.add(nodeB);
        assertEquals(nodeA.getAdjacentNodes(), a);

        HashSet b = new HashSet<>();
        b.add(nodeC);
        b.add(nodeA);
        assertEquals(nodeB.getAdjacentNodes(), b);

        HashSet c = new HashSet<>();
        c.add(nodeB);
        assertEquals(nodeC.getAdjacentNodes(), c);

        assertNull(nodeD.getAdjacentNodes());
    }

    @Test
    public void testAdjacentEdges() {
        HashSet a = new HashSet<>();
        a.add(edgeAA);
        a.add(edgeAB);
        assertEquals(nodeA.getAdjacentEdges(), a);

        HashSet b = new HashSet<>();
        b.add(edgeBC);
        b.add(edgeAB);
        assertEquals(nodeB.getAdjacentEdges(), b);

        HashSet c = new HashSet<>();
        c.add(edgeBC);
        assertEquals(nodeC.getAdjacentEdges(), c);

        assertNull(nodeD.getAdjacentEdges());
    }
}
