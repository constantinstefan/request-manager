package fii.request.manager.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    //LocalDateTime createdAt;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL)
    private Set<WorkflowStep> workflowSteps;

    @OneToOne
    @JoinColumn(name = "workflow_sharing_id")
    private WorkflowSharing sharing;
}
