package kr.ac.jejunu.studyplanner.Goal;

import jakarta.persistence.*;
import kr.ac.jejunu.studyplanner.Planner.Planner;
import kr.ac.jejunu.studyplanner.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

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
    private Member user;

    @Column
    private String content;
    private int studyTime;
    private String completeYn;
    @CreatedDate
    private String createdAt;


}
