package athletetrainingprogram.controller;

import athletetrainingprogram.dto.request.SignUpRequest;
import athletetrainingprogram.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;

    @GetMapping
    public ModelAndView showSignUp(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return new ModelAndView("signup");
    }

    @PostMapping
    public String register(@ModelAttribute SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        log.info("User registered: {}", signUpRequest.getUsername());
        return "redirect:/login";
    }

}
