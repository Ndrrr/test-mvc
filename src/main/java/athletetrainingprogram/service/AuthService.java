package athletetrainingprogram.service;

import athletetrainingprogram.dto.request.SignUpRequest;
import athletetrainingprogram.model.User;
import athletetrainingprogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        var user = modelMapper.map(request, User.class);
        user.addRole("ROLE_USER");
        userRepo.save(user);
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

}
