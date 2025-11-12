import org.jgrapht.graph.DefaultEdge;

public class CollaborationEdge_Sec33_G10 extends DefaultEdge {
    private String projectId;

    public CollaborationEdge_Sec33_G10(String projectId) {
        this.projectId = projectId;
    }
    public String getProjectId() {
        return projectId;
    }
    @Override
    public String toString() {
        return projectId;
    }
}