package donpark.datapark.controller;

import donpark.datapark.constants.SessionConst;
import donpark.datapark.dto.LoginForm;
import donpark.datapark.dto.MemberInfo;
import donpark.datapark.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberService memberService;

  @GetMapping("/login")
  public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
    return "login";
  }

  @PostMapping("/login")
  public String login(@ModelAttribute LoginForm form, BindingResult bindingResult,
                      @RequestParam(defaultValue = "/") String redirectURI,
                      HttpSession session) {
    if (bindingResult.hasErrors()) {
      return "login";
    }
    MemberInfo loginResult = memberService.login(form);
    if (loginResult == null) {
      bindingResult.reject("loginFail", null);
      return "login";
    }
    // DONE: 로그인 성공 처리
    session.setAttribute(SessionConst.LOGIN_INFO, loginResult);
    return "redirect:" + redirectURI;
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }

}
