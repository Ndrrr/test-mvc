package athletetrainingprogram.controller;

import athletetrainingprogram.dto.request.CreateAthleteRequest;
import athletetrainingprogram.service.AthleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/athlete")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService athleteService;

    @GetMapping
    public ModelAndView loadAthlete(Model model) {
        model.addAttribute("athlete", new CreateAthleteRequest());
        ModelAndView modelAndView = new ModelAndView("athlete");
        modelAndView.addObject("athletes", athleteService.getAllAthletes());
        modelAndView.addObject("pages", athleteService.getPages());
        log.info("Athlete page loaded");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchAthlete(Model model, @RequestParam(value = "keyword", defaultValue = "") String name,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "sort", defaultValue = "id") String sort) {
        model.addAttribute("athlete", new CreateAthleteRequest());
        ModelAndView modelAndView = new ModelAndView("athlete");
        modelAndView.addObject("athletes", athleteService.getAllAthletesByName(name, page, sort));
        modelAndView.addObject("pages", athleteService.getPages());
        log.info("Athletes are searched by name: {}, page: {}, sort: {}", name, page, sort);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView addAthlete(@ModelAttribute("athlete") CreateAthleteRequest request, Model model) {
        athleteService.createAthlete(request);
        log.info("Athlete created: {}", request);
        return loadAthlete(model);
    }

    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam Long id, Model model) {
        athleteService.deleteAthlete(id);
        log.info("Athlete deleted: {}", id);
        return loadAthlete(model);
    }

}
