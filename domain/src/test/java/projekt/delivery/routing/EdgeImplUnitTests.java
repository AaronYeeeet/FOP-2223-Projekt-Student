package projekt.delivery.routing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import projekt.ComparableUnitTests;
import projekt.ObjectUnitTests;
import projekt.base.Location;

import java.util.HashSet;

import static org.tudalgo.algoutils.student.Student.crash;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeImplUnitTests {

    private static ComparableUnitTests<Region.Edge> comparableUnitTests;
    private static ObjectUnitTests<Region.Edge> objectUnitTests;
    private static NodeImpl nodeA;
    private static NodeImpl nodeB;
    private static NodeImpl nodeC;

    private static EdgeImpl edgeAA;
    private static EdgeImpl edgeAB;
    private static EdgeImpl edgeBC;

    @BeforeAll
    public static void initialize() {
        RegionImpl testRegion = new RegionImpl();
        HashSet testHashset = new HashSet<>();
        testHashset.add(testRegion);

        nodeA = new NodeImpl(testRegion, "Node A", new Location(0, 0), testHashset);
        nodeB = new NodeImpl(testRegion, "Node B", new Location(0, 1), testHashset);
        nodeC = new NodeImpl(testRegion, "Node C", new Location(1, 1), testHashset);

        edgeAA = new EdgeImpl(testRegion, "Edge AA", nodeA.getLocation(), nodeA.getLocation(), 0);
        edgeAB = new EdgeImpl(testRegion, "Edge AA", nodeA.getLocation(), nodeB.getLocation(), 5);
        edgeBC = new EdgeImpl(testRegion, "Edge AA", nodeB.getLocation(), nodeC.getLocation(), 5);

        testRegion.putNode(nodeA);
        testRegion.putNode(nodeB);
        testRegion.putNode(nodeC);
        testRegion.putEdge(edgeAA);
        testRegion.putEdge(edgeAB);
        testRegion.putEdge(edgeBC);
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
    public void testGetNode() {
        assertEquals(edgeAA.getNodeA(), nodeA);
        assertNull(edgeAA.getNodeB());
        assertEquals(edgeAB.getNodeA(), nodeA);
        assertEquals(edgeAB.getNodeB(), nodeB);
        assertNull(edgeBC.getNodeA());
        assertEquals(edgeBC.getNodeB(), nodeB);
    }
}
