package kr.ac.jejunu.studyplanner.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();
    Optional<Member> findByMembername(String name);
    Member getByMembername(String name);

    Optional<Member> findById(int id);
}
