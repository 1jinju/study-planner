package kr.ac.jejunu.studyplanner.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/user/login")
    public String showLoginForm() {
        return "loginForm";
    }

    @GetMapping("/user/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signupForm";
    }

    @PostMapping("/user/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "signupForm";
        }

        if(!userCreateForm.getPassword().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.");
            return "signupForm";
        }

        try {
            // 사용자 조회
            User existingUser = userService.findByUsername(userCreateForm.getUsername());

            // 이미 등록된 사용자인 경우 예외 처리
            if (existingUser != null) {
                bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
                return "signupForm";
            }
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword());
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signupForm";
        }

        return "redirect:/user/login";
    }


}
