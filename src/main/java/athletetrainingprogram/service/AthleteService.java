package athletetrainingprogram.service;

import athletetrainingprogram.dto.request.CreateAthleteRequest;
import athletetrainingprogram.dto.request.UpdateAthleteRequest;
import athletetrainingprogram.dto.response.AthleteResponse;
import athletetrainingprogram.error.AthleteNotFoundException;
import athletetrainingprogram.model.Athlete;
import athletetrainingprogram.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static athletetrainingprogram.service.constant.PaginationConstant.PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final ModelMapper modelMapper;

    public void createAthlete(CreateAthleteRequest request) {
        Athlete athlete = modelMapper.map(request, Athlete.class);
        athleteRepository.save(athlete);
    }

    public List<AthleteResponse> getAllAthletes() {
        return athleteRepository.findAll()
                .stream()
                .map(athlete -> modelMapper.map(athlete, AthleteResponse.class))
                .collect(Collectors.toList());
    }

    public List<AthleteResponse> getAllAthletesByName(String name, Integer page, String sort) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by(sort));
        return athleteRepository.findAllByName("%" + name + "%", pageable)
                .stream()
                .map(athlete -> modelMapper.map(athlete, AthleteResponse.class))
                .collect(Collectors.toList());
    }

    public AthleteResponse getAthlete(Long id) {
        return athleteRepository.findById(id)
                .map(athlete -> modelMapper.map(athlete, AthleteResponse.class))
                .orElseThrow(() -> new AthleteNotFoundException("Athlete not found" + id));
    }

    public List<Long> getPages() {
        List<Long> pages = LongStream.rangeClosed(1, (athleteRepository.count() / PAGE_SIZE) + 1)
                .boxed()
                .collect(Collectors.toList());
        return pages;
    }

    public void updateAthlete(Long id, UpdateAthleteRequest request) {
        boolean isExist = existById(id);

        if (!isExist) {
            throw new AthleteNotFoundException("Athlete not found " + id);
        }

        Athlete athlete = modelMapper.map(request, Athlete.class);
        athlete.setId(id);

        athleteRepository.save(athlete);

    }

    public void deleteAthlete(Long id) {
        boolean isExist = existById(id);

        if (!isExist) {
            throw new AthleteNotFoundException("Athlete not found " + id);
        }

        athleteRepository.deleteById(id);
    }

    private boolean existById(Long id) {
        return athleteRepository.existsById(id);
    }

}
