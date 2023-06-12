package kr.ac.jejunu.studyplanner.Planner;

import kr.ac.jejunu.studyplanner.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Optional<Planner> findById(Long plannerId);
    List<Planner> findPlannerByMember(Member member);
}