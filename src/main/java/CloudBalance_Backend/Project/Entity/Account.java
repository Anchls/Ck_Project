package CloudBalance_Backend.Project.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id", nullable = false, unique = true)
    private String accountId;
    @Column(name = "account_name", nullable = false)
    private String accountName;
    private String arn;


//    private String region;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
//
//    public void getRegion(String region) {
//    }
}
