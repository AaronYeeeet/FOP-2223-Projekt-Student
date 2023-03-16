package projekt.delivery.routing;

import org.jetbrains.annotations.Nullable;
import projekt.base.Location;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.tudalgo.algoutils.student.Student.crash;

class NodeImpl implements Region.Node {

    protected final Set<Location> connections;
    protected final Region region;
    protected final String name;
    protected final Location location;

    /**
     * Creates a new {@link NodeImpl} instance.
     * @param region The {@link Region} this {@link NodeImpl} belongs to.
     * @param name The name of this {@link NodeImpl}.
     * @param location The {@link Location} of this {@link EdgeImpl}.
     * @param connections All {@link Location}s this {@link NeighborhoodImpl} has an {@link Region.Edge} to.
     */
    NodeImpl(
        Region region,
        String name,
        Location location,
        Set<Location> connections
    ) {
        this.region = region;
        this.name = name;
        this.location = location;
        this.connections = connections;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public Set<Location> getConnections() {
        return connections;
    }

    @Override
    public @Nullable Region.Edge getEdge(Region.Node other) {
        if (!connections.contains(other.getLocation())){
            return null;
        }else{
            return region.getEdge(this,other);
        }
    }

    @Override
    public Set<Region.Node> getAdjacentNodes() {
        Set<Region.Node> adjacentNodes = new HashSet<>();
        Collection<Region.Node> nodes = region.getNodes();
        for (Region.Node node : nodes) {
            if (node != null){
                if (connections.contains(node.getLocation())) {
                    adjacentNodes.add(node);
                }
            }
        }
        return adjacentNodes;
    }

    @Override
    public Set<Region.Edge> getAdjacentEdges() {
        Set<Region.Edge> adjacentEdges = new HashSet<>();
        for (Region.Node adjacentNode : getAdjacentNodes()) {
            Region.Edge edge = getEdge(adjacentNode);
            if (edge != null) {
                adjacentEdges.add(edge);
            }
        }
        return adjacentEdges;
    }

    @Override
    public int compareTo(Region.Node o) {
        return this.location.compareTo(o.getLocation());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (!(o instanceof NodeImpl)) {
            return false;
        }
        NodeImpl node = (NodeImpl) o;
        return (this == o) || (Objects.equals(name, node.name) &&
            Objects.equals(location, node.location) &&
            Objects.equals(connections, node.connections));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, connections);
    }

    @Override
    public String toString() {
        return "NodeImpl(name='" + name + "', location='" + location + "', connections='" + connections + "')";
    }
}
