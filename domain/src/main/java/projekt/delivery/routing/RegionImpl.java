package projekt.delivery.routing;

import org.jetbrains.annotations.Nullable;
import projekt.base.DistanceCalculator;
import projekt.base.EuclideanDistanceCalculator;
import projekt.base.Location;
import java.util.*;

import static org.tudalgo.algoutils.student.Student.crash;

class RegionImpl implements Region {

    private final Map<Location, NodeImpl> nodes = new HashMap<>();
    private final Map<Location, Map<Location, EdgeImpl>> edges = new HashMap<>();
    private final List<EdgeImpl> allEdges = new ArrayList<>();
    private final DistanceCalculator distanceCalculator;

    /**
     * Creates a new, empty {@link RegionImpl} instance using a {@link EuclideanDistanceCalculator}.
     */
    public RegionImpl() {
        this(new EuclideanDistanceCalculator());
    }

    /**
     * Creates a new, empty {@link RegionImpl} instance using the given {@link DistanceCalculator}.
     */
    public RegionImpl(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public @Nullable Node getNode(Location location) {
        return nodes.get(location);
    }

    @Override
    public @Nullable Edge getEdge(Location locationA, Location locationB) {
        Location smallerLocation = locationA.compareTo(locationB) < 0 ? locationA : locationB;
        Location largerLocation = locationA.compareTo(locationB) > 0 ? locationA : locationB;
        Map<Location, EdgeImpl> innerMap = edges.get(smallerLocation);
        if (innerMap != null) {
            return innerMap.get(largerLocation);
        }
        return null;
    }

    @Override
    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

    @Override
    public Collection<Edge> getEdges() {
        List<Edge> edge = new ArrayList<>();
        for (Map<Location, EdgeImpl> map : edges.values()) {
            edge.addAll(map.values());
        }
        return Collections.unmodifiableCollection(edge);
    }

    @Override
    public DistanceCalculator getDistanceCalculator() {
        return distanceCalculator;
    }

    /**
     * Adds the given {@link NodeImpl} to this {@link RegionImpl}.
     * @param node the {@link NodeImpl} to add.
     */
    void putNode(NodeImpl node) {
        if (!this.equals(node.getRegion())){
            throw new IllegalArgumentException("Node " + node.toString() + " has incorrect region");
        }else{
            nodes.put(node.getLocation(),node);
        }
    }

    /**
     * Adds the given {@link EdgeImpl} to this {@link RegionImpl}.
     * @param edge the {@link EdgeImpl} to add.
     */
    void putEdge(EdgeImpl edge) {

        //If edge.getNodeA() or edge.getNodeB() returns null
        if (edge.getNodeA() == null || edge.getNodeB() == null) {
            throw new IllegalArgumentException("Node"
                + (edge.getNodeA() == null ? "A "+ edge.getLocationA().toString(): "B "+ edge.getLocationB().toString())
                + " is not part of the region");


            //If the passed edge, or one of its vertices, is not in this region (this).
        }else if (!this.equals(edge.getRegion()) || !getNodes().contains(edge.getNodeA()) || !getNodes().contains(edge.getNodeB())) {
            throw new IllegalArgumentException("Edge " +edge.toString()+ " has incorrect region");

        }else{
            //Add edge to the list
            allEdges.add(edge);

            //Add edge to the map
            Location locationA = edge.getNodeA().getLocation();
            Location locationB = edge.getNodeB().getLocation();
            Location smallerLocation = locationA.compareTo(locationB) < 0 ? locationA : locationB;
            Location largerLocation = locationA.compareTo(locationB) > 0 ? locationA : locationB;

            /**
             Map<Location,EdgeImpl> innerMap = new HashMap<>();
             innerMap.put(largerLocation,edge);
             edges.putIfAbsent(smallerLocation,innerMap);
             */
            edges.computeIfAbsent(smallerLocation, k -> new HashMap<>()).put(largerLocation, edge);

        }
    }

    @Override
    public boolean equals(Object o) {

        if (o == null){
            return false;
        }else if (o == this) {
            return true;
        }else if(!(o instanceof RegionImpl)) {
            return false;
        }

        RegionImpl otherRegion = (RegionImpl) o;
        return Objects.equals(this.nodes, otherRegion.nodes) && Objects.equals(this.edges, otherRegion.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes,edges);
    }
}
