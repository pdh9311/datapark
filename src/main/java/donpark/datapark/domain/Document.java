package donpark.datapark.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "icon_id")
  private Icon icon;

}
