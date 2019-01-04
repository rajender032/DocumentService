package com.document.demo.drive;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.document.demo.dao.DocumentStorageDao;
import com.document.demo.drive.GoogleDriveService;
import com.document.demo.dto.Document;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GoogleDriveServiceTest {

	@Mock
	private DocumentStorageDao storageMoock;
	
	@InjectMocks
	private GoogleDriveService serviceMock;
	
	@Test
	public void testCreateFileInGoogleDriveValidFile() {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.doc", "text/plain",
				"test data".getBytes());
		Mockito.when(storageMoock.save(Mockito.any())).thenReturn(1);
		
		Document actual =serviceMock.upload(mockMultipartFile);
		
		assertEquals("test.doc", actual.getFileName());
		
		
	}

	@Test
	public void testCreateFileInGoogleDriveInvalidFile() {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.text", "text/plain",
				"test data".getBytes());
		
		Document actual = serviceMock.upload(mockMultipartFile);
		
		assertEquals("test.text", actual.getFileName());
	}
	
	@Test
	public void testFindFileInGoogleDriveValidFile() {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.doc", "text/plain",
				"test data".getBytes());
		Mockito.when(storageMoock.save(Mockito.any())).thenReturn(1);
		
		String actualResult = serviceMock.findFileInGoogleDrive(mockMultipartFile);
		
		assertEquals("File Exists in Google Drive test.doc", actualResult);
	}
	
	@Test
	public void testFindFileInGoogleDriveInvalidFile() {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "232142143245325325.doc", "text/plain",
				"test data".getBytes());
		
		String actualResult = serviceMock.findFileInGoogleDrive(mockMultipartFile);
		
		assertEquals("File not exists in Google Drive 232142143245325325.doc", actualResult);
	}

}
