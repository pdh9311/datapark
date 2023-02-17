package donpark.datapark.controller;

import donpark.datapark.constants.SessionConst;
import donpark.datapark.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  @GetMapping
  public String home(@SessionAttribute(name = SessionConst.LOGIN_INFO, required = false) MemberInfo memberInfo,
                     Model model) {
    log.info("memberInfo = {}", memberInfo);
    model.addAttribute("memberInfo", memberInfo);
    return "home";
  }

}
