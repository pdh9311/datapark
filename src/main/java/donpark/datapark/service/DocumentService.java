package donpark.datapark.service;

import donpark.datapark.domain.Document;
import donpark.datapark.domain.Icon;
import donpark.datapark.domain.Member;
import donpark.datapark.dto.DocReadDto;
import donpark.datapark.dto.DocsDto;
import donpark.datapark.dto.WriteForm;
import donpark.datapark.repository.DocumentRepository;
import donpark.datapark.repository.IconRepository;
import donpark.datapark.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

  private final DocumentRepository documentRepository;
  private final IconRepository iconRepository;
  private final MemberRepository memberRepository;

  public void write(WriteForm form, Member member) {
    // DONE: 아이콘 이미지 추가
    MultipartFile file = form.getFile();
    Icon icon;
    if (!StringUtils.isEmptyOrWhitespace(file.getOriginalFilename())) {
      icon = Icon.of(file.getOriginalFilename(), member.getLoginId());
      saveIconFile(member, file, icon);
      iconRepository.save(icon);
    } else {
      icon = iconRepository.findByStoredName("page.svg").get();
    }
    Document document = Document.of(form, member, icon);
    documentRepository.save(document);
  }

  private static void saveIconFile(Member member, MultipartFile file, Icon icon) {
    try {
      String projectPath = System.getProperty("user.dir");

      StringBuilder savedPath = new StringBuilder();
      savedPath.append(projectPath)
          .append(File.separator).append("src")
          .append(File.separator).append("main")
          .append(File.separator).append("resources")
          .append(File.separator).append("static")
          .append(File.separator).append("icons")
          .append(File.separator).append(member.getLoginId());
      Files.createDirectories(Paths.get(savedPath.toString()));

      file.transferTo(new File(savedPath.toString(), icon.getStoredName()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Page<DocsDto> paging(Pageable pageable) {
    PageRequest pageRequest = PageRequest.of(
        pageable.getPageNumber() - 1,
        pageable.getPageSize(),
        Sort.Direction.ASC,
        "id");

    return documentRepository.findAll(pageRequest)
        .map(d -> DocsDto.of(d));
  }

  public DocReadDto getDocument(Long id) {
    return documentRepository.findById(id)
        .map(d -> DocReadDto.of(d))
        .orElse(null);
  }

  public void delete(Long id) {
    documentRepository.deleteById(id);
  }

  public void update(Long id, String title, String content) {
    Document document = documentRepository.findById(id).get();
    document.update(title, content);
  }
}
