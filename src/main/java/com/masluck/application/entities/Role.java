package com.masluck.application.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles",
//    joinColumns = @JoinColumn(name = "role_id"),
//    inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> users;

}
