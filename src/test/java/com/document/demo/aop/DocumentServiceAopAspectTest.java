package com.document.demo.aop;

import static org.mockito.Mockito.when;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DocumentServiceAopAspectTest {

	@Mock
	private JoinPoint joinPoint;
	
	@Mock
	private ProceedingJoinPoint pjoinPoint;
	
	@Mock
	private MethodSignature signature;
	
	private DocumentServiceAopAspect documentAspect = new DocumentServiceAopAspect();
	
	@Test
	public void testBefore() {
		when(joinPoint.getSignature()).thenReturn(signature);
		when(joinPoint.getSignature().getDeclaringTypeName()).thenReturn("com.MyExample");
		when(joinPoint.getSignature().getName()).thenReturn("myMethod");
		documentAspect.before(joinPoint);
	}

	@Test
	public void testAfter() {
		when(joinPoint.getSignature()).thenReturn(signature);
		when(joinPoint.getSignature().getDeclaringTypeName()).thenReturn("com.MyExample");
		when(joinPoint.getSignature().getName()).thenReturn("myMethod");
		documentAspect.after(joinPoint);
	}

	@Test
	public void testAround() throws Throwable {
		when(pjoinPoint.getSignature()).thenReturn(signature);
		when(pjoinPoint.getSignature().getDeclaringTypeName()).thenReturn("com.MyExample");
		when(pjoinPoint.getSignature().getName()).thenReturn("myMethod");
		documentAspect.around(pjoinPoint);
	}

}
