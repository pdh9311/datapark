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
public class DocumentDto {

  private Long id;
  private String title;
  private LocalDateTime updatedTime;

  public static DocumentDto of(Document document) {
    return DocumentDto.builder()
        .id(document.getId())
        .title(document.getTitle())
        .updatedTime(document.getUpdatedTime())
        .build();
  }
}
