package Contributors;

import Exceptions.*;

public class SchoolContributor_Sec33_G10 extends Contributor_Sec33_G10 {

    public SchoolContributor_Sec33_G10(String name, String region, String id) throws InvalidRegionException, InvalidContributorTypeException {
        super(name,"School", region, id);
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
