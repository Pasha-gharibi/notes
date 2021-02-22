package disqo.pasha.configuration;


import de.flapdoodle.embed.process.runtime.Network;
import disqo.pasha.domain.User;
import disqo.pasha.repository.UserRepository;
import org.hibernate.cfg.AvailableSettings;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;
import static java.lang.String.format;

@Configuration
@EnableTransactionManagement
public class InMemoryTestDbConfig {
    @Bean
    @DependsOn("postgresProcess")
    public DataSource dataSource(PostgresConfig config) {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(format("jdbc:postgresql://%s:%s/%s", config.net().host(), config.net().port(), config.storage().dbName()));
        ds.setUsername(config.credentials().username());
        ds.setPassword(config.credentials().password());
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(dataSource);
        lcemfb.setPackagesToScan("disqo.pasha.domain");
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        lcemfb.setJpaVendorAdapter(va);
        lcemfb.setJpaProperties(getHibernateProperties());
        lcemfb.afterPropertiesSet();
        return lcemfb;

    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties getHibernateProperties() {
        Properties ps = new Properties();
        ps.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        ps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        ps.put("hibernate.hbm2ddl.auto", "update");
        ps.put("hibernate.connection.characterEncoding", "UTF-8");
        ps.put("hibernate.connection.charSet", "UTF-8");
        ps.put(AvailableSettings.FORMAT_SQL, "true");
        ps.put(AvailableSettings.SHOW_SQL, "true");
        return ps;

    }

    @Bean
    public PostgresConfig postgresConfig() throws IOException {
        final PostgresConfig postgresConfig = new PostgresConfig(Version.V9_6_8,
                new AbstractPostgresConfig.Net("localhost", Network.getFreeServerPort()),
                new AbstractPostgresConfig.Storage("notes"),
                new AbstractPostgresConfig.Timeout(),
                new AbstractPostgresConfig.Credentials("user", "pass")
        );
        return postgresConfig;
    }

    @Bean(destroyMethod = "stop")
    public PostgresProcess postgresProcess(PostgresConfig config) throws IOException {
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();
        PostgresExecutable exec = runtime.prepare(config);
        PostgresProcess process = exec.start();
        return process;
    }



    @Bean
    public DataSourceInitializationMode dataSourceInitializationMode(){
        return DataSourceInitializationMode.ALWAYS;
    }


}
