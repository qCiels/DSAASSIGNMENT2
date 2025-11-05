import Contributors.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;

public class UAEConnect {
    public static void main(String[] args) throws Exception {
        GraphManager gm = new GraphManager();

        // 1) Add contributors (nodes)
        Contributor ali    = new IndividualContributor("Ali Al Mansoori", "ABUDHABI");
        Contributor green  = new NGOContributor("Green Foundation", "DUBAI");
        Contributor alma   = new SchoolContributor("Al Maarifa School", "SHARJAH");
        Contributor muni   = new InstitutionContributor("Abu Dhabi Municipality", "ABUDHABI");

        gm.addContributor(ali);
        gm.addContributor(green);
        gm.addContributor(alma);
        gm.addContributor(muni);

        // 2) Projects list
        gm.addProject(new Project(101, "Beach Cleanup", "Environmental"));
        gm.addProject(new Project(102, "STEM Workshop", "Educational"));
        gm.addProject(new Project(103, "Park Revamp", "Community"));

        // 2) Create collaborations (bidirectional edges annotated by projectId)
        gm.addCollaboration(ali,   green, 101);
        gm.addCollaboration(alma,  green, 102);
        gm.addCollaboration(muni,  green, 103);
        gm.addCollaboration(ali,   alma,  102); // parallel edge allowed (Multigraph)

        // 3) Update contributor info
        gm.updateName(ali, "Ali A. Mansoori");

        // 4) Remove a specific collaboration (not the contributors)
        gm.removeCollaboration(ali, alma, 102);

        // 5) Degree-centrality ranking
        List<Contributor> ranked = gm.rankByDegreeDesc();
        System.out.println("Engagement ranking (by degree):");
        int i = 1;
        for (Contributor c : ranked) {
            System.out.println((i++) + ". " + c.getName() + " — degree " + gm.getGraph().degreeOf(c));
        }

        // Connectivity snapshot
        System.out.println("Connected? " + gm.isConnected());
        System.out.println("Components: " + gm.connectedComponents().size());

        // 6) Dynamic monitoring is inherent: the ranking above reflects all adds/updates/removals

        // --- GraphStream visualization ---
        // Uses contributor names as node IDs. Requires gs-core; Swing UI.
        System.setProperty("org.graphstream.ui", "swing");

        Graph visual = new SingleGraph("UAEConnect");
        // basic labels
        visual.setAttribute("ui.stylesheet",
                "node { text-size: 16px; padding: 4px; } edge { text-size: 12px; }");

        // add nodes
        for (Contributor c : gm.getGraph().vertexSet()) {
            String id = c.getName();
            if (visual.getNode(id) == null) {
                Node n = visual.addNode(id);
                n.setAttribute("ui.label", c.getName());
            }
        }

        // add edges (unique ID per source-target-project)
        gm.getGraph().edgeSet().forEach(e -> {
            var src = gm.getGraph().getEdgeSource(e);
            var dst = gm.getGraph().getEdgeTarget(e);
            String edgeId = src.getName() + "-" + dst.getName() + "-" + e.projectId();
            if (visual.getEdge(edgeId) == null) {
                var ve = visual.addEdge(edgeId, src.getName(), dst.getName());
                ve.setAttribute("ui.label", "P#" + e.projectId());
            }
        });

        visual.display();
    }
}
