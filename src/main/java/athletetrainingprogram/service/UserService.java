package athletetrainingprogram.service;

import athletetrainingprogram.dto.UserDto;
import athletetrainingprogram.dto.request.SignUpRequest;
import athletetrainingprogram.dto.request.UpdateUserRequest;
import athletetrainingprogram.model.User;
import athletetrainingprogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    public List<UserDto> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    public UserDto getUser(Long id) {
        return userRepo.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow();
    }

    public void updateUser(Long id, UpdateUserRequest user) {
        var dbUser = userRepo.findById(id).orElseThrow();
        dbUser.setRoles(user.getRoles());
        dbUser.setAuthorities(List.of(user.getRoles().split(",")));
        userRepo.save(dbUser);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

}
