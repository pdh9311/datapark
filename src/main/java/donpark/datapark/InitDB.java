package donpark.datapark;

import donpark.datapark.domain.Gender;
import donpark.datapark.domain.Member;
import donpark.datapark.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDB {

  private final MemberRepository memberRepository;

  @PostConstruct
  public void init() {
    Member member = Member.builder()
        .id(0L)
        .loginId("qwe")
        .loginPw("qwe123")
        .email("qwe@qwe.com")
        .phone("010-001-0010")
        .name("qwe")
        .birth(LocalDate.of(2023, 2, 10))
        .gender(Gender.MAN)
        .build();
    memberRepository.save(member);
  }
}
