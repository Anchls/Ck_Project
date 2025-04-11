package CloudBalance_Backend.Project.Controller.AccountControllers;

import CloudBalance_Backend.Project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
@Autowired
    AdminService adminService;

    @PreAuthorize("hasRole('ADMIN', 'READ_ONLY')")
    @GetMapping("/user-management")
    public ResponseEntity<String> userManagement() {
        return ResponseEntity.ok(adminService.getUserManagementContent());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/onboarding")
    public ResponseEntity<String> onboarding() {
        return ResponseEntity.ok(adminService.getUserManagementContent());
    }
}

