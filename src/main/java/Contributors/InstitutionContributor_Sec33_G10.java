package Contributors;

import Exceptions.*;

public class InstitutionContributor_Sec33_G10 extends Contributor_Sec33_G10 {

    public InstitutionContributor_Sec33_G10(String name, String region) throws InvalidRegionException, InvalidContributorTypeException {
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
