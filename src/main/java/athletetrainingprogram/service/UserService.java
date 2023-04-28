package athletetrainingprogram.service;

import athletetrainingprogram.dto.UserDto;
import athletetrainingprogram.dto.request.SignUpRequest;
import athletetrainingprogram.dto.response.AthleteResponse;
import athletetrainingprogram.model.User;
import athletetrainingprogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static athletetrainingprogram.service.constant.PaginationConstant.PAGE_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        var user = modelMapper.map(request, User.class);
        user.addRole("ROLE_USER");
        userRepo.save(user);
    }

    public List<Long> getPages() {
        List<Long> pages = LongStream.rangeClosed(1, (userRepo.count() / PAGE_SIZE) + 1)
                .boxed()
                .collect(Collectors.toList());
        return pages;
    }

    public List<AthleteResponse> getAllAthletesByName(String name, Integer page, String sort) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, Sort.by(sort));
        return userRepo.findAllByUsername("%" + name + "%", pageable)
                .stream()
                .map(athlete -> modelMapper.map(athlete, AthleteResponse.class))
                .collect(Collectors.toList());
    }

    public List<UserDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(user -> {
                    var userDto = modelMapper.map(user, UserDto.class);
                    userDto.setRoles(user.getRoleList());
                    return userDto;
                })
                .toList();
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

}
