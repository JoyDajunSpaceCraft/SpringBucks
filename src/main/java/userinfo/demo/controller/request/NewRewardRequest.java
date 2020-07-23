package userinfo.demo.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import userinfo.demo.model.User;

import java.util.Date;

@Getter
@Setter
@ToString
public class NewRewardRequest {
  private Integer discount;
  private Integer expiryDate;
  private String rewardName;
  private String username;
}
