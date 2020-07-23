package userinfo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


@Data
@Builder
@Entity
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_REWARD")
public class Reward extends BaseEntity implements Serializable {
  private Integer discount;
  private Date expiryDate;//优惠劵失效时间
  private String rewardName;
  @Enumerated
  @Column(nullable = false)
  private RewardState rewardState;
  //配置多对一关系
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id", referencedColumnName = "id")
  @JsonIgnoreProperties("T_REWARD")
  private User user;
}
// 优惠劵名称
//优惠额度
// 优惠劵状态
//优惠劵失效时间

