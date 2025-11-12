package Contributors;

import Exceptions.*;

public class IndividualContributor_Sec33_G10 extends Contributor_Sec33_G10 {

    public IndividualContributor_Sec33_G10(String name, String region, String id) throws InvalidRegionException, InvalidContributorTypeException {
        super(name,"Individual", region, id);
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
