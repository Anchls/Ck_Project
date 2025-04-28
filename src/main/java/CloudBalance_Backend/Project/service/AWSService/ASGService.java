package CloudBalance_Backend.Project.service.AWSService;

import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Exception.AccountNotFound;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.dto.AWS.ASGInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;
import software.amazon.awssdk.services.autoscaling.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ASGService {

       private final AssumeRoleService assumeRoleService;
       private final AccountRepository accountRepository;

       public List<ASGInstance> fetchASGInstances(String accountId) {
              Account account = accountRepository.findByAccountId(accountId)
                      .orElseThrow(() -> new AccountNotFound("Account not found"));

              AwsSessionCredentials sessionCredentials = assumeRoleService.assumeRole(account.getArn());

              try (AutoScalingClient autoScalingClient = AutoScalingClient.builder()
                      .region(Region.US_EAST_1)
                      .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                      .build()) {

                     DescribeAutoScalingGroupsRequest request = DescribeAutoScalingGroupsRequest.builder().build();
                     DescribeAutoScalingGroupsResponse response = autoScalingClient.describeAutoScalingGroups(request);

                     List<ASGInstance> asgInstances = new ArrayList<>();

                     for (AutoScalingGroup group : response.autoScalingGroups()) {
                            String zone = group.availabilityZones().isEmpty() ? "N/A" : group.availabilityZones().get(0);

                            for (Instance instance : group.instances()) {
                                   ASGInstance asgInstance = new ASGInstance();
                                   asgInstance.setResourceId(instance.instanceId());
                                   asgInstance.setResourceName(group.autoScalingGroupName());
                                   asgInstance.setRegion(zone);
                                   asgInstance.setDesiredCapacity(String.valueOf(group.desiredCapacity()));
                                   asgInstance.setMinSize(String.valueOf(group.minSize()));
                                   asgInstance.setMaxSize(String.valueOf(group.maxSize()));
                                   asgInstance.setStatus(instance.lifecycleState().toString());
                                   asgInstances.add(asgInstance);
                            }
                     }
                     return asgInstances;
              }
       }
}
