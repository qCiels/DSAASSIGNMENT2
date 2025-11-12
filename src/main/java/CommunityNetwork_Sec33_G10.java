import Contributors.*;
import Exceptions.InvalidContributorTypeException;

import java.util.List;

public interface CommunityNetwork_Sec33_G10 {
    // 1) contributors
    void addContributor(Contributor_Sec33_G10 c) throws InvalidContributorTypeException;
    void removeContributor(Contributor_Sec33_G10 c);

    // 2) collaborations
    void addCollaboration(Contributor_Sec33_G10 a, Contributor_Sec33_G10 b, String projectId);
    void removeCollaboration(String a, String b, String projectId);

    // 3) updates
    void updateName(Contributor_Sec33_G10 c, String name);
    void updateType(Contributor_Sec33_G10 c, String typeStr) throws Exceptions.InvalidContributorTypeException;
    void updateRegion(Contributor_Sec33_G10 c, String regionStr) throws Exceptions.InvalidRegionException;


    void addProject(Project_Sec33_G10 p);


}
