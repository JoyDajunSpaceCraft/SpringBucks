package userinfo.demo.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.SourceLocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.xml.transform.SourceLocator;
import java.io.ObjectStreamClass;

/**
 * 注意around的 传入参数 和 before等的传入参数的不同
 */
@Aspect//表示为切面
@Component//表示为Bean
@Slf4j
public class PerformanceAspect {

  @Around("repositoryOps()")//包围所有repository包下的方法
  public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable{
    long startTime = System.currentTimeMillis();
    String name = "-";//打印方法名
    String result = "Y";//方法执行成功
    try {
      name = pjp.getSignature().toShortString();//获得方法名
      return pjp.proceed();//实际后续调用
    }catch (Throwable t)
    {
      result = "N";//方法失败 N
      throw t;
    }
    finally {
      long endTime = System.currentTimeMillis();
      log.info("{};{};{}ms for repository",name, result,endTime-startTime);
    }
  }
  @Pointcut("execution(* userinfo.demo.repository..*(..))")//pointcut表示切入位置
  private void repositoryOps() {
  }

  @Before("serviceOps()")
  public void logServicePerformance(JoinPoint joinPoint) throws  Throwable{
    long startTime = System.currentTimeMillis();
    String name = "-";//打印方法名
    String result = "Y";//方法执行成功
    String target = "**";
    try {
      name = joinPoint.getSignature().toShortString();//获得方法名
      target = joinPoint.getTarget().toString();
    }catch (Throwable t)
    {
      result = "N";//方法失败 N
      throw t;
    }
    finally {
      long endTime = System.currentTimeMillis();
      log.info("{};{};{};{}ms for service",name, result,target, endTime-startTime);
    }
  }

  @Pointcut("execution(* userinfo.demo.service..*(..))")
  private void serviceOps(){}
}
