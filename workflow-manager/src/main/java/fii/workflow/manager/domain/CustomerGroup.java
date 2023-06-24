package fii.workflow.manager.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class CustomerGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long groupId;

    String name;
    String description;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE},
                mappedBy = "customerGroups")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();
}
