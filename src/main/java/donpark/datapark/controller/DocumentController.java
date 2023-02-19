package donpark.datapark.controller;

import donpark.datapark.constants.SessionConst;
import donpark.datapark.domain.Member;
import donpark.datapark.dto.DocumentDto;
import donpark.datapark.dto.MemberInfo;
import donpark.datapark.dto.WriteForm;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;
  private final MemberService memberService;

  @GetMapping("/docs")
  public String docsList(Model model,
                         @PageableDefault(page = 1) Pageable pageable) {
    Page<DocumentDto> list = documentService.paging(pageable);
    Paging paging = Paging.builder()
        .total(list.getTotalPages())
        .curr(list.getPageable().getPageNumber() + 1)
        .build();
    log.info("paging={}", paging);
    model.addAttribute("docs", list);
    model.addAttribute("pg", paging);
    return "docs";
  }

  @GetMapping("/write")
  public String writeForm() {
    return "write";
  }

  @PostMapping("/write")
  public String write(WriteForm form,
                      @SessionAttribute(name = SessionConst.LOGIN_INFO, required = false) MemberInfo memberInfo) {
    Member member = memberService.findById(memberInfo.getId());
    documentService.write(form, member);
    return "redirect:/docs";
  }

}
