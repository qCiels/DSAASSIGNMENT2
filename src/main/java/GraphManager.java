import Contributors.*;
import ENUMS.*;
import Exceptions.*;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.Multigraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphManager implements CommunityNetwork {
    private final Graph<Contributor, CollaborationEdge> graph = new Multigraph<>(CollaborationEdge.class);

    // ongoing projects registry (ArrayList only)
    private final List<Project> projects = new ArrayList<>();

    // ---------- contributors ----------
    @Override
    public boolean addContributor(Contributor c) {
        if (c == null)
            throw new IllegalArgumentException("null contributor");
        if (graph.containsVertex(c))
            return false;
        return graph.addVertex(c);
    }

    @Override
    public boolean removeContributor(Contributor c) {
        if (c == null)
            return false;
        return graph.removeVertex(c);
    }

    // ---------- collaborations ----------
    @Override
    public void addCollaboration(Contributor a, Contributor b, int projectId) {
        if (a == null || b == null)
            throw new IllegalArgumentException("null endpoint");

        if (!graph.containsVertex(a))
            graph.addVertex(a)
                    ;
        if (!graph.containsVertex(b))
            graph.addVertex(b);

        graph.addEdge(a, b, new CollaborationEdge(projectId));
        // optional bookkeeping on endpoints
        a.joinProject(projectId);
        b.joinProject(projectId);
    }

    @Override
    public void removeCollaboration(Contributor a, Contributor b, int projectId) {
        if (a == null || b == null) return;
        var edges = new ArrayList<>(graph.getAllEdges(a, b)); // copy to avoid CME
        for (var e : edges) {
            if (e.projectId() == projectId) {
                graph.removeEdge(e);
            }
        }
    }

    // ---------- updates ----------
    @Override
    public void updateName(Contributor c, String name) {
        if (c == null) return;
        c.setName(name);
    }

    @Override
    public void updateType(Contributor c, String typeStr) throws InvalidContributorTypeException {
        if (c == null) return;
        c.setType(typeStr);
    }

    @Override
    public void updateRegion(Contributor c, String regionStr) throws InvalidRegionException {
        if (c == null) return;
        c.setRegion(regionStr);
    }

    // ---------- projects registry ----------
    @Override
    public void addProject(Project p) {
        if (p == null) throw new IllegalArgumentException("null project");
        projects.add(p);
    }

    @Override
    public boolean removeProjectById(int id) {
        boolean removed = false;
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).id() == id) {
                projects.remove(i);
                removed = true;
                break;
            }
        }
        return removed;
    }

    @Override
    public List<Project> listProjects() {
        return Collections.unmodifiableList(projects);
    }

    // ---------- rankings (degree centrality) ----------
    @Override
    public List<Contributor> rankByDegreeDesc() {
        var list = new ArrayList<>(graph.vertexSet());
        list.sort((x, y) -> Integer.compare(graph.degreeOf(y), graph.degreeOf(x)));
        return list;
    }

    // ---------- optional helpers ----------
    public boolean isConnected() {
        return new ConnectivityInspector<>(graph).isConnected();
    }

    public List<List<Contributor>> connectedComponents() {
        var sets = new ConnectivityInspector<>(graph).connectedSets();
        var result = new ArrayList<List<Contributor>>(sets.size());
        for (var s : sets) result.add(new ArrayList<>(s));
        return result;
    }

    public Graph<Contributor, CollaborationEdge> getGraph() {
        return graph;
    }
}
