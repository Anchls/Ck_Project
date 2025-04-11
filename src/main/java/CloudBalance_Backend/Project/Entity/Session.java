package CloudBalance_Backend.Project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


    @Entity
    @Table(name = "sessions")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public class Session{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String accessToken;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        @Column(nullable = false)
        private Boolean isValid;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;
    }
