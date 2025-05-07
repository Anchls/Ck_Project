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
        List<EC2Instance> instances = ec2Service.fetchInstances(roleArn);
        return ResponseEntity.ok(instances);
    }

    @GetMapping("/rds/instances")
    public ResponseEntity<List<RDSInstance>> getRdsInstances(@RequestParam String roleArn) {
        List<RDSInstance> instances = rdsService.fetchRDSInstances(roleArn);
        return ResponseEntity.ok(instances);
    }

    @GetMapping("/asg/instances")
    public ResponseEntity<List<ASGInstance>> getAsgInstances(@RequestParam String roleArn) {
        List<ASGInstance> instances = asgService.fetchASGInstances(roleArn);
        return ResponseEntity.ok(instances);
    }

}
