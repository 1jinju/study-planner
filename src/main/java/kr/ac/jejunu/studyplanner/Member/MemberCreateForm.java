package kr.ac.jejunu.studyplanner.Member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {
    @Size(min=3,max=25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String membername;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;
}