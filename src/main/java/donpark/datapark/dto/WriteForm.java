package donpark.datapark.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class WriteForm {

  private MultipartFile file;
  private String title;
  private String content;

}
