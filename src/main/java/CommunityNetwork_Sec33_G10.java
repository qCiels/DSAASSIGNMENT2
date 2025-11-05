import Contributors.*;

import java.util.List;

public interface CommunityNetwork_Sec33_G10 {
    // 1) contributors
    boolean addContributor(Contributor_Sec33_G10 c);
    boolean removeContributor(Contributor_Sec33_G10 c);

    // 2) collaborations
    void addCollaboration(Contributor_Sec33_G10 a, Contributor_Sec33_G10 b, int projectId);
    void removeCollaboration(Contributor_Sec33_G10 a, Contributor_Sec33_G10 b, int projectId);

    // 3) updates
    void updateName(Contributor_Sec33_G10 c, String name);
    void updateType(Contributor_Sec33_G10 c, String typeStr) throws Exceptions.InvalidContributorTypeException;
    void updateRegion(Contributor_Sec33_G10 c, String regionStr) throws Exceptions.InvalidRegionException;


    void addProject(Project_Sec33_G10 p);
    boolean removeProjectById(int id);
    List<Project_Sec33_G10> listProjects();

    // 5) rankings
    List<Contributor_Sec33_G10> rankByDegreeDesc();
}
