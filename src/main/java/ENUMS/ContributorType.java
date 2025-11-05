package ENUMS;

public enum ContributorType {
    INDIVIDUAL,
    NGO,
    SCHOOL,
    INSTITUTION;
    public void printRegions() {
        System.out.println("ALLOWED REGION INPUTS;");
        for (ContributorType type : ContributorType.values()) {
            System.out.println(type);
        }
    }
}
