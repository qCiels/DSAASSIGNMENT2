import Contributors.*;
import Exceptions.*;

public class UAEConnect_Sec33_G10 {
    public static void main(String[] args) {
        GraphManager_Sec33_G10 gm = new GraphManager_Sec33_G10();

        // 1) Load from resources
        String contribPath = UAEConnect_Sec33_G10.class.getResource("/contributors.json").getPath();
        String projectsPath = UAEConnect_Sec33_G10.class.getResource("/projects.json").getPath();
        gm.loadContributorsFromFile(contribPath);
        gm.loadProjectsFromFile(projectsPath);

        // 2) Add a new contributor
        try {
            Contributor_Sec33_G10 cNew = new IndividualContributor_Sec33_G10("Layla Al Nuaimi", "Abu_Dhabi", "C999");
            gm.addContributor(cNew);
        } catch (InvalidRegionException | InvalidContributorTypeException e) {
            System.err.println(e.getMessage());
        }

        // 3) Update an existing contributor
        Contributor_Sec33_G10 c1 = gm.getContributorById("C1");
        if (c1 != null) {
            gm.updateName(c1, "Ali A. Mansoori");
            try {
                gm.updateRegion(c1, "Dubai");
                gm.updateType(c1, "Individual");
            } catch (InvalidRegionException | InvalidContributorTypeException e) {
                System.err.println(e.getMessage());
            }
        }

        // 4) Create a new collaboration
        Contributor_Sec33_G10 a = gm.getContributorById("C1");
        Contributor_Sec33_G10 b = gm.getContributorById("C2");
        if (a != null && b != null) {
            gm.addCollaboration(a, b, "P999");
        }

        // 5) Remove a specific collaboration
        gm.removeCollaboration("C1", "C2", "P999");

        // 6) Remove a contributor
        Contributor_Sec33_G10 toRemove = gm.getContributorById("C999");
        if (toRemove != null) {
            gm.removeContributor(toRemove);
        }

        //Ranking
        System.out.println("\n--- Contributor Ranking by Degree ---");
        int rank = 1;
        for (var c : gm.rankByDegreeDesc()) {
            System.out.printf("%d) %s - Degree: %d%n", rank++, c.getName(), gm.getGraph().degreeOf(c));
        }

        // --- test 1: connected pair (should return a path)
        System.out.println("\nPath C2 -> C4:");
        var path1 = gm.findConnectionPath("C2", "C4");
        System.out.println(path1.stream().map(Contributor_Sec33_G10::getName).toList());

// --- test 2: same node (path of length 1)
        System.out.println("\nPath C1 -> C1:");
        var path2 = gm.findConnectionPath("C1", "C1");
        System.out.println(path2.stream().map(Contributor_Sec33_G10::getName).toList());

// --- test 3: nonexistent ID (should be empty)
        System.out.println("\nPath C2 -> C9999 (missing):");
        var path3 = gm.findConnectionPath("C2", "C9999");
        System.out.println(path3);

        System.out.println("Demo complete.");
        gm.showNetworkStats();
        NetworkVisualizer_Sec33_G10.show(gm.getGraph());

    }
}
