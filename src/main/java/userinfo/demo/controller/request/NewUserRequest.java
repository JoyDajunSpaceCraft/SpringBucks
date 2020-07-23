package userinfo.demo.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 1.@NotNull：不能为null，但可以为empty
 * 2.@NotEmpty：不能为null，而且长度必须大于0
 * 3.@NotBlank：只能作用在String上，
 *      不能为null，而且调用trim()后，
 *      长度必须大于0 ("test") 即：必须有实际字符
 */
@Getter
@Setter
@ToString
public class NewUserRequest {
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  @NotNull
  private Long phone;
}
