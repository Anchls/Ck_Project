package CloudBalance_Backend.Project.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_request")
@Data
public class UserRequestEntity {

    private String role;
    @Id
    private String email;
    private String username;
    private String password;


}
