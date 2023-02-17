package donpark.datapark.dto;

import donpark.datapark.domain.Gender;
import donpark.datapark.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class MemberDto {

  private Long id;
  private String loginId;
  private String loginPw;
  private String email;
  private String phone;
  private String name;
  private LocalDate birth;
  private Gender gender;

  public static MemberDto of(Member member) {
    return MemberDto.builder()
        .id(member.getId())
        .loginId(member.getLoginId())
        .loginPw(member.getLoginPw())
        .email(member.getEmail())
        .phone(member.getPhone())
        .name(member.getName())
        .birth(member.getBirth())
        .gender(member.getGender())
        .build();
  }
}
