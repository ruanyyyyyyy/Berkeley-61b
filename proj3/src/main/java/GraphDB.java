import example.CSCourseDB;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;
import edu.princeton.cs.algs4.Bag;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private Map<String, Node> nodes = new HashMap<>();
    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * a node
     */
    static class Node {
        String id;
        Double lon;
        Double lat;
        boolean islocation = false;
        String locationName;
        Set<Node> adjacents;

        Node(String _id, String _lon, String _lat) {
            this.id = _id;
            this.lon = Double.parseDouble(_lon);
            this.lat = Double.parseDouble(_lat);
            this.adjacents = new HashSet<>();
        }
    }

    /**
     * Add a node to the database.
     *
     * @param n node
     */
    void addNode(Node n) {
        this.nodes.put(n.id, n);
    }

    /**
     * an edge
     */
    static class Edge {
        ArrayList<String> waypoints;
        String maxspeed;
        boolean valid;

        Edge() {
            waypoints = new ArrayList<>();
            valid = false;
        }
    }

    /**
     * Add an edge to the database.
     *
     * @param e edge
     */
    void addEdge(Edge e) {
        /* Assume all the nodes in way appears in nodes too */
        for (int i = 1; i < e.waypoints.size(); i += 1) {
            if (nodes.containsKey(e.waypoints.get(i)) && nodes.containsKey(e.waypoints.get(i-1))) {
                Node nodeCur = nodes.get(e.waypoints.get(i));
                Node nodePre = nodes.get(e.waypoints.get(i - 1));
                nodeCur.adjacents.add(nodePre);
                nodePre.adjacents.add(nodeCur);
            }
        }
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        Map<String, Node> temp = new HashMap<>(nodes);
        for (Map.Entry<String, Node> entry: temp.entrySet()) {
            if (entry.getValue().adjacents.size() == 0) {
                nodes.remove(entry.getKey());
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        ArrayList<Long> verts = new ArrayList<>();
        for (Map.Entry<String, Node> entry: nodes.entrySet()) {
            verts.add(Long.parseLong(entry.getKey()));
        }
        return verts;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        ArrayList<Long> verts = new ArrayList<>();
        String id= String.valueOf(v);
        Node n = nodes.get(id);
        for (Node ids: n.adjacents) {
            verts.add(Long.parseLong(ids.id));
        }
        return verts;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double min = 1000000.0;
        Long target = Long.valueOf(0);
        for (Map.Entry<String, Node> entry: nodes.entrySet()) {
            Node n = entry.getValue();
            double diff = distance(lon, lat, n.lon, n.lat);
            if (diff < min) {
                min = diff;
                target = Long.parseLong(entry.getKey());
            }
        }
        return target;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        Node n = nodes.get(String.valueOf(v));
        return n.lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        Node n = nodes.get(String.valueOf(v));
        return n.lat;
    }
}
