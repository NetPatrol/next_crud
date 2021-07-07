package org.course.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

