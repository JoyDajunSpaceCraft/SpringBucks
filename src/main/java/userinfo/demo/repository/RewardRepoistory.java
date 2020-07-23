package userinfo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import userinfo.demo.model.Reward;

import java.util.List;

/**
 * public interface UserRepository extends JpaRepository<T, ID>,
 *
 * 根据上述描述：T 需要类型化为实体类(Entity)User，ID需要实体类User中Id（定义的Id类型是Long）的类型
 */

public interface RewardRepoistory extends JpaRepository<Reward, Long> {
  List<Reward> findRewardsByUserUsername(String username);
//  Reward findBy(Long id);
  Reward findByRewardName(String name);
  List<Reward> findByRewardNameInOrderById(List<String> rewardnames);
}
