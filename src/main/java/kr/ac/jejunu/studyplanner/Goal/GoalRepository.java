package kr.ac.jejunu.studyplanner.Goal;

import kr.ac.jejunu.studyplanner.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByPlannerIdAndCreatedAt(Long plannerId, LocalDate createdAt);
    List<Goal> findByPlannerIdAndCreatedAtAndUser(Long plannerId, LocalDate createdAt, User user);

    int countByPlannerIdAndCreatedAtBetween(Long plannerId, LocalDate startDate, LocalDate endDate);

    int countByPlannerIdAndCompleteYnAndCreatedAtBetween(Long plannerId, String completeYn, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(g.studyTime), 0) FROM Goal g WHERE g.createdAt >= :startDate AND g.createdAt <= :endDate AND g.planner.id = :plannerId")
    int getTotalStudyTimeInRange(@Param("plannerId") Long plannerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
