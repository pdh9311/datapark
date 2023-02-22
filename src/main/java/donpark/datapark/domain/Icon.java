package donpark.datapark.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

  public static Icon of(String originalFilename , String loginId) {
    StringBuilder storedFilename = new StringBuilder();
    storedFilename
        .append(System.currentTimeMillis())
        .append("_")
        .append(originalFilename);

    StringBuilder iconPath = new StringBuilder();
    iconPath.append("/icons/")
        .append(loginId)
        .append("/")
        .append(storedFilename);

    return Icon.builder()
        .originName(originalFilename)
        .storedName(storedFilename.toString())
        .path(iconPath.toString())
        .build();
  }
}
