package ENUMS;

public enum Region {
    ABUDHABI,
    DUBAI,
    SHARJAH,
    AJMAN,
    UMM_AL_QUWAIN,
    FUJAIRAH,
    RAS_AL_KHAIMAH;

    public void printRegions() {
        System.out.println("ALLOWED REGION INPUTS;");
        for (Region region : Region.values()) {
            System.out.println(region);
        }
    }
    }

