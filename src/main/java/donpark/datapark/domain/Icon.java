package donpark.datapark.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
  private String path;

  @Column
  private String storedName;

  @Column
  private String originName;

  public static Icon of(MultipartFile file, String loginId) {
    try {

      String projectPath = System.getProperty("user.dir");

      StringBuilder savedPath = new StringBuilder();
      savedPath.append(projectPath)
          .append(File.separator).append("src")
          .append(File.separator).append("main")
          .append(File.separator).append("resources")
          .append(File.separator).append("static")
          .append(File.separator).append("icons")
          .append(File.separator).append(loginId);

      Files.createDirectories(Paths.get(savedPath.toString()));

      String originalFilename = file.getOriginalFilename();

      StringBuilder storedFilename = new StringBuilder();
      storedFilename
          .append(System.currentTimeMillis())
          .append("_")
          .append(originalFilename);

      return Icon.builder()
          .path(savedPath.toString())
          .originName(originalFilename)
          .storedName(storedFilename.toString())
          .build();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
