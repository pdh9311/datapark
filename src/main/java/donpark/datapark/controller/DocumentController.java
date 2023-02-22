package donpark.datapark.controller;

import donpark.datapark.constants.SessionConst;
import donpark.datapark.domain.Member;
import donpark.datapark.dto.*;
import donpark.datapark.service.DocumentService;
import donpark.datapark.service.MemberService;
import donpark.datapark.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;
  private final MemberService memberService;

  @GetMapping("/docs")
  public String docsList(Model model,
                         @PageableDefault(page = 1) Pageable pageable) {
    Page<DocsDto> list = documentService.paging(pageable);
    Paging paging = Paging.builder()
        .total(list.getTotalPages())
        .curr(list.getPageable().getPageNumber() + 1)
        .build();
    log.info("paging={}", paging);

    model.addAttribute("docs", list);
    model.addAttribute("pg", paging);
    return "document/docs";
  }

  @GetMapping("/write")
  public String writeForm() {
    return "document/write";
  }

  @PostMapping("/write")
  public String write(WriteForm form,
                      @SessionAttribute(name = SessionConst.LOGIN_INFO, required = false) MemberInfo memberInfo) {
    Member member = memberService.findById(memberInfo.getId());
    documentService.write(form, member);
    return "redirect:/docs";
  }

  @GetMapping("/docs/{id}")
  public String readForm(@PathVariable("id") Long id, Model model) {
    DocReadDto document = documentService.getDocument(id);
    model.addAttribute("document", document);
    log.info("document = {}", document);
    return "document/read";
  }

  @DeleteMapping("/docs/{id}")
  public String deleteDocument(@PathVariable Long id) {
    documentService.delete(id);
    return "redirect:/docs";
  }

  @GetMapping("/docs/{id}/update")
  public String updateForm(@PathVariable Long id, Model model) {
    DocReadDto doc = documentService.getDocument(id);
    model.addAttribute("doc", doc);
    return "document/update";
  }

  @PutMapping("/docs/{id}")
  public String update(@PathVariable Long id, DocUpdateForm form) {
    documentService.update(id, form.getTitle(), form.getContent());
    return "redirect:/docs/" + id;
  }

}
