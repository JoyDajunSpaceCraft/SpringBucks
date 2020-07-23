package userinfo.demo;

import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import userinfo.demo.model.*;
import userinfo.demo.repository.RewardRepoistory;
import userinfo.demo.repository.UserRepository;
import userinfo.demo.service.RewardService;
import userinfo.demo.service.UserService;

import javax.persistence.Cacheable;
import javax.transaction.Transactional;
import java.beans.Transient;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
@EnableTransactionManagement
@EnableAspectJAutoProxy
//@EnableCaching(proxyTargetClass = true)//基于AOP的实现缓存的机制
public class UserinfoApplication implements ApplicationRunner {
  @Autowired
  private UserService userService;

  @Autowired
  private RewardService rewardService;

  @Autowired
  private RewardRepoistory rewardRepoistory;

  @Autowired
  private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(UserinfoApplication.class, args);
  }

  /**
   * 如果遇到需要很多类型的缓存 建议使用 JSON序列化和反序列化 再利用 StringRedisTemplate 操作
   * 否则就要创建多个 RedisTemplate<,>
   * @param redisConnectionFactory
   * @return
   */
  @Bean
  public RedisTemplate<String, User>redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, User> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }

  @Bean
  public LettuceClientConfigurationBuilderCustomizer customizer() {
    return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {

    User u = userService.createUser("lilei", "12313", 123123L);
    Optional<User> lilie = userService.findOneUser("lilei");
    if (lilie.isPresent()) {
      log.info("user list is:{}", lilie);
    }
    for (int i = 0; i < 5; i++) {
      lilie = userService.findOneUser("lilei");
    }
    log.info("from redis{}", lilie);
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, 15);
    date = c.getTime();
    log.info("Now time is{}",date);//设置超时时间
    Reward saved = rewardService.saveReward(65, date, "Size Upgrade", u);
    log.info("Save reward success:{}", saved);


  }

}

