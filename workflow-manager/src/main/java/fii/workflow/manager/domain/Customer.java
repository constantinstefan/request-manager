package fii.workflow.manager.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    String email;
    String password;
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Customer_To_Group",
            joinColumns = { @JoinColumn(name = "customer_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private Set<CustomerGroup> customerGroups = new HashSet<>();
}
