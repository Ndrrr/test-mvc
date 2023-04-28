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

    @GetMapping("/search")
    public ModelAndView searchUser(@RequestParam(value = "keyword", defaultValue = "") String name,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "sort", defaultValue = "id") String sort) {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getAllAthletesByName(name, page, sort));
        modelAndView.addObject("pages", userService.getPages());
        log.info("Users are searched by name: {}, page: {}, sort: {}", name, page, sort);
        return modelAndView;
    }
}
