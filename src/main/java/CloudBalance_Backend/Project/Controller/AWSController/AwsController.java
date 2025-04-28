package CloudBalance_Backend.Project.Controller.AWSController;

import CloudBalance_Backend.Project.dto.AWS.ASGInstance;
import CloudBalance_Backend.Project.dto.AWS.EC2Instance;
import CloudBalance_Backend.Project.dto.AWS.RDSInstance;
import CloudBalance_Backend.Project.service.AWSService.ASGService;
import CloudBalance_Backend.Project.service.AWSService.EC2Service;
import CloudBalance_Backend.Project.service.AWSService.RDSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AwsController {

    private final EC2Service ec2Service;
    private final RDSService rdsService;
    private final ASGService asgService;

    @GetMapping("/ec2/instances")
    public ResponseEntity<List<EC2Instance>> getEc2Instances(@RequestParam String roleArn) {
        String accountId = extractAccountIdFromArn(roleArn);
        List<EC2Instance> instances = ec2Service.fetchInstances(accountId);
        return ResponseEntity.ok(instances);
    }

    @GetMapping("/rds/instances")
    public ResponseEntity<List<RDSInstance>> getRdsInstances(@RequestParam String roleArn) {
        String accountId = extractAccountIdFromArn(roleArn);
        List<RDSInstance> instances = rdsService.fetchRDSInstances(accountId);
        return ResponseEntity.ok(instances);
    }

    @GetMapping("/asg/instances")
    public ResponseEntity<List<ASGInstance>> getAsgInstances(@RequestParam String roleArn) {
        String accountId = extractAccountIdFromArn(roleArn);
        List<ASGInstance> instances = asgService.fetchASGInstances(accountId);
        return ResponseEntity.ok(instances);
    }

    // ✅ Utility method to extract accountId from ARN
    private String extractAccountIdFromArn(String arn) {
        // Example ARN: arn:aws:iam::054062525424:role/your-role-name
        String[] parts = arn.split(":");
        if (parts.length > 4) {
            return parts[4];
        } else {
            throw new IllegalArgumentException("Invalid ARN format: " + arn);
        }
    }
}
