package kr.ac.jejunu.studyplanner.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String membername, String password){
        Member member = new Member();
        member.setMembername(membername);
        member.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(member);
        return member;
    }
    public Member getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof Member) {
            return (Member) principal;
        }
        return null;
    }

    public boolean authenticateMember(String membername, String password) {
        Optional<Member> optionalMember = memberRepository.findByMembername(membername);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (passwordEncoder.matches(password, member.getPassword())) {
                return true; // 인증 성공
            }
        }
        return false;
    }

    public Optional<Member> getById(int id){
        return memberRepository.findById(id);
    }

    public Member getByUsername(String username){
        return memberRepository.getByMembername(username);
    };
}
