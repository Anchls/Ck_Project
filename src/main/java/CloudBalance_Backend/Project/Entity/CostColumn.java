package CloudBalance_Backend.Project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CostColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "column_name")
    private String columnName;

}
