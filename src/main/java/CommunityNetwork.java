import Contributors.*;

import java.util.List;

public interface CommunityNetwork {
    // 1) contributors
    boolean addContributor(Contributor c);
    boolean removeContributor(Contributor c);

    // 2) collaborations
    void addCollaboration(Contributor a, Contributor b, int projectId);
    void removeCollaboration(Contributor a, Contributor b, int projectId);

    // 3) updates
    void updateName(Contributor c, String name);
    void updateType(Contributor c, String typeStr) throws Exceptions.InvalidContributorTypeException;
    void updateRegion(Contributor c, String regionStr) throws Exceptions.InvalidRegionException;


    void addProject(Project p);
    boolean removeProjectById(int id);
    List<Project> listProjects();

    // 5) rankings
    List<Contributor> rankByDegreeDesc();
}
