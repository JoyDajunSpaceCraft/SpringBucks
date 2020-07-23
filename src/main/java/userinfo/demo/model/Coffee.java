package userinfo.demo.model;

import lombok.*;
import org.hibernate.annotations.Table;
import org.joda.money.Money;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable  {
  private String name;
  private Money price;
}

