package userinfo.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Entity
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_USER")
public class User extends BaseEntity implements Serializable {
  private String password;
  private String username;
  private Long phone;
  @Enumerated
  @Column(nullable = false)
  private LoginState state;
  //配置一对多关系
//  @OneToMany(targetEntity = Reward.class, mappedBy = "user", fetch = FetchType.EAGER)
//  @JsonIgnoreProperties("T_USER")
//  private List<Reward> rewardList;
}
