package kr.ac.jejunu.studyplanner.Goal;

import kr.ac.jejunu.studyplanner.Planner.Planner;
import kr.ac.jejunu.studyplanner.Planner.PlannerService;
import kr.ac.jejunu.studyplanner.User.User;
import kr.ac.jejunu.studyplanner.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;
    private final PlannerService plannerService;
    private final UserService userService;

    @GetMapping(value = "/planner/{planner_id}")
    public String getByPlannerId(@PathVariable("planner_id") Long plannerId, Model model) {
        List<Goal> goals = goalService.getByPlannerId(plannerId);
        model.addAttribute("goals", goals);
        model.addAttribute("plannerId", plannerId);
        return "goalList";
    }

    @GetMapping("/planner/{planner_id}/search")
    public String search(@PathVariable("planner_id") Long plannerId, Model model) {
        model.addAttribute("plannerId", plannerId);
        return "search";
    }

    @GetMapping("/planner/{plannerId}/date")
    public String searchByDate(@PathVariable("plannerId") Long plannerId, @RequestParam("selectedDate") String selectedDate, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LocalDate date = LocalDate.parse(selectedDate);
        List<Goal> goals = goalService.searchByDate(plannerId, date, username);
        model.addAttribute("goals", goals);
        model.addAttribute("selectedDate", selectedDate);  // 선택된 날짜를 모델에 추가
        return "search";
    }

    @GetMapping("/planner/{plannerId}/statistics")
    public String showStatistics(@PathVariable("plannerId") Long plannerId, Model model) {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        // 플래너 ID에 해당하는 목표 개수, 달성된 목표 개수 조회
        int goalCount = goalService.getGoalCountByPlannerId(plannerId, firstDayOfMonth, currentDate);
        int completedGoalCount = goalService.getCompletedGoalCountByPlannerId(plannerId, firstDayOfMonth, currentDate);

        // 목표 달성률 계산
        double goalAchievementRate = calculateGoalAchievementRate(goalCount, completedGoalCount);

        int totalStudyTime = goalService.getTotalStudyTimeInRange(plannerId, firstDayOfMonth, currentDate);

        int hours = totalStudyTime / 60;
        int minutes = totalStudyTime % 60;
        String formattedStudyTime = hours + "시간 " + minutes + "분";

        // 모델에 필요한 데이터 추가
        model.addAttribute("plannerId", plannerId);
        model.addAttribute("goalCount", goalCount);
        model.addAttribute("completedGoalCount", completedGoalCount);
        model.addAttribute("goalAchievementRate", goalAchievementRate);
        model.addAttribute("formattedStudyTime", formattedStudyTime);

        return "statistics";
    }

    private double calculateGoalAchievementRate(int goalCount, int completedGoalCount) {
        if (goalCount == 0) {
            return 0;
        }
       // 소수점 둘째 자리까지만 출력
        double achievementRate = (completedGoalCount * 100.0) / goalCount;
        return Math.round(achievementRate * 100.0) / 100.0;
    }


    @GetMapping(value = "/planner/{planner_id}/add")
    public String showCreateGoalForm(@PathVariable("planner_id") Long plannerId, Model model) {
        model.addAttribute("plannerId", plannerId);
        return "createGoal";
    }


    @PostMapping("/planner/{planner_id}/add")
    public String createGoal(@PathVariable("planner_id") Long plannerId, @ModelAttribute Goal goal, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUsername(username);

        Planner planner = plannerService.getPlannerById(plannerId);
        goal.setPlanner(planner);
        goal.setUser(user);

        Goal savedGoal = goalService.saveGoal(goal);
        model.addAttribute("goal", savedGoal);
        return "redirect:/goal/planner/{planner_id}";
    }

    @GetMapping("/{id}/put")
    public String showUpdateGoalForm(@PathVariable("id") Long id, Model model) {
        Goal goal = goalService.getGoalById(id);
        if (goal == null) {
            return "goalNotFound";
        }

        Long plannerId = goal.getPlanner().getId(); // 플래너 ID 가져오기
        model.addAttribute("goalId", id);
        model.addAttribute("plannerId", plannerId);
        model.addAttribute("goal", goal);
        return "putGoal";
    }

    @PostMapping("/{id}/put")
    public String updateGoal(@PathVariable("id") Long id, @ModelAttribute("goal") Goal updatedGoal, Model model) {
        Goal goal = goalService.getGoalById(id);
        if (goal == null) {
            return "goalNotFound";
        }

        goal.setContent(updatedGoal.getContent());
        goal.setStudyTime(updatedGoal.getStudyTime());
        goal.setCompleteYn(updatedGoal.getCompleteYn());

        Goal savedGoal = goalService.updateGoal(id, goal);
        if (savedGoal == null) {
            return "goalNotFound";
        }
        Long plannerId = savedGoal.getPlanner().getId();
        return "redirect:/goal/planner/" + plannerId;
    }

    @PostMapping("/{id}/delete")
    public String deleteGoal(@PathVariable("id") Long id) {
        Goal deletedGoal = goalService.getGoalById(id);

        boolean deleted = goalService.deleteGoal(id);
        if (!deleted) {
            return "goalNotFound";
        }

        Long plannerId = deletedGoal.getPlanner().getId();

        return "redirect:/goal/planner/" + plannerId;
    }
}

