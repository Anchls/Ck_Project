package CloudBalance_Backend.Project.service.AWSService;

import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Exception.AccountNotFound;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.dto.AWS.RDSInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RDSService {

    private final AssumeRoleService assumeRoleService;
    private final AccountRepository accountRepository;

    public List<RDSInstance> fetchRDSInstances(String accountId) {
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AccountNotFound("Account not found"));

        AwsSessionCredentials sessionCredentials = assumeRoleService.assumeRole(account.getArn());
        RdsClient rdsClient = RdsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                .build();

        // Build the request
        DescribeDbInstancesRequest request = DescribeDbInstancesRequest.builder().build();

        // Fetch the response
        DescribeDbInstancesResponse response = rdsClient.describeDBInstances(request);

        // Process the response
        List<RDSInstance> rdsInstances = new ArrayList<>();
        for (DBInstance dbInstance : response.dbInstances()) {
            RDSInstance rds = new RDSInstance();
            rds.setResourceId(System.nanoTime()); // or use dbInstance.dbInstanceIdentifier().hashCode()
            rds.setResourceName(dbInstance.dbInstanceIdentifier());
            rds.setEngine(dbInstance.engine());
            rds.setRegion(dbInstance.availabilityZone());
            rds.setStatus(dbInstance.dbInstanceStatus());
            rdsInstances.add(rds);
        }

        rdsClient.close();
        return rdsInstances;
    }
}