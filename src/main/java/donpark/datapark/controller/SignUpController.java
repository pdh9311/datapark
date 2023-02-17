package donpark.datapark.controller;

import donpark.datapark.domain.Member;
import donpark.datapark.dto.SignUpForm;
import donpark.datapark.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignUpController {

  private final MemberService memberService;

  @GetMapping("/signup")
  public String signupForm(@ModelAttribute("signUpForm") SignUpForm signUpForm) {
    return "signup";
  }

  @PostMapping("/signup")
  public String signup(@ModelAttribute SignUpForm form, BindingResult bindingResult) {

    log.info("form={}", form);
    if (bindingResult.hasErrors()) {
      log.error("error??");
      return "signup";
    }
    // 아이디 중복 확인
    Optional<Member> dupLoginId = memberService.checkDuplicateLoginId(form.getLoginId());
    if (dupLoginId.isPresent()) {
      bindingResult.rejectValue("loginId", "existLoginId", null);
      return "signup";
    }
    // 비밀번호 == 비밀번호 확인
    if (!form.getLoginPw().equals(form.getLoginPwChk())) {
      bindingResult.rejectValue("loginPwChk", "passwordNotMatch", null);
      return "signup";
    }
    memberService.signup(form);
    return "redirect:/login";
  }

}
