package nl.quintor.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class RoleModel {

    private static final String prefix = "ROLE_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "roles")
    private Set<UserAccountModel> users = new HashSet<>();

    public RoleModel() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = prefix + name;
    }

    public Set<UserAccountModel> getUsers() {
        return users;
    }

    public void setUsers(Set<UserAccountModel> users) {
        this.users = users;
    }

    public RoleModel(String name){
        this.name = prefix + name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}