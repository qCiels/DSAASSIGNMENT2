package ENUMS;

public enum Region_Sec33_G10 {
    ABU_DHABI,
    DUBAI,
    SHARJAH,
    AJMAN,
    UMM_AL_QUWAIN,
    FUJAIRAH,
    RAS_AL_KHAIMAH;

    public void printRegions() {
        System.out.println("ALLOWED REGION INPUTS;");
        for (Region_Sec33_G10 regionSec33G10 : Region_Sec33_G10.values()) {
            System.out.println(regionSec33G10);
        }
    }
    }

