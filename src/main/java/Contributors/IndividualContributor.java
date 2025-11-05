package Contributors;

import Exceptions.*;

public class IndividualContributor extends Contributor {

    public IndividualContributor(String name, String region) throws InvalidRegionException, InvalidContributorTypeException {
        super(name,"Individual", region);
    }

    @Override
    public void joinProject(int projectId) {
        addProjectId(projectId);
        System.out.println(getName() + " joined project " + projectId + " as an individual.");
    }

    @Override
    public void introduce() {
        System.out.println("Hello, I'm " + getName() + " from " + getRegion() + ".");
    }

    @Override
    public String roleLabel() {
        return "Individual Contributor";
    }
}
