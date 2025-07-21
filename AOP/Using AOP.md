## Add the AOP Dependency `spring-boot-starter-aop` and Define an Aspect Class

``` java

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Configuration // Marks this class as a Spring configuration (bean)
@Aspect        // Indicates that this class is an Aspect (i.e., contains cross-cutting logic)
public class LoggingAspect {

    // Logger to log messages (using SLF4J)
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Advice: This method runs BEFORE the execution of any method in the specified package
    @Before("execution(* com.learning.spring_aop.*.*.*(..))") // pointcut
    public void logMethodCall(JoinPoint joinPoint) {  // The JoinPoint gives info like method name and arguments.
        // Logs the method signature before the actual method execution
        logger.info("Before Aspect - Method is called {}", joinPoint);
    }

    @After("execution(* com.learning.spring_aop.*.*.*(..))")
    public void logMethodCallAfterExecution(JoinPoint joinPoint) {
        logger.info("After Aspect - Method is called {}" , joinPoint);
    }

    @AfterThrowing(
            pointcut = "execution(* com.learning.spring_aop.*.*.*(..))",
            throwing = "exception"
    )
    public void logMethodCallAfterException(JoinPoint joinPoint,Exception exception) {
        logger.info("AfterThrowing Aspect - {} has thrown an exception {}"
                ,joinPoint,exception);
    }

    @AfterReturning(
            pointcut = "execution(* com.learning.spring_aop.*.*.*(..))",
            returning = "result"
    )
    public void logMethodCallAfterReturning(JoinPoint joinPoint,Object result) {
        logger.info("AfterReturning Aspect - {} has returned {}"
                ,joinPoint,result);
    }
}


```

### `execution(* com.example.service.*.*(..))`

This is an **AspectJ pointcut expression** that tells Spring AOP where to apply advice (e.g., before/after method execution). Let’s break it down:

1. **`execution(...)`**

   * The keyword `execution` targets **method executions**—this is the primary join point type supported by Spring AOP.

2. **`*` (before the package)**

   * Indicates the **return type** of the methods to match.
   * `*` means it matches **any return type** (e.g., `void`, `String`, custom types).

3. **`com.example.service.*`**

   * Specifies the **package pattern**.
   * `com.example.service.*` matches **any class** directly under the `service` package (one level deep).

4. **`.*(..)` (after the class)**

   * `.*` means **any method name** in those classes.
   * `(..)` means **any number and type of parameters** (zero or more).

---

## Important Terminology

* Advice - What code to execute?
    * Example: Logging, Authentication

* Pointcut - Expression that identifies method calls to be intercepted
* Aspect - A combination of
  * Advice - what to do AND
  * Pointcut- when to intercept a method call

* Weaver - Weaver is the framework that implements AOP
AspectJ or Spring AOP
* Join Point
   * When pointcut condition is true, the advice is executed. A specific execution instance of an advice is called a Join Point.
 
---

* @Before - Do something before a method is called
* @After - Do something after a method is executed irrespective of whether:
    * 1: Method executes successfully OR
    * 2: Method throws an exception
* @AfterReturning Do something ONLY when a method executes successfully
* @AfterThrowing - Do something ONLY when a method throws an exception

---

## @Around Do something before and after a method execution

### code Example

``` java

package com.learning.spring_aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class PerformanceTrackingAspect {

    private Logger logger = LoggerFactory.getLogger(PerformanceTrackingAspect.class);

    @Around("execution(* com.learning.spring_aop.*.*.*(..))")
    public Object findExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    // here we need ProceedingJoinPoint insted of joinPoint to proceed the execution method

        long StartTime = System.currentTimeMillis();

        // Proceed with the actual method execution
        Object proceed = proceedingJoinPoint.proceed();

        long EndTime = System.currentTimeMillis();

        long ExecutionTime = EndTime - StartTime;

        logger.info("Execution time of {} is {}", proceedingJoinPoint.getSignature().getName(), ExecutionTime);

        return proceed;
    }
}


```
