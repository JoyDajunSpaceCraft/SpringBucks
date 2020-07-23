package userinfo.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import userinfo.demo.model.Reward;
import userinfo.demo.model.RewardState;
import userinfo.demo.model.User;
import userinfo.demo.repository.RewardRepoistory;
import userinfo.demo.repository.UserRepository;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
//@CacheConfig(cacheNames = "reward")
public class RewardService {
  @Autowired
  private RewardRepoistory rewardRepoistory;
  @Autowired
  private UserRepository userRepository;
  public Optional<Reward> findReward(String rewardName){
    ExampleMatcher matcher = ExampleMatcher.matching()
      .withIgnoreCase("rewardName");
    Optional<Reward> reward = rewardRepoistory.findOne(
      Example.of(Reward.builder().rewardName(rewardName).build(), matcher));
      log.info("reword found{}", reward);
      return reward;
  }

  /**
   * 根据用户名找到优惠劵
   * @param userName
   * @return
   */
  public List<Reward> findRewardsByUserName(String userName){
    return rewardRepoistory.findRewardsByUserUsername(userName);
  }

  public Date setDefaultExpiryDate(){
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, 15);
    date = c.getTime();
    return date;
  }

  public Date setExpiryDate(Integer time){
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, time);
    date = c.getTime();
    return date;
  }


  /**
   * TODO 创建了新的User类型 但是不知道怎么传入值 而且传入参数太多 怎样优化
   * 这里需要传入user的整个实例 ？
   * @param rewardName
   * @param expiryDate
   * @param discount
   * @param user
   * @return
   */
  public Reward saveReward(int discount, Date expiryDate, String rewardName, User user){
    return rewardRepoistory.save(Reward.builder()
      .discount(discount)
      .expiryDate(expiryDate)
      .rewardName(rewardName)
      .rewardState(RewardState.NOTUSED)
      .user(user).build());
  }
  public boolean updateState(Reward reward, RewardState state){
    if(state.compareTo(reward.getRewardState())<=0){
      log.warn("Reward is outof date!");
      return false;
    }
    reward.setRewardState(state);
    rewardRepoistory.save(reward);
    log.info("Save reward :{}", reward);
    return true;
  }

  @Cacheable
  public Reward getReward(String rewardName){
    return rewardRepoistory.findByRewardName(rewardName);
  }

  @CacheEvict
  public void reloadReward(){}

  public Reward getReward(Long id){
    return rewardRepoistory.getOne(id);
  }

  public long getRewardCount(){
    return rewardRepoistory.count();
  }
  public List<Reward> getRewardByRewardName(List<String> rewardnames){
    return rewardRepoistory.findByRewardNameInOrderById(rewardnames);
  }



}

