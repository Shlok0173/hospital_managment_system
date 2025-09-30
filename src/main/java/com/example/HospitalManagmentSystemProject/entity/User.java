package com.example.HospitalManagmentSystemProject.entity;

import com.example.HospitalManagmentSystemProject.type.RoleType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @JoinColumn(unique = true)
    private  String username;

    private String  password;


//    @ElementCollection(fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//      Set<RoleType> roleTypes=new HashSet<>();
    public User(Long id, String userName, String password) {
        this.id = id;
        this.username = userName;
        this.password = password;
    }

    public User() {
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Set<RoleType> getRoleTypes() {
//        return roleTypes;
//    }
//
//    public void setRoleTypes(Set<RoleType> roleTypes) {
//        this.roleTypes = roleTypes;
//    }

}
