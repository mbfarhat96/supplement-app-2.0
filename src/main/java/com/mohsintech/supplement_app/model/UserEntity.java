package com.mohsintech.supplement_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    //This table below is a join table that's necessary because a user can have many roles, and a role can belong to
    //many users. Therefore, we create a join table to associate the two.
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //we want roles to be fetched immediately for users
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
    List<Role> roles = new ArrayList<>();
}
