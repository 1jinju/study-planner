package kr.ac.jejunu.studyplanner.Goal;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @GetMapping(value = "/planner/{planner_id}")
    public String getByPlannerId(@PathVariable("planner_id") Long plannerId, Model model) {
        List<Goal> goals = goalService.getByPlannerId(plannerId);
        model.addAttribute("goals", goals);
        model.addAttribute("plannerId", plannerId);
        return "goalList";
    }

    @GetMapping(value = "/planner/{planner_id}/add")
    public String create(@PathVariable("planner_id") Long plannerId, Model model) {
        model.addAttribute("plannerId", plannerId);
        return "createGoal";
    }

//    @GetMapping("/planner/{planner_id}/add")
//    public String createGoalForm(@PathVariable("planner_id") Long plannerId, @PathVariable("user_id") Long userId, @ModelAttribute Goal goal) {
//        Planner planner = new Planner();
//        planner.setId(plannerId);
//        goal.setPlanner(planner);
//
//        User user = new User();
//        user.setId(userId);
//        goal.setUser(user);
//
//        goalService.saveGoal(plannerId, userId, goal);
//        return "createGoal";
//    }

    @PostMapping("/planner/{planner_id}/add")
    public String createGoal(@ModelAttribute Goal goal, Model model) {
        Goal savedGoal = goalService.saveGoal(goal);
        model.addAttribute("goal", savedGoal);
        return "redirect:/goal/planner/{planner_id}";
    }


    @PutMapping("/{id}/put")
    public String updateGoal(@PathVariable("id") Long id, @ModelAttribute Goal goal) {
        Goal updatedGoal = goalService.updateGoal(id, goal);
        if (updatedGoal == null) {
            return "goalNotFound";
        }
        return "redirect:/goal/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteGoal(@PathVariable("id") Long id, HttpServletRequest request) {
        boolean deleted = goalService.deleteGoal(id);
        if (!deleted) {
            return "goalNotFound";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}

