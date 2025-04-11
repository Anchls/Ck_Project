package CloudBalance_Backend.Project.Controller.AccountControllers;

import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
//@CrossOrigin(origins = "*") // React frontend
public class Dashboard {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public String test(){
        return "Hell0";
    }

    @GetMapping("/user-management")
    @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<User>> getUserManagement() {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
    }

    @PostMapping("/onboarding")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> onboarding() {
        return ResponseEntity.ok("Onboarding Dashboard - Admin Only");
    }

    @PostMapping("/cost-explorer")
    @PreAuthorize("hasAnyRole('ADMIN', 'READ_ONLY', 'CUSTOMER')")
    public ResponseEntity<String> costExplorer() {
        return ResponseEntity.ok("Cost Explorer Dashboard - All Roles");
    }

    @PostMapping("/aws-services")
    @PreAuthorize("hasAnyRole('ADMIN', user-management'READ_ONLY', 'CUSTOMER')")
    public ResponseEntity<String> awsServices() {
        return ResponseEntity.ok("AWS Services Dashboard - All Roles");
    }
}
