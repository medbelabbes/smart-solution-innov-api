package com.example.smartsolutioninnovapi.domain;

import com.example.smartsolutioninnovapi.domain.enumeration.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskActivity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private TaskStatus newStatus;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

}
