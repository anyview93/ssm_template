package cn.mine.database;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

	//记录日志
	Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
	
	//service层切点
	@Pointcut("execution(* cn.mine.*.dao.mapper.*.*(..))")
	public void dataSourceAspect(){
		
	}
	
	@Before("dataSourceAspect()")
	public void doBefore(JoinPoint joinPoint) throws Throwable{
		Object target = joinPoint.getTarget();
		String method = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		Class<?>[] classes = target.getClass().getInterfaces();
		Class<?>[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
		String dataSourceValue = "";
		Method m = classes[0].getMethod(method, parameterTypes);  
        if (m != null && m.isAnnotationPresent(DataSourceAnnotation.class)) {  
        	dataSourceValue = m.getAnnotation(DataSourceAnnotation.class).dataSourceValue();  
            logger.info("类【{}】中的方法:【{}】使用数据源【{}】",className, method,dataSourceValue);
          //mybatis自动生成类判断
        } else if(method.toUpperCase().indexOf("INSERT") == 0 || method.toUpperCase().indexOf("DELETE") == 0 ||method.toUpperCase().indexOf("UPDATE") == 0){
        	dataSourceValue = "master";
        	logger.info("使用dataSource:"+"master");
        }else if(method.toUpperCase().indexOf("QUERY") == 0 || method.toUpperCase().indexOf("SELECT") == 0 || method.toUpperCase().indexOf("COUNT") == 0 || method.toUpperCase().indexOf("FIND") == 0){
        	dataSourceValue = "slave";  
        	logger.info("使用dataSource:"+"slave");
        }else{
        	dataSourceValue = "";
        }
        if(!dataSourceValue.equals("")){
        	DynamicDataSourceHolder.setDataSource(dataSourceValue);
        }else{
        	throw new RuntimeException("类【{" + className +"}】中的方法:【{" + method + "}】未获取到数据源");
        }
	}
}
