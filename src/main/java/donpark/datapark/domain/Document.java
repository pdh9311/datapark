package donpark.datapark.domain;

import donpark.datapark.dto.WriteForm;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Document extends BaseTime {

  @Id
  @GeneratedValue
  @Column(name = "document_id")
  private Long id;

  @Column
  private String title;

  @Column
  private String content;

  @Column
  private Long hits;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "icon_id")
  private Icon icon;

  public static Document of(WriteForm writeForm, Member member, Icon icon) {
    return Document.builder()
        .title(writeForm.getTitle())
        .content(writeForm.getContent())
        .hits(0L)
        .member(member)
        .icon(icon)
        .build();
  }

}
