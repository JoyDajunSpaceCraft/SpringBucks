package userinfo.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.stereotype.Service;
import userinfo.demo.model.LoginState;
import userinfo.demo.model.Reward;
import userinfo.demo.model.User;
import userinfo.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserService {
  private static final String CACHE = "springbucks-user";
  @Autowired
  private UserRepository userRepository;
  public User createUser(String username, String password, Long phone){
    User user = User.builder()
                  .phone(phone).username(username).password(password).state(LoginState.LOGIN).build();
    User saved = userRepository.save(user);
    log.info("New User:{}", saved);
    return saved;
  }

  public Boolean updateState(User user, LoginState state){
    if(state.compareTo(user.getState())<=0){
      log.warn("Login State can not turn to UnLogin State");
      return false;
    }
    user.setState(state);
    userRepository.save(user);
    log.info("Save user:{}", user);
    return true;
  }

  @Cacheable
  public List<User> findAllUser(){
    return userRepository.findAll();
  }

  @CacheEvict
  public void reloadUser(){}

  public User getUser(String userName){
    return userRepository.findByUsername(userName);
  }

  public User getUser(Long id){
    return userRepository.getOne(id);
  }

  public long getUserCount(){
    return userRepository.count();
  }
  public User getUserByUsername(String usernames){
    return userRepository.findByUsername(usernames);
  }


  @Autowired
  private RedisTemplate<String, User> redisTemplate;//在主程序中配置需要类型的redisTemplate Bean
  /**
   * 为什么用 Optional因为可以实现 判断是否存在.isPresent()方法 防止空指针异常
   * 后面加入了redis 操作
   * 模糊查询 用户名 忽略大小写
   * @param username
   * @return
   */
  public Optional<User> findOneUser(String username) {//TODO 做序列化和反序列化 StringRedisTemplate!https://www.cnblogs.com/wangzhuxing/p/10198347.html#_label0_0
    // TODO 了解一下cache和redistemplate的封装区别
    //判断缓存中有没有 user
    HashOperations<String, String, User> hashOperations = redisTemplate.opsForHash();
    if (redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, username)) {
      log.info("Get user {} from redis",username);
      return Optional.of(hashOperations.get(CACHE,username));
    }


    ExampleMatcher matcher = ExampleMatcher.matching()
      .withIgnoreCase("username");
    Optional<User> user = userRepository.findOne(
      Example.of(User.builder().username(username).build(), matcher));
    log.info("User Found: {}", user);

    //如果存在user就将其加入缓存中
    if(user.isPresent()){
      log.info("Put user{} into redis cache",username);
      hashOperations.put(CACHE,username, user.get());
      redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);//设置缓存失效时间
    }

    return user;
  }


}
