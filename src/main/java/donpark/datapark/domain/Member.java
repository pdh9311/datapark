package donpark.datapark.domain;

import donpark.datapark.dto.SignUpForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseTime {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  @Column
  private String loginId;

  @Column
  private String loginPw;

  @Column
  private String email;

  @Column
  private String phone;

  @Column
  private String name;

  @Column
  private LocalDate birth;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  public static Member of(SignUpForm form) {
    return Member.builder()
        .loginId(form.getLoginId())
        .loginPw(form.getLoginPw())
        .email(form.getEmail())
        .phone(form.getPhone())
        .name(form.getName())
        .birth(form.getBirth())
        .gender(form.getGender())
        .build();
  }


}
