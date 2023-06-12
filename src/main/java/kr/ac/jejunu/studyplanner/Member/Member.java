package kr.ac.jejunu.studyplanner.Member;

import jakarta.persistence.*;
import kr.ac.jejunu.studyplanner.Planner.Planner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "members")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String membername;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Planner> planners= new ArrayList<>();
}
