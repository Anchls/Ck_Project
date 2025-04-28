package CloudBalance_Backend.Project.Entity;

import net.snowflake.client.jdbc.internal.google.apps.card.v1.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public enum Role{
    ADMIN,
    READ_ONLY,
    CUSTOMER;

    @Repository
    public static interface ColumnRepository extends JpaRepository<Columns, Long> {
        Optional<Columns> findByDisplayName(String displayName);
    }
}

