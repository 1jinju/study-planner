package kr.ac.jejunu.studyplanner.Member;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


//    @GetMapping("/")
//    public String getUserById(@PathVariable Long id, Model model) {
//        User currentUser = userSecurityService.getUserByUsername(userDetails.getUsername());
//        List<Planner> plannerList = plannerService.getPlannerByUserId(id);
//        model.addAttribute("user", user);
//        model.addAttribute("plannerList", plannerList);
//        return "main";
//    }

    @GetMapping("/member/signup")
    public String signup(MemberCreateForm memberCreateForm){
        return "signup_form";
    }

    @PostMapping("/member/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }

        if(!memberCreateForm.getPassword().equals(memberCreateForm.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            memberService.create(memberCreateForm.getMembername(), memberCreateForm.getPassword());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/member/login";
    }

    @GetMapping("/member/login")
    public String login() {
        return "login_form";
    }

    @PostMapping("/member/login")
    public String login(@RequestParam("membername") String membername,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        boolean isAuthenticated = memberService.authenticateMember(membername, password);
        if (isAuthenticated) {
            Member authenticatedMember = memberService.getAuthenticatedUser();
            session.setAttribute("authenticatedMember", authenticatedMember);
            return "redirect:/main";
        } else {
            // 인증 실패 시, 에러 메시지를 반환하거나 로그인 페이지를 다시 보여줄 수 있음
            redirectAttributes.addFlashAttribute("errorMessage", "로그인에 실패하셨습니다.");
            return "redirect:/member/login?error";
        }
    }

}
