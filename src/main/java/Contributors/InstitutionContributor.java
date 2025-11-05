package Contributors;

import Exceptions.*;
import ENUMS.*;

public class InstitutionContributor extends Contributor {

    public InstitutionContributor(String name, String region) throws InvalidRegionException, InvalidContributorTypeException {
        super(name, "Institution", region);
    }

    @Override
    public void joinProject(int projectId) {
        addProjectId(projectId);
        System.out.println(getName() + " (Institution) partnered in project " + projectId + ".");
    }

    @Override
    public void introduce() {
        System.out.println(getName() + " is an institution contributing from " + getRegion() + ".");
    }

    @Override
    public String roleLabel() {
        return "Government or Corporate Institution";
    }
}
