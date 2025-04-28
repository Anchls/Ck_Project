package CloudBalance_Backend.Project.Confi;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@EnableJpaRepositories(
        basePackages = "CloudBalance_Backend.Project",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
@EntityScan("CloudBalance_Backend.Project.Entity")

public class MySqlConfig {

        private final String dbUrl = System.getenv("MYSQL_URL");
        private final String dbUsername = System.getenv("MYSQL_USERNAME");
        private final String dbPassword = System.getenv("MYSQL_PASSWORD");

//    private static final Logger logger = LoggerFactory.getLogger(MySqlConfig.class);

        @Primary
        @Bean(name = "mysqlDataSource")
        public DataSource mysqlDataSource() {

            log.info("MySQLJDBC URL: {}", dbUrl);

            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(dbUrl);
            dataSource.setUsername(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            return dataSource;
        }

        @Primary
        @Bean(name = "mysqlEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
                @Qualifier("mysqlDataSource") DataSource dataSource) {

            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setPackagesToScan("CloudBalance_Backend.Project.Entity");

            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);

            Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.put("hibernate.hbm2ddl.auto", "update");
            em.setJpaPropertyMap(properties);

            return em;
        }

        @Primary
        @Bean(name = "mysqlTransactionManager")
        public PlatformTransactionManager mysqlTransactionManager(
                @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
            return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryBean.getObject()));
        }

    }

