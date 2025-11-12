package Contributors;

import Exceptions.*;

public class NGOContributor_Sec33_G10 extends Contributor_Sec33_G10 {

    public NGOContributor_Sec33_G10(String name, String region, String id) throws InvalidRegionException, InvalidContributorTypeException {
        super(name,"NGO", region, id);
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
