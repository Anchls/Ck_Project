package CloudBalance_Backend.Project.service;

import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
@Autowired
    UserRepository userRepository;

@Override
@Transactional
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);
//            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
    System.out.println("i am in userdetail");
    return UserDetailsImpl.build(user.get());
}

}