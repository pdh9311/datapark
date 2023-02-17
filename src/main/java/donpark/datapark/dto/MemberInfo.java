package donpark.datapark.dto;

import donpark.datapark.domain.Gender;
import donpark.datapark.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class MemberInfo {

  private Long id;
  private String loginId;
  private String email;
  private String phone;
  private String name;
  private LocalDate birth;
  private Gender gender;

  public static MemberInfo of(Member member) {
    return MemberInfo.builder()
        .id(member.getId())
        .loginId(member.getLoginId())
        .email(member.getEmail())
        .phone(member.getPhone())
        .name(member.getName())
        .birth(member.getBirth())
        .gender(member.getGender())
        .build();
  }

}
