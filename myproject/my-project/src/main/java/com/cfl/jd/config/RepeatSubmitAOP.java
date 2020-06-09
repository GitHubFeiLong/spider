package com.cfl.jd.config;

import com.cfl.jd.annotation.RepeatSubmit;
import com.cfl.jd.constant.CacheConsts;
import com.cfl.jd.util.IpAddressUtil;
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
import org.springframework.util.ObjectUtils;
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

		// 创建redis的key
		String redisKey = CacheConsts.REPEAT_SUBMIT + IpAddressUtil.getIpAddress(request) + request.getRequestURI();

		// 从redis中获取key对应的值
		Object redisValue = redisUtil.get(redisKey);

		// TODO: redis中没有指定key，符合条件则继续执行，否则终止方法的执行
		if (ObjectUtils.isEmpty(redisValue)) {
			// 执行方法
			ret =  pjp.proceed();

			// 将key存在redis中，指定时间
			redisUtil.set(redisKey,1, repeatSubmit.value());
			System.out.println("HH 方法环绕proceed，结果是 :" + ret);
		} else {
			System.out.println("HH 方法环绕proceed，不满足条件未执行");
		}
		return ret;
	}
}
