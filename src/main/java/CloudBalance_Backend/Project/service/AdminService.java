package CloudBalance_Backend.Project.service;

import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    public String getUserManagementContent() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return "No users found.";
        }

        return users.stream()
                .map(user -> "ID: " + user.getId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole())
                .collect(Collectors.joining("\n"));
    }


    public String getOnboardingContent() {
        return "Onboarding Content from Service Layer";
    }
}
