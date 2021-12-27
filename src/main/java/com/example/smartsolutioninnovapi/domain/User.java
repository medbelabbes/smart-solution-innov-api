package com.example.smartsolutioninnovapi.domain;

import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;

    @Column(unique = true)
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    private UserStatus status;

    private String password;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();


    @CreatedBy
    private Long createdBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @LastModifiedBy
    private Long lastModifiedBy;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @ManyToMany(fetch = LAZY)
    private Collection<Task> tasks = new ArrayList<>();
}
