package kr.ac.jejunu.studyplanner.Goal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.ac.jejunu.studyplanner.Planner.Planner;
import kr.ac.jejunu.studyplanner.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "goals")
@NoArgsConstructor
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
    @NotNull
    private int studyTime;

    private String completeYn;
    @CreatedDate
    private LocalDate createdAt;


}
