package com.cfl.jd.config;

import com.cfl.jd.annotation.RepeatSubmit;
import com.cfl.jd.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Aspect
@Component
public class RepeatSubmitAOP {

	/**
	 * 使用redis来实现防止表单重复提交操作
	 */
	@Autowired
	private RedisUtil redisUtil;


	/**
	 * 定义切点
	 */
	@Pointcut(value = "@annotation(com.cfl.jd.annotation.RepeatSubmit)")
	public void repeatSubmit(){}


	/**
	 * 使用环绕通知可以阻止程序继续执行
	 * @param pjp
	 * @throws Throwable
	 */
	@Around("repeatSubmit()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		//获取request和response
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();

		// 获取注解的参数
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		RepeatSubmit repeatSubmit = signature.getMethod().getAnnotation(RepeatSubmit.class);
		System.out.println(repeatSubmit.value());

		Object ret = null;
		// TODO: 此处为自定义验证逻辑，符合条件则继续执行，否则终止方法的执行
		if (1 == 1) {
			// 执行方法
			ret =  pjp.proceed();
			System.out.println("HH 方法环绕proceed，结果是 :" + ret);
		} else {
			System.out.println("HH 方法环绕proceed，不满足条件未执行");
		}

		return ret;
	}
}
