import Contributors.*;

import java.nio.file.*;
import java.util.List;

// VIS imports
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public final class UAEConnect_Sec33_G10 {
    public static void main(String[] args) throws Exception {
        GraphManager_Sec33_G10 gm = new GraphManager_Sec33_G10();

        // -----------------------------
        // (6) FILE-BASED FEED (dynamic)
        // -----------------------------
        String txtPath = "updates.txt";
        if (Files.exists(Paths.get(txtPath))) {
            gm.applyCsvFile(txtPath);                  // applies adds/updates/removals from file
            System.out.println("[Feed] Loaded: " + txtPath);
        } else {
            System.out.println("[Feed] No updates.txt present");
        }

        // -----------------------------------------
        // (1) ADD CONTRIBUTORS (unique + metadata)
        // -----------------------------------------
        Contributor_Sec33_G10 blue = new NGOContributor_Sec33_G10("Blue Planet Initiative", "DUBAI");
        Contributor_Sec33_G10 horizon = new SchoolContributor_Sec33_G10("Horizon International School", "SHARJAH");
        gm.addContributor(blue);
        gm.addContributor(horizon);

        // ---------------------------------------------
        // (2) PROJECT LIST + COLLABORATIONS (annotated)
        // ---------------------------------------------
        gm.addProject(new Project_Sec33_G10(101, "Beach Cleanup", "Environmental"));
        gm.addProject(new Project_Sec33_G10(102, "STEM Workshop", "Educational"));
        gm.addProject(new Project_Sec33_G10(103, "Community Sports Day", "Youth"));
        gm.addCollaboration(horizon, blue, 102);    // another annotated edge
        System.out.println("[Projects] " + gm.listProjects().size() + " active projects");

        // ------------------------------------------------------
        // (3) UPDATE CONTRIBUTOR INFO (name, type, region, proj)
        // ------------------------------------------------------

        gm.updateRegion(blue, "ABU_DHABI");

        // ----------------------------------------------------
        // (4) REMOVE SPECIFIC COLLAB OR ENTIRE CONTRIBUTOR
        // ----------------------------------------------------

        // --------------------------------------------
        // (5) DEGREE-CENTRALITY RANKING (by # of edges)
        // --------------------------------------------
        System.out.println("\n[Ranking] Degree centrality:");
        List<Contributor_Sec33_G10> ranked = gm.rankByDegreeDesc();
        for (int i = 0; i < ranked.size(); i++) {
            var c = ranked.get(i);
            System.out.println((i + 1) + ". " + c.getName() +
                    " — degree " + gm.getGraph().degreeOf(c));
        }

        // ------------------------------
        // VIS: init window + initial sync
        // ------------------------------
        System.setProperty("org.graphstream.ui", "swing");
        Graph vis = new SingleGraph("UAEConnect");
        vis.setAttribute("ui.stylesheet", "node { text-size: 14px; } edge { text-size: 12px; }");
        vis.display();
        refreshVisualization(gm, vis);
            // VIS: refresh after any mutation/command
            refreshVisualization(gm, vis);

    }



    // VIS: keep GraphStream view in sync with JGraphT graph
    private static void refreshVisualization(GraphManager_Sec33_G10 gm, Graph vis) {
        // add missing nodes
        for (Contributor_Sec33_G10 c : gm.getGraph().vertexSet()) {
            if (vis.getNode(c.getName()) == null) {
                Node n = vis.addNode(c.getName());
                n.setAttribute("ui.label", c.getName());
            }
        }
        // remove stale nodes
        java.util.List<String> toRemoveNodes = new java.util.ArrayList<>();
        for (Node n : vis) {
            boolean exists = false;
            for (Contributor_Sec33_G10 c : gm.getGraph().vertexSet()) {
                if (c.getName().equals(n.getId())) { exists = true; break; }
            }
            if (!exists) toRemoveNodes.add(n.getId());
        }
        toRemoveNodes.forEach(vis::removeNode);

        // sync edges (unique id per src-dst-project)
        java.util.Set<String> wanted = new java.util.HashSet<>();
        for (CollaborationEdge_Sec33_G10 e : gm.getGraph().edgeSet()) {
            var s = gm.getGraph().getEdgeSource(e).getName();
            var t = gm.getGraph().getEdgeTarget(e).getName();
            String id = s + "-" + t + "-P" + e.projectId();
            wanted.add(id);
            if (vis.getEdge(id) == null) {
                var ve = vis.addEdge(id, s, t);
                ve.setAttribute("ui.label", "P#" + e.projectId());
            }
        }
        // remove stale edges
        java.util.List<String> stale = new java.util.ArrayList<>();
        for (var e : vis.getEachEdge()) if (!wanted.contains(e.getId())) stale.add(e.getId());
        stale.forEach(vis::removeEdge);
    }
}
