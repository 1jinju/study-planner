package kr.ac.jejunu.studyplanner.Goal;

import jakarta.persistence.*;
import kr.ac.jejunu.studyplanner.Planner.Planner;
import kr.ac.jejunu.studyplanner.User.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="planner_id")
    private Planner planner;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;
    private boolean completed_yn;
    private int studyTime;
    @CreatedDate
    private LocalDate created_at;
}
