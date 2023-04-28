package athletetrainingprogram.controller;

import athletetrainingprogram.dto.request.UpdateAthleteRequest;
import athletetrainingprogram.service.AthleteService;
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
@RequestMapping("/athlete/edit")
@RequiredArgsConstructor
public class UpdateAthleteController {

    private final AthleteService athleteService;

    @GetMapping("/{id}")
    public ModelAndView loadUpdateAthlete(@PathVariable Long id) {
        var modelAndView = new ModelAndView("update-athlete");
        modelAndView.addObject("athlete", athleteService.getAthlete(id));
        log.error("Athlete page loaded with id: {}", id);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView updateAthlete(@ModelAttribute("athlete") UpdateAthleteRequest request,
                                      @RequestParam("id") Long id) {
        athleteService.updateAthlete(id, request);
        ModelAndView modelAndView = new ModelAndView("redirect:/athlete");
        log.info("Athlete updated: {}", request);
        return modelAndView;
    }

}