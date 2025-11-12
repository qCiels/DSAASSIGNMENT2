import Contributors.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;

public final class NetworkVisualizer_Sec33_G10 {

    public static void show(org.jgrapht.Graph<Contributor_Sec33_G10, CollaborationEdge_Sec33_G10> jg) {

        System.setProperty("org.graphstream.ui", "swing"); // for Swing viewer
        Graph gs = new SingleGraph("UAEConnect");

        // --- nodes ---
        for (Contributor_Sec33_G10 v : jg.vertexSet()) {
            Node n = gs.addNode(v.getId());
            n.setAttribute("ui.label", v.getName());
        }

        // --- edges (undirected, unique id per edge) ---
        for (CollaborationEdge_Sec33_G10 e : jg.edgeSet()) {
            var src = jg.getEdgeSource(e);
            var dst = jg.getEdgeTarget(e);
            String eid = src.getId() + "-" + e.getProjectId() + "-" + dst.getId() + "-" + System.nanoTime();
            Edge ge = gs.addEdge(eid, src.getId(), dst.getId(), false);
            ge.setAttribute("ui.label", e.getProjectId());
        }

        // --- styling ---
        gs.setAttribute("ui.stylesheet",
                "node { fill-color: #66aaff; size: 14px; text-size: 14px; text-alignment: above; } " +
                        "edge { text-size: 12px; }");

        gs.display();
    }
}