package donpark.datapark.controller;

import donpark.datapark.constants.SessionConst;
import donpark.datapark.dto.*;
import donpark.datapark.service.DocumentService;
import donpark.datapark.service.MemberService;
import donpark.datapark.util.Paging;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.UUID;

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

  /*@PostMapping("/writeV1")
  public String write(WriteForm form,
                      @SessionAttribute(name = SessionConst.LOGIN_INFO, required = false) MemberInfo memberInfo) {
    Member member = memberService.findById(memberInfo.getId());
    documentService.write(form, member);
    return "redirect:/docs";
  }*/

  @PostMapping("/write")
  public String write(WriteForm form,
                      @SessionAttribute(name = SessionConst.LOGIN_INFO, required = false) MemberInfo memberInfo) {

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

  @GetMapping("/toast")
  public String toastForm() {
    return "document/toast-write";
  }

  @ResponseBody
  @PostMapping("/toast")
  public String toast(@RequestBody Test test) {

    log.info("content={}", test.getContent());
    String projectPath = System.getProperty("user.dir");
    StringBuilder filePath = new StringBuilder();
    filePath.append(projectPath)
        .append(File.separator).append("src")
        .append(File.separator).append("main")
        .append(File.separator).append("resources")
        .append(File.separator).append("static")
        .append(File.separator).append("docs");

    String filename = UUID.randomUUID().toString() + "_document.md";
    try {
      File file = new File(filePath.toString(), filename);
      if (!file.exists()) {
        file.createNewFile();
      }
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(test.content);
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
      return "bad";
    }
    return filename;
  }

  @GetMapping("/toast/read")
  public String toastReadForm(@RequestParam(required = false) String document, Model model) {
    log.info("document={}", document);
    String projectPath = System.getProperty("user.dir");
    StringBuilder sb = new StringBuilder();
    sb.append(projectPath)
        .append(File.separator).append("src")
        .append(File.separator).append("main")
        .append(File.separator).append("resources")
        .append(File.separator).append("static")
        .append(File.separator).append("docs")
        .append(File.separator).append(document);

    StringBuilder content = new StringBuilder();
    try {
      File file = new File(sb.toString());

      BufferedReader br = new BufferedReader(new FileReader(file));
      String str;
      while ((str = br.readLine()) != null) {
        content.append(str).append('\n');
      }
      model.addAttribute("content", content);
    } catch (IOException e) {
      e.printStackTrace();
      return "redirect:/";
    }


    return "document/toast-read";
  }

  @Data
  static class Test {
    private String content;
  }
}

