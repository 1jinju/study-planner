package kr.ac.jejunu.studyplanner.Planner;

import kr.ac.jejunu.studyplanner.Member.Member;
import kr.ac.jejunu.studyplanner.Member.MemberService;
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
    private MemberService memberService;

    public Planner getPlannerById(Long plannerId) {
        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);
        return plannerOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Planner not found with id: " + plannerId));
    }
    public List<Planner> getPlannersByUsername(String username) {
        Member member = memberService.getByUsername(username);
        return plannerRepository.findPlannerByMember(member);
    }

    public void savePlanner(Planner planner, String username) {
        Member member = memberService.getByUsername(username);
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