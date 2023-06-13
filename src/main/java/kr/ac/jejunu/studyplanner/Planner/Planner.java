package kr.ac.jejunu.studyplanner.Planner;

import jakarta.persistence.*;
import kr.ac.jejunu.studyplanner.Goal.Goal;
import kr.ac.jejunu.studyplanner.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "planners")
@NoArgsConstructor
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;

    @OneToMany(mappedBy = "planner", cascade = CascadeType.ALL)
    private List<Goal> goals= new ArrayList<>();

}