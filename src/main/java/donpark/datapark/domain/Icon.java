package donpark.datapark.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Icon extends BaseTime {

  @Id
  @GeneratedValue
  @Column(name = "icon_id")
  private Long id;

  @Column
  private String originName;

  @Column
  private String storedName;

  @Column
  private String path;

  @OneToOne(mappedBy = "icon")
  private Document document;

}
