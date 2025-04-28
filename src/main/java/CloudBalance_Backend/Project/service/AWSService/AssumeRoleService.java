package CloudBalance_Backend.Project.service.AWSService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.StsException;

@Service
@AllArgsConstructor
public class AssumeRoleService {
    private final StsClient stsClient;

    public AwsSessionCredentials assumeRole(String arn) {

        try {
            AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
                    .roleArn(arn)
                    .roleSessionName("session-" + System.currentTimeMillis())
                    .durationSeconds(3600)
                    .build();

            AssumeRoleResponse assumeRoleResponse = stsClient.assumeRole(roleRequest);
            return AwsSessionCredentials.create(
                    assumeRoleResponse.credentials().accessKeyId(),
                    assumeRoleResponse.credentials().secretAccessKey(),
                    assumeRoleResponse.credentials().sessionToken()
            );
        } catch (StsException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to assume role: " + e.awsErrorDetails().errorMessage());
        }
    }
    }

