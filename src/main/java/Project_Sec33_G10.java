import java.util.List;

public record Project_Sec33_G10(String id, String name, List<String> contributors) {
    public Project_Sec33_G10 {
        if (id == null || id.isBlank() || id.isEmpty())
            throw new IllegalArgumentException("ERROR: PROJECT ID CANNOT BE EMPTY");
        if (name == null || name.isBlank() || name.isEmpty())
            throw new IllegalArgumentException("ERROR PROJECT NAME CANNOT BE EMPTY");
        name = name.trim();
        contributors = (contributors == null) ? List.of() : List.copyOf(contributors);

    }


    public String label() {
        return name + " [#" + id + "]";
    }
}
