package athletetrainingprogram.controller;

import athletetrainingprogram.dto.request.UpdateUserRequest;
import athletetrainingprogram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user/edit")
@RequiredArgsConstructor
public class UpdateUserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ModelAndView loadUpdateAthlete(@PathVariable Long id) {
        var modelAndView = new ModelAndView("update-user");
        modelAndView.addObject("user", userService.getUser(id));
        log.error("User update page loaded with id: {}", id);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView updateUser(@ModelAttribute("user") UpdateUserRequest request,
                                   @RequestParam("id") Long id) {
        userService.updateUser(id, request);
        ModelAndView modelAndView = new ModelAndView("redirect:/user");
        log.info("User updated: {}", request);
        return modelAndView;
    }

}