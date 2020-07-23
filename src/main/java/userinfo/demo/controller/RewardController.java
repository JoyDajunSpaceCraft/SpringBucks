package userinfo.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import userinfo.demo.controller.request.NewRewardRequest;
import userinfo.demo.model.Reward;
import userinfo.demo.model.User;
import userinfo.demo.service.RewardService;
import userinfo.demo.service.UserService;

import java.awt.*;
import java.util.Date;

@RestController
@RequestMapping("/reward")
@Slf4j
public class RewardController {
  @Autowired
  private RewardService rewardService;
  @Autowired
  private UserService userService;

  /**
   * 根据put方法创建 reward
   * 传入参数是
   * int discount, Date expiryDate, String rewardName , setExpiryDate
   *
   * @param newRewardRequest
   * @return
   */
  @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Reward create(@RequestBody NewRewardRequest newRewardRequest) {
    log.info("Recieved Reword {}", newRewardRequest);
    // 找到对应user 和 expiryDate
    User user = userService.getUserByUsername(newRewardRequest.getUsername());

    Date date = (newRewardRequest.getExpiryDate() == null) ?
      rewardService.setDefaultExpiryDate()
      : rewardService.setExpiryDate(newRewardRequest.getExpiryDate());
    Reward saveReward = rewardService.saveReward(newRewardRequest.getDiscount(),
      date,
      newRewardRequest.getRewardName(),
      user);
    return saveReward;
  }

  @GetMapping(path = "/", params = "username")
  @ResponseStatus(HttpStatus.CREATED)
  public Reward[] findRewardsByUsername(@RequestParam String username) {
    Reward[] rewards = rewardService.findRewardsByUserName(username)
      .toArray(new Reward[]{});
    return rewards;
  }
}
