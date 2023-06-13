package kr.ac.jejunu.studyplanner.Planner;

import kr.ac.jejunu.studyplanner.User.User;
import kr.ac.jejunu.studyplanner.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;

    @Autowired
    public PlannerService(PlannerRepository plannerRepository) {
        this.plannerRepository = plannerRepository;
    }

    @Autowired
    private UserService userService;

    public Planner getPlannerById(Long plannerId) {
        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);
        return plannerOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Planner not found with id: " + plannerId));
    }
    public List<Planner> getPlannersByUsername(String username) {
        User user = userService.getByUsername(username);
        return plannerRepository.findPlannerByUser(user);
    }

    public void savePlanner(Planner planner, String username) {
        User user = userService.getByUsername(username);
        plannerRepository.save(planner);
    }

    public void updatePlanner(Planner planner) {
        plannerRepository.save(planner);
    }

    public boolean deletePlanner(Long plannerId) {
        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);
        if (plannerOptional.isPresent()) {
            plannerRepository.delete(plannerOptional.get());
            return true;
        }
        return false;
    }


}