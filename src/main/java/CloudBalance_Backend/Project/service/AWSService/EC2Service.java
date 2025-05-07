package CloudBalance_Backend.Project.service.AWSService;

import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Exception.AccountNotFound;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.dto.AWS.EC2Instance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EC2Service {

    private final AssumeRoleService assumeRoleService;
    private final AccountRepository accountRepository;

    public List<EC2Instance> fetchInstances(String roleArn) {

        AwsSessionCredentials sessionCredentials = assumeRoleService.assumeRole(roleArn);

        try (Ec2Client ec2Client = Ec2Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                .build()) {

            DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
            DescribeInstancesResponse response = ec2Client.describeInstances(request);

            List<EC2Instance> instances = new ArrayList<>();
            for (Reservation reservation : response.reservations()) {
                for (Instance instance : reservation.instances()) {
                    String name = instance.tags().stream()
                            .filter(t -> t.key().equalsIgnoreCase("Name"))
                            .findFirst()
                            .map(Tag::value)
                            .orElse("N/A");

                    instances.add(new EC2Instance(
                            instance.instanceId(),
                            name,
                            instance.placement().availabilityZone(),
                            instance.state().nameAsString()
                    ));
                }
            }
            return instances;
        }
    }
}
