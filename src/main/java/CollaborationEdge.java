public record CollaborationEdge(int projectId) {
    public CollaborationEdge {
        if (projectId < 0)
            throw new IllegalArgumentException("ERROR: PROJECT ID CANNOT BE NEGATIVE");
    }

    @Override
    public String toString() {
        return "Project " + projectId;
    }
}
