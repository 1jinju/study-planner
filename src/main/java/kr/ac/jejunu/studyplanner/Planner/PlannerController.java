package kr.ac.jejunu.studyplanner.Planner;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jejunu.studyplanner.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerService plannerService;
    private final UserService userService;

    @GetMapping("/planners")
    public String getPlanners(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Planner> planners = plannerService.getPlannersByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("planners", planners);
        return "main";
    }

    @PostMapping("/planner/add")
    public String createPlanner(@ModelAttribute Planner planner) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        planner.setUser(userService.getByUsername(username));
        plannerService.savePlanner(planner, username);
        return "redirect:/planners";
    }

    @PostMapping("/planner/{id}/put")
    public String putPlanner(@PathVariable Long id, @ModelAttribute Planner updatedPlanner) {
        Planner planner = plannerService.getPlannerById(id);
        planner.setTitle(updatedPlanner.getTitle());
        plannerService.updatePlanner(planner);
        return "redirect:/planners";
    }

    @PostMapping("/planner/{id}/delete")
    public String deletePlanner(@PathVariable Long id, HttpServletRequest request) {
        boolean deleted = plannerService.deletePlanner(id);
        if (!deleted) {
            return "PlannerNotFound";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
