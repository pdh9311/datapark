package donpark.datapark.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @Column
  private String gender;

}
