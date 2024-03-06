package skcnc.framework.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class AppCommonControllerChk {
	@Around(value = "bean(*Controller)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

		Logger log = LoggerFactory.getLogger("INOUT_TIME");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start(String.valueOf(joinPoint.getSignature()));
		Object proceed = joinPoint.proceed();
		stopWatch.stop();

		log.debug("getTotalTimeSeconds : {}", stopWatch.getTotalTimeSeconds());
		
		return proceed;
	}
}
