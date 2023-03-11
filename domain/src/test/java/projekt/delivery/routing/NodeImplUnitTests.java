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
        HashSet testHashset = new HashSet<>();
        testHashset.add(testRegion);

        RegionImpl otherRegion = new RegionImpl();
        otherRegion.putNode(nodeA);
        otherRegion.putNode(nodeB);
        otherRegion.putNode(nodeC);
        otherRegion.putNode(nodeD);
        otherRegion.putEdge(edgeAA);
        otherRegion.putEdge(edgeAB);
        otherRegion.putEdge(edgeBC);

        NodeImpl nodeD = new NodeImpl(otherRegion, "Node D", new Location(10, 10), testHashset);
        otherRegion.putNode(nodeD);

        Function<Integer, NodeImpl> testObjectFactory = i -> {
            int x = i % 10;
            int y = i / 7;
            if (i <= 10) {
                x = 7;
                y = 12;
            }
            return new NodeImpl(testRegion, "Node " + i, new Location(x, y), testHashset);
        };
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
        assertEquals(nodeA.getEdge(nodeB), edgeAB);
        assertNull(nodeA.getEdge(nodeC));
        assertNull(nodeA.getEdge(nodeD));
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
