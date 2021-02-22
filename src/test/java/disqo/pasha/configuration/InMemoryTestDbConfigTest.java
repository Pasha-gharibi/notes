package disqo.pasha.configuration;

import disqo.pasha.domain.User;
import disqo.pasha.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InMemoryTestDbConfig.class})
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
public class InMemoryTestDbConfigTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    public void contextLoads() {
        userRepo.save(new User("pasha@gmail.com","pasha")
                .createTime(LocalDate.now())
                .lastUpdateTime(LocalDate.now())
        );
        Assert.assertTrue(userRepo.findById(1l) != null);
    }

}
