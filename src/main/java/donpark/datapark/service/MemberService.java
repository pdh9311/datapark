package donpark.datapark.service;

import donpark.datapark.domain.Member;
import donpark.datapark.dto.LoginForm;
import donpark.datapark.dto.MemberInfo;
import donpark.datapark.dto.SignUpForm;
import donpark.datapark.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  // optional stream 학습 후 코드 줄여볼 것
  public MemberInfo login(LoginForm form) {
    return memberRepository.findByLoginId(form.getLoginId())
        .filter(m -> m.getLoginPw().equals(form.getLoginPw()))
        .map(m -> MemberInfo.of(m))
        .orElse(null);
  }

  public Optional<Member> checkDuplicateLoginId(String loginId) {
    // 아이디 중복 확인
    return memberRepository.findByLoginId(loginId);
  }

  public void signup(SignUpForm form) {
    Member member = Member.of(form);
    memberRepository.save(member);
  }

  public Member findById(Long id) {
    return memberRepository.findById(id)
        .orElse(null);
  }

}
