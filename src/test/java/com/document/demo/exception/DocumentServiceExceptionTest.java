package com.document.demo.exception;

import org.junit.Test;

import com.document.demo.exception.DocumentServiceException;

public class DocumentServiceExceptionTest {

	
	@Test
	public void testFileStorageExceptionString() {
		new DocumentServiceException("Exception Message");
	}

	@Test
	public void testFileStorageExceptionStringThrowable() {
		new DocumentServiceException("Exception Message", new Throwable());
	}

}
