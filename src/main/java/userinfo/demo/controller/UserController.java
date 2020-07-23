package userinfo.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.MetaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import userinfo.demo.controller.exception.FormValidationException;
import userinfo.demo.controller.request.NewUserRequest;
import userinfo.demo.model.User;
import userinfo.demo.service.RewardService;
import userinfo.demo.service.UserService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/#/account")
public class UserController {
  @Autowired
  private RewardService rewardService;
  @Autowired
  private UserService userService;

  @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public User createUser(@Valid NewUserRequest newUserRequest,
                         BindingResult result)
  {
    if(result.hasErrors()){
      log.warn("Bing Error :{}",result);
      throw new FormValidationException(result);
    }
    return userService.createUser(newUserRequest.getUsername(),newUserRequest.getPassword(),newUserRequest.getPhone());
  }

  public void updateState(){

  }


}
