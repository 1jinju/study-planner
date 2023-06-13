package kr.ac.jejunu.studyplanner.Goal;

import kr.ac.jejunu.studyplanner.User.User;
import kr.ac.jejunu.studyplanner.User.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public List<Goal> getByPlannerId(Long plannerId) {
        LocalDate currentDate = LocalDate.now();
        return goalRepository.findByPlannerIdAndCreatedAt(plannerId, currentDate);
    }

    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public Goal updateGoal(Long id, Goal goal) {
        Goal existingGoal = goalRepository.findById(id).orElse(null);
        if (existingGoal == null) {
            return null;
        }

        existingGoal.setContent(goal.getContent());
        existingGoal.setCompleteYn(goal.getCompleteYn());
        existingGoal.setStudyTime(goal.getStudyTime());

        return goalRepository.save(existingGoal);
    }

    public boolean deleteGoal(Long id) {
        if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Goal getGoalById(Long id) {
        return goalRepository.findById(id).orElse(null);
    }

    public List<Goal> searchByDate(Long plannerId, LocalDate selectedDate, String username) {
        User user = userRepository.findByUsername(username);
        return goalRepository.findByPlannerIdAndCreatedAtAndUser(plannerId, selectedDate, user);
    }

    public int getGoalCountByPlannerId(Long plannerId, LocalDate startDate, LocalDate endDate) {
        return goalRepository.countByPlannerIdAndCreatedAtBetween(plannerId, startDate, endDate);
    }

    public int getCompletedGoalCountByPlannerId(Long plannerId, LocalDate startDate, LocalDate endDate) {
        return goalRepository.countByPlannerIdAndCompleteYnAndCreatedAtBetween(plannerId, "Y", startDate, endDate);
    }

    public int getTotalStudyTimeInRange(Long plannerId, LocalDate startDate, LocalDate endDate) {
        return goalRepository.getTotalStudyTimeInRange(plannerId, startDate, endDate);
    }
}
