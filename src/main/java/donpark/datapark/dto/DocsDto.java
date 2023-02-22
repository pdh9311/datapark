package donpark.datapark.dto;

import donpark.datapark.domain.Document;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DocsDto {

  private Long id;
  private String icon;
  private String title;
  private LocalDateTime updatedTime;

  public static DocsDto of(Document document) {
    return DocsDto.builder()
        .id(document.getId())
        .icon(document.getIcon().getPath())
        .title(document.getTitle())
        .updatedTime(document.getUpdatedTime())
        .build();
  }
}
