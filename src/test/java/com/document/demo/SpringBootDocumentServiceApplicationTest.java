package com.document.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.document.demo.SpringBootDocumentServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDocumentServiceApplicationTest {

	@Test
	public void testMain() {
		SpringBootDocumentServiceApplication.main(new String[] {});
	}

}

