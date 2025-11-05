import Contributors.*;
import Exceptions.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphManager_Sec33_G10 implements CommunityNetwork_Sec33_G10 {
    private final Graph<Contributor_Sec33_G10, CollaborationEdge_Sec33_G10> graph = new Multigraph<>(CollaborationEdge_Sec33_G10.class);

    // ongoing projects registry (ArrayList only)
    private final List<Project_Sec33_G10> projectSec33G10s = new ArrayList<>();

    // ---------- contributors ----------
    @Override
    public boolean addContributor(Contributor_Sec33_G10 c) {
        if (c == null)
            throw new IllegalArgumentException("null contributor");
        if (graph.containsVertex(c))
            return false;
        return graph.addVertex(c);
    }

    @Override
    public boolean removeContributor(Contributor_Sec33_G10 c) {
        if (c == null)
            return false;
        return graph.removeVertex(c);
    }

    // ---------- collaborations ----------
    @Override
    public void addCollaboration(Contributor_Sec33_G10 a, Contributor_Sec33_G10 b, int projectId) {
        if (a == null || b == null)
            throw new IllegalArgumentException("null endpoint");

        if (!graph.containsVertex(a))
            graph.addVertex(a);

        if (!graph.containsVertex(b))
            graph.addVertex(b);

        graph.addEdge(a, b, new CollaborationEdge_Sec33_G10(projectId));
        // optional bookkeeping on endpoints
        a.joinProject(projectId);
        b.joinProject(projectId);
    }

    @Override
    public void removeCollaboration(Contributor_Sec33_G10 a, Contributor_Sec33_G10 b, int projectId) {
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
    public void updateName(Contributor_Sec33_G10 c, String name) {
        if (c == null) return;
        c.setName(name);
    }

    @Override
    public void updateType(Contributor_Sec33_G10 c, String typeStr) throws InvalidContributorTypeException {
        if (c == null) return;
        c.setType(typeStr);
    }

    @Override
    public void updateRegion(Contributor_Sec33_G10 c, String regionStr) throws InvalidRegionException {
        if (c == null) return;
        c.setRegion(regionStr);
    }

    // ---------- projects registry ----------
    @Override
    public void addProject(Project_Sec33_G10 p) {
        if (p == null)
            throw new IllegalArgumentException("null project");
        projectSec33G10s.add(p);
    }

    @Override
    public boolean removeProjectById(int id) {
        boolean removed = false;
        for (int i = 0; i < projectSec33G10s.size(); i++) {
            if (projectSec33G10s.get(i).id() == id) {
                projectSec33G10s.remove(i);
                removed = true;
                break;
            }
        }
        return removed;
    }

    @Override
    public List<Project_Sec33_G10> listProjects() {
        return Collections.unmodifiableList(projectSec33G10s);
    }

    // ---------- rankings (degree centrality) ----------
    @Override
    public List<Contributor_Sec33_G10> rankByDegreeDesc() {
        var list = new ArrayList<>(graph.vertexSet());
        list.sort((x, y) -> Integer.compare(graph.degreeOf(y), graph.degreeOf(x)));
        return list;
    }


    public Graph<Contributor_Sec33_G10, CollaborationEdge_Sec33_G10> getGraph() {
        return graph;
    }

    public void applyCsvFile(String path) throws java.io.IOException, Exceptions.InvalidContributorTypeException, Exceptions.InvalidRegionException {
        java.util.List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(path));
        for (String line : lines) {
            if (line == null || line.isBlank() || line.startsWith("#")) continue;
            String[] t = line.split(",", -1);
            String op = t[0].trim().toUpperCase();

            switch (op) {
                case "ADD_CONTRIB" -> { // ADD_CONTRIB,type,name,region
                    String type = t[1].trim();
                    String name = t[2].trim();
                    String region = t[3].trim();
                    Contributor_Sec33_G10 c = switch (type.toUpperCase()) {
                        case "INDIVIDUAL"  -> new IndividualContributor_Sec33_G10(name, region);
                        case "NGO"         -> new NGOContributor_Sec33_G10(name, region);
                        case "SCHOOL"      -> new SchoolContributor_Sec33_G10(name, region);
                        case "INSTITUTION" -> new InstitutionContributor_Sec33_G10(name, region);
                        default -> throw new IllegalArgumentException("Unknown type: " + type);
                    };
                    addContributor(c);
                }
                case "ADD_COLLAB" -> { // ADD_COLLAB,nameA,nameB,projectId
                    var a = byName(t[1]); var b = byName(t[2]);
                    addCollaboration(a, b, Integer.parseInt(t[3].trim()));
                }
                case "UPDATE_NAME" -> updateName(byName(t[1]), t[2]);
                case "UPDATE_TYPE" -> updateType(byName(t[1]), t[2]);
                case "UPDATE_REGION" -> updateRegion(byName(t[1]), t[2]);
                case "REMOVE_CONTRIB" -> removeContributor(byName(t[1]));
                case "REMOVE_COLLAB" -> removeCollaboration(byName(t[1]), byName(t[2]), Integer.parseInt(t[3].trim()));
                case "ADD_PROJECT" -> addProject(new Project_Sec33_G10(Integer.parseInt(t[1].trim()), t[2].trim(), t.length>3?t[3].trim():"Uncategorized"));
                case "REMOVE_PROJECT" -> removeProjectById(Integer.parseInt(t[1].trim()));
                default -> throw new IllegalArgumentException("Unknown op: " + op);
            }
        }
    }
    // helper in GraphManager (linear scan only)
    private Contributor_Sec33_G10 byName(String name) {
        for (Contributor_Sec33_G10 v : graph.vertexSet())
            if (v.getName().equalsIgnoreCase(name.trim())) return v;
        throw new IllegalArgumentException("Contributor not found: " + name);
    }

}
