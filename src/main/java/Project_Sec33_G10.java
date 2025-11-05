public record Project_Sec33_G10(int id, String name, String category) {
    public Project_Sec33_G10 {
        if (id < 0)
            throw new IllegalArgumentException("ERROR: PROJECT ID CANNOT BE NEGATIVE");
        if (name == null || name.isBlank() || name.isEmpty())
            throw new IllegalArgumentException("ERROR PROJECT NAME CANNOT BE EMPTY");
        name = name.trim();

        // allow empty category to default cleanly
        category = (category == null || category.isBlank() || category.isEmpty()) ? "Uncategorized" : category.trim();
    }


    public String label() {
        return name + " [#" + id + ", " + category + "]";
    }
}
