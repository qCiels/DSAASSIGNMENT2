package Contributors;

import ENUMS.*;
import Exceptions.*;
import java.util.*;

public abstract class Contributor_Sec33_G10 {

    protected String id;
    protected String name;
    protected ContributorType_Sec33_G10 type;
    protected Region_Sec33_G10 regionSec33G10;


    protected Contributor_Sec33_G10(String name, String type, String region, String id) throws InvalidRegionException, InvalidContributorTypeException {
        setRegion(region);
        setName(name);
        setType(type);
        setId(id);

    }

     public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("ERROR: INVALID CONTRIBUTOR NAME INPUT, try again...");
        this.name = name.trim();
    }


    public void setType(String type) throws InvalidContributorTypeException {
        if (type == null || type.isBlank() || type.isEmpty()) {
            throw new InvalidContributorTypeException("ERROR: INVALID CONTRIBUTOR TYPE INPUT, try again...");
        }
        this.type = ContributorType_Sec33_G10.valueOf(type.trim().toUpperCase());
    }

    public void setRegion(String region) throws InvalidRegionException {
        if (region == null || region.isBlank())
            throw new InvalidRegionException("ERROR: INVALID CONTRIBUTOR REGION INPUT, try again...");
       this.regionSec33G10 = Region_Sec33_G10.valueOf(region.trim().toUpperCase());
    }
    public void setId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("ERROR: INVALID CONTRIBUTOR ID INPUT, try again...");
        this.id = id.trim();
    }

    //getters
    public Region_Sec33_G10 getRegion() {
        return regionSec33G10;
    }

    public ContributorType_Sec33_G10 getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contributor_Sec33_G10 that = (Contributor_Sec33_G10) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



    /** Short self-presentation to UI/logs. */
    public abstract void introduce();

    /** Domain label for UI (e.g., "Individual", "NGO", etc.). */
    public abstract String roleLabel();

    // --- utility ---
    @Override
    public String toString() {
        return name + " [" + type + ", " + regionSec33G10 + "]";
    }




}
