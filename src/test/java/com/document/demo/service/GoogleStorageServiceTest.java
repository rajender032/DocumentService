package com.document.demo.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.document.demo.drive.GoogleDriveService;
import com.document.demo.dto.Document;
import com.document.demo.service.GoogleStorageServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GoogleStorageServiceTest {

	@Mock
	private GoogleDriveService driveMock;

	@InjectMocks
	private GoogleStorageServiceImpl storageService;

	@Test
	public void testUploadFileInGoogleDrive() {
		Document expected = new Document();
		expected.setFileName("test.docs");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.doc", "text/plain",
				"test data".getBytes());
		Mockito.when(driveMock.upload(Mockito.any())).thenReturn(expected);
		Document actualResult = storageService.upload(mockMultipartFile);
		assertEquals(expected.getFileName(), actualResult.getFileName());
	}
	
	@Test
	public void testGetGoogleFilesByName() {
		String expected = "File Exists in Google Drive";
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "text/plain",
				"test data".getBytes());
		Mockito.when(driveMock.findFileInGoogleDrive(Mockito.any())).thenReturn("File Exists in Google Drive");
		String actual = storageService.retrive(mockMultipartFile);
		assertEquals(expected, actual);
	}
}
