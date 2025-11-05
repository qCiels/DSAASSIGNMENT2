public record CollaborationEdge_Sec33_G10(int projectId) {
    public CollaborationEdge_Sec33_G10 {
        if (projectId < 0)
            throw new IllegalArgumentException("ERROR: PROJECT ID CANNOT BE NEGATIVE");
    }

    @Override
    public String toString() {
        return "Project " + projectId;
    }
}
