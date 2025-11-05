package Contributors;

import Exceptions.*;

public class NGOContributor extends Contributor {

    public NGOContributor(String name, String region) throws InvalidRegionException, InvalidContributorTypeException {
        super(name,"NGO", region);
    }

    @Override
    public void joinProject(int projectId) {
        addProjectId(projectId);
        System.out.println(getName() + " (NGO) joined project " + projectId + " to support community development.");
    }

    @Override
    public void introduce() {
        System.out.println("We are " + getName() + ", an NGO operating in " + getRegion() + ".");
    }

    @Override
    public String roleLabel() {
        return "Non-Governmental Organization";
    }
}
