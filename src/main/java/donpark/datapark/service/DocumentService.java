package donpark.datapark.service;

import donpark.datapark.domain.Document;
import donpark.datapark.domain.Icon;
import donpark.datapark.domain.Member;
import donpark.datapark.dto.DocumentDto;
import donpark.datapark.dto.WriteForm;
import donpark.datapark.repository.DocumentRepository;
import donpark.datapark.repository.IconRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

  private final DocumentRepository documentRepository;
  private final IconRepository iconRepository;

  public void write(WriteForm form, Member member) {
    // DONE: 아이콘 이미지 추가
    try {
      MultipartFile file = form.getFile();
      Icon icon = null;
      if (!StringUtils.isEmptyOrWhitespace(file.getOriginalFilename())) {
        icon = Icon.of(file, member.getLoginId());
        file.transferTo(new File(icon.getPath(), icon.getStoredName()));
        iconRepository.save(icon);
      }
      Document document = Document.of(form, member, icon);
      documentRepository.save(document);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public Page<DocumentDto> paging(Pageable pageable) {
    PageRequest pageRequest = PageRequest.of(
        pageable.getPageNumber() - 1,
        pageable.getPageSize(),
        Sort.Direction.ASC,
        "id");

    return documentRepository.findAll(pageRequest)
        .map(d -> DocumentDto.of(d));
  }

}
