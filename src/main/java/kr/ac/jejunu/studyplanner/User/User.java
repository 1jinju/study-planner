package kr.ac.jejunu.studyplanner.User;

import jakarta.persistence.*;
import kr.ac.jejunu.studyplanner.Planner.Planner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Planner> plannerList = new ArrayList<>();;
}
