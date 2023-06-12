package kr.ac.jejunu.studyplanner.Goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getByPlannerId(Long plannerId) {
        return goalRepository.findByPlannerId(plannerId);
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

}
