package CloudBalance_Backend.Project.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;  // This will be encrypted with BCrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
