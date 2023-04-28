package athletetrainingprogram.controller;

import athletetrainingprogram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ModelAndView loadUsers() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("pages", userService.getPages());
        log.info("User page loaded");
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam Long id) {
        userService.deleteUser(id);
        log.info("User deleted: {}", id);
        return loadUsers();
    }
}
