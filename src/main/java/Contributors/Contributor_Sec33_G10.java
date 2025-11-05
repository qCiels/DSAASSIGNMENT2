package Contributors;

import ENUMS.*;
import Exceptions.*;
import java.util.*;

public abstract class Contributor_Sec33_G10 {

    private final String uid = java.util.UUID.randomUUID().toString(); // immutable id
    private String name;
    private ContributorType_Sec33_G10 type;
    private Region_Sec33_G10 regionSec33G10;
    private final List<Integer> projectIds = new ArrayList<>();

    protected Contributor_Sec33_G10(String name, String type, String region) throws InvalidRegionException, InvalidContributorTypeException {
        setRegion(region);
        setName(name);
        setType(type);

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

    // project participation bookkeeping
    public List<Integer> getProjectIds() {
        return Collections.unmodifiableList(projectIds);
    }
    protected void addProjectId(int projectId) {
        if (projectId < 0)
            throw new IllegalArgumentException("projectId < 0");
        projectIds.add(projectId);
    }

    // --- abstract hooks (define HOW the subtype behaves) ---
    /** Called when this contributor joins a project; impl should decide any extra behavior. */
    public abstract void joinProject(int projectId);

    /** Short self-presentation to UI/logs. */
    public abstract void introduce();

    /** Domain label for UI (e.g., "Individual", "NGO", etc.). */
    public abstract String roleLabel();

    // --- utility ---
    @Override
    public String toString() {
        return name + " [" + type + ", " + regionSec33G10 + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contributor_Sec33_G10 c)) return false;
        return uid.equals(c.uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

}
