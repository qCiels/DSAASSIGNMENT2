package ENUMS;

public enum ContributorType_Sec33_G10 {
    INDIVIDUAL,
    NGO,
    SCHOOL,
    INSTITUTION;
    public void printRegions() {
        System.out.println("ALLOWED REGION INPUTS;");
        for (ContributorType_Sec33_G10 type : ContributorType_Sec33_G10.values()) {
            System.out.println(type);
        }
    }
}
