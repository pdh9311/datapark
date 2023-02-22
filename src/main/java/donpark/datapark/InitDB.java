package donpark.datapark;

import donpark.datapark.domain.Document;
import donpark.datapark.domain.Gender;
import donpark.datapark.domain.Icon;
import donpark.datapark.domain.Member;
import donpark.datapark.repository.DocumentRepository;
import donpark.datapark.repository.IconRepository;
import donpark.datapark.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDB {

  private final MemberRepository memberRepository;
  private final DocumentRepository documentRepository;
  private final IconRepository iconRepository;

  @PostConstruct
  public void init() {
    Member member = Member.builder()
        .loginId("qwe")
        .loginPw("qwe123!@#")
        .email("qwe@qwe.com")
        .phone("010-001-0010")
        .name("qwe")
        .birth(LocalDate.of(2023, 2, 10))
        .gender(Gender.MAN)
        .build();
    memberRepository.save(member);

    Icon icon = Icon.builder()
        .originName("page.svg")
        .storedName("page.svg")
        .path("/icons/page.svg")
        .build();

    iconRepository.save(icon);

    for (int i = 1; i <= 3; i++) {
      Document document = Document.builder()
          .title("title" + i)
          .content("content" + i)
          .hits(0L)
          .member(member)
          .icon(icon)
          .build();
      documentRepository.save(document);
    }

  }
}
