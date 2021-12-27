package com.example.smartsolutioninnovapi.domain;

import com.example.smartsolutioninnovapi.domain.enumeration.TaskPriority;
import com.example.smartsolutioninnovapi.domain.enumeration.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String description;
    private TaskPriority priority;
    private TaskStatus currentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @ManyToMany(fetch = EAGER)
    private Collection<Tag> tags = new ArrayList<>();

    private int estimateTime;

    private int sprintPoints;

    @OneToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    private List<TaskActivity> activities;

}
