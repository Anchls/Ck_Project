package CloudBalance_Backend.Project.Controller.AccountControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'READ_ONLY')")
    @PostMapping("/cost-explorer")
    public ResponseEntity<String> costExplorer() {
        return ResponseEntity.ok("Cost Explorer Content");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'READ_ONLY')")
    @PostMapping("/aws-services")
    public ResponseEntity<String> awsServices() {
        return ResponseEntity.ok("AWS Services Content");
    }
}

