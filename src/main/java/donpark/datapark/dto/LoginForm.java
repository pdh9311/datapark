package donpark.datapark.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginForm {
  private String loginId;
  private String loginPw;
}
