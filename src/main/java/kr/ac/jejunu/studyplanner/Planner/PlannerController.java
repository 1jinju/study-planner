package kr.ac.jejunu.studyplanner.Planner;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.jejunu.studyplanner.Member.MemberService;
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
    private final MemberService userService;

    @GetMapping("/planners")
    public String getPlanners(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String membername = authentication.getName();
        List<Planner> planners = plannerService.getPlannersByUsername(membername);
        model.addAttribute("membername", membername);
        model.addAttribute("planners", planners);
        return "main";
    }

    @PostMapping("/planners/add")
    public String createPlanner(@ModelAttribute Planner planner) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        planner.setMember(userService.getByUsername(username));
        plannerService.savePlanner(planner, username);
        return "redirect:/main";
    }

    @PostMapping("planner/{id}/put")
    public String putPlanner(@PathVariable Long id, @ModelAttribute Planner updatedPlanner) {
        Planner planner = plannerService.getPlannerById(id);
        planner.setTitle(updatedPlanner.getTitle());
        plannerService.updatePlanner(planner);
        return "redirect:/user/" + planner.getMember().getId(); // 리다이렉트 URL
    }

    @PostMapping("planner/{id}/delete")
    public String deletePlanner(@PathVariable Long id, HttpServletRequest request) {
        boolean deleted = plannerService.deletePlanner(id);
        if (!deleted) {
            return "PlannerNotFound";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
