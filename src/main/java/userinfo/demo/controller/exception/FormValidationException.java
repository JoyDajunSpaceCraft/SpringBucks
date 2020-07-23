package userinfo.demo.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@AllArgsConstructor//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
public class FormValidationException extends RuntimeException {

  private BindingResult result;

}
