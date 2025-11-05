package org.Assignment2DSA;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class JGraphTTest {
    public static void main(String[] args) {
        // Create a simple undirected graph
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Add vertices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        // Add edges
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");

        // Output some properties to confirm functionality
        System.out.println("Vertices: " + graph.vertexSet());
        System.out.println("Edges: " + graph.edgeSet());
        System.out.println("Total vertices: " + graph.vertexSet().size());
        System.out.println("Total edges: " + graph.edgeSet().size());

        // Simple check
        if (graph.containsEdge("A", "B")) {
            System.out.println("JGraphT is working correctly!");
        } else {
            System.out.println("Something went wrong with JGraphT setup.");
        }
    }
}
