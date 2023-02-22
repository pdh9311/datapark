package donpark.datapark.dto;

import donpark.datapark.domain.Document;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class DocReadDto {
  private Long id;
  private String icon;
  private String title;
  private String content;
  private LocalDateTime updatedTime;

  public static DocReadDto of(Document document) {
    if (document.getIcon().getPath() != null) {
      return DocReadDto.builder()
          .id(document.getId())
          .icon(document.getIcon().getPath())
          .title(document.getTitle())
          .content(document.getContent())
          .updatedTime(document.getUpdatedTime())
          .build();
    }
    return DocReadDto.builder()
        .id(document.getId())
        .icon("/icons/page.svg")
        .title(document.getTitle())
        .content(document.getContent())
        .updatedTime(document.getUpdatedTime())
        .build();
  }

}
