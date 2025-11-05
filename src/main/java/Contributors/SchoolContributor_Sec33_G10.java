package Contributors;

import Exceptions.*;

public class SchoolContributor_Sec33_G10 extends Contributor_Sec33_G10 {

    public SchoolContributor_Sec33_G10(String name, String region) throws InvalidRegionException, InvalidContributorTypeException {
        super(name,"School", region);
    }
    @Override
    public void joinProject(int projectId) {
        addProjectId(projectId);
        System.out.println(getName() + " (School) joined project " + projectId + " to involve students in the community.");
    }

    @Override
    public void introduce() {
        System.out.println(getName() + " from " + getRegion() + " focuses on educational outreach and student engagement.");
    }

    @Override
    public String roleLabel() {
        return "Educational Institution";
    }
}
