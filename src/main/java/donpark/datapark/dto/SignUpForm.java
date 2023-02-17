package donpark.datapark.dto;

import donpark.datapark.domain.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SignUpForm {

  private String loginId;

  private String loginPw;

  private String loginPwChk;

  private String email;

  private String phone;

  private String name;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  private Gender gender;

}
