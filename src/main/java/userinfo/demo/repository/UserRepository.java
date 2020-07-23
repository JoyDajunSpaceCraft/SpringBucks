package userinfo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import userinfo.demo.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
  User findByUsername(String username);
  void deleteByUsername(String username);
  List<User> findByUsernameInOrderById(List<String> usernames);
}
