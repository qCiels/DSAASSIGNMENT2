package Contributors;

import Exceptions.*;
import ENUMS.*;

public class SchoolContributor extends Contributor {

    public SchoolContributor(String name, String region) throws InvalidRegionException, InvalidContributorTypeException {
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
