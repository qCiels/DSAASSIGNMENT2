import Contributors.*;
import Exceptions.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.Multigraph;
import java.lang.reflect.Type;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;




public class GraphManager_Sec33_G10 implements CommunityNetwork_Sec33_G10 {
    private final Graph<Contributor_Sec33_G10, CollaborationEdge_Sec33_G10> graph = new Multigraph<>(CollaborationEdge_Sec33_G10.class);

    // ongoing projects registry (ArrayList only)
    private final List<Project_Sec33_G10> projectSec33G10s = new ArrayList<>();

    // ---------- contributors ----------
    @Override
    public void addContributor(Contributor_Sec33_G10 contributor) throws InvalidContributorTypeException {
        if (contributor != null && !graph.containsVertex(contributor)) {
            graph.addVertex(contributor);
            System.out.println("added contributor successfully");
        }
        else {
            throw new InvalidContributorTypeException("INVALID CONTRIBUTOR TYPE, TRY AGAIN...");
        }
    }

    @Override
    public void removeContributor(Contributor_Sec33_G10 contributor) {
        if (contributor != null) {
         graph.removeVertex(contributor);
        }

    }

    // ---------- collaborations ----------
    @Override
    public void addCollaboration(Contributor_Sec33_G10 a, Contributor_Sec33_G10 b, String projectId) {
        if (a != null && b != null && graph.containsVertex(a) && graph.containsVertex(b)) {
            graph.addEdge(a, b, new CollaborationEdge_Sec33_G10(projectId));
        }

    }

    public void removeCollaboration(String contributorId1, String contributorId2, String projectId) {
        Contributor_Sec33_G10 c1 = getContributorById(contributorId1);
        Contributor_Sec33_G10 c2 = getContributorById(contributorId2);
        if (c1 != null && c2 != null) {
            CollaborationEdge_Sec33_G10 toRemove = null;
            for (CollaborationEdge_Sec33_G10 edge : graph.getAllEdges(c1, c2)) {
                if (edge.getProjectId().equals(projectId)) {
                    toRemove = edge;
                    break;
                }
            }
            if (toRemove != null) {
                graph.removeEdge(toRemove);
            }
        }
    }

    // ---------- updates ----------
    @Override
    public void updateName(Contributor_Sec33_G10 c, String name) {
        if (c == null)
            return;
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

    @Override
    public void addProject(Project_Sec33_G10 p) {
        if (p == null)
            throw new IllegalArgumentException("null project");
        projectSec33G10s.add(p);
    }


    public Contributor_Sec33_G10 getContributorById(String id) {
        for (Contributor_Sec33_G10 c : graph.vertexSet()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
    public void loadProjectsFromFile(String filePath) {
        try (java.io.FileReader reader = new java.io.FileReader(filePath)) {
            java.lang.reflect.Type projectListType =
                    new com.google.gson.reflect.TypeToken<java.util.ArrayList<Project_Sec33_G10>>() {}.getType();

            java.util.List<Project_Sec33_G10> loaded =
                    new com.google.gson.Gson().fromJson(reader, projectListType);
            if (loaded == null) return;

            // store them if you keep a registry
            projectSec33G10s.addAll(loaded);

            for (Project_Sec33_G10 project : loaded) {
                java.util.List<String> ids = project.contributors();
                for (int i = 0; i < ids.size() - 1; i++) {
                    for (int j = i + 1; j < ids.size(); j++) {
                        Contributor_Sec33_G10 c1 = getContributorById(ids.get(i));
                        Contributor_Sec33_G10 c2 = getContributorById(ids.get(j));
                        if (c1 != null && c2 != null) {
                            addCollaboration(c1, c2, project.id());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading projects: " + e.getMessage());
        }
    }

    public void loadContributorsFromFile(String filePath) {
        try (java.io.FileReader reader = new java.io.FileReader(filePath)) {
            java.lang.reflect.Type listType =
                    new com.google.gson.reflect.TypeToken<java.util.ArrayList<ContributorDTO>>() {}.getType();

            java.util.List<ContributorDTO> dtos =
                    new com.google.gson.Gson().fromJson(reader, listType);
            if (dtos == null) return;

            for (ContributorDTO dto : dtos) {
                addContributor(toModel(dto));
            }
        } catch (Exception e) {
            System.err.println("Error loading contributors: " + e.getMessage());
        }
    }
    private Contributor_Sec33_G10 toModel(ContributorDTO d)
            throws InvalidRegionException, InvalidContributorTypeException {
        String t = d.type() == null ? "" : d.type().trim().toUpperCase();
        return switch (t) {
            case "INDIVIDUAL"  -> new IndividualContributor_Sec33_G10(d.name(), d.region(), d.id());
            case "NGO"         -> new NGOContributor_Sec33_G10(d.name(), d.region(), d.id());
            case "SCHOOL"      -> new SchoolContributor_Sec33_G10(d.name(), d.region(), d.id());
            case "INSTITUTION" -> new InstitutionContributor_Sec33_G10(d.name(), d.region(), d.id());
            default -> throw new InvalidContributorTypeException("Unknown type: " + d.type());
        };
    }
    public org.jgrapht.Graph<Contributor_Sec33_G10, CollaborationEdge_Sec33_G10> getGraph() {
        return graph;
    }
    // ---------- ranking ----------

    public List<Contributor_Sec33_G10> rankByDegreeDesc() {
        List<Contributor_Sec33_G10> list = new ArrayList<>(graph.vertexSet());
        list.sort((a, b) -> Integer.compare(graph.degreeOf(b), graph.degreeOf(a)));
        return list;
    }
    public List<Contributor_Sec33_G10> findConnectionPath(String id1, String id2) {
        var src = getContributorById(id1);
        var dst = getContributorById(id2);
        if (src == null || dst == null) return List.of();

        var bfs = new org.jgrapht.alg.shortestpath.BFSShortestPath<>(graph);
        var path = bfs.getPath(src, dst);
        return (path == null) ? List.of() : path.getVertexList();
    }

    public void showNetworkStats() {
        int vertexCount = graph.vertexSet().size();
        int edgeCount = graph.edgeSet().size();
        double avgDegree = vertexCount > 0 ? (2.0 * edgeCount) / vertexCount : 0;
        double density = vertexCount > 1 ? (2.0 * edgeCount) / (vertexCount * (vertexCount - 1)) : 0;

        System.out.println("\n--- Network Statistics ---");
        System.out.println("Total Contributors: " + vertexCount);
        System.out.println("Total Collaborations: " + edgeCount);
        System.out.printf("Average Degree: %.2f%n", avgDegree);
        System.out.printf("Network Density: %.3f%n", density);
    }

}

