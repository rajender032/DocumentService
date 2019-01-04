package com.document.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.document.demo.dto.Document;
import com.document.demo.service.GoogleStorageService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GoogleDriveDocumentController.class)
public class GoogleDriveDocumentControllerTest {

	@MockBean
	private GoogleStorageService googleStorageService;

	@Inject
	private MockMvc mock;

	@Test
	public void testuploadDocumentValid() throws Exception {
		Document expected = new Document();
		expected.setFileName("test.docs");

		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.docs", "application/octet-stream",
				"test data".getBytes());

		Mockito.when(googleStorageService.upload(Mockito.any(MultipartFile.class))).thenReturn(expected);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/document").file(mockMultipartFile);
		MvcResult result = mock.perform(requestBuilder).andReturn();

		Gson g = new Gson();
		Document actualResult = g.fromJson(result.getResponse().getContentAsString(), Document.class);

		assertEquals(expected.getFileName(), actualResult.getFileName());
	}

	@Test
	public void testuploadDocumentEmpty() throws Exception {
		Document expected = new Document();
		expected.setFileName("test.docs");

		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", null, "application/octet-stream",
				"test data".getBytes());

		Mockito.when(googleStorageService.upload(Mockito.any(MultipartFile.class))).thenReturn(expected);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/document").file(mockMultipartFile);
		mock.perform(requestBuilder).andReturn();

		verify(googleStorageService, times(0)).upload(Mockito.any(MultipartFile.class));
	}

	@Test
	public void testuploadDocuments() throws Exception {
		Document request = null;
		request = new Document();
		request.setFileName("test.xml");

		MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "test.xml", "text/plain",
				"test data".getBytes());
		MockMultipartFile mockMultipartFile1 = new MockMultipartFile("files", "test.doc", "text/plain",
				"test data".getBytes());

		Mockito.when(googleStorageService.upload(Mockito.any(MultipartFile.class))).thenReturn(request);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/documents").file(mockMultipartFile)
				.file(mockMultipartFile1);

		mock.perform(requestBuilder).andReturn();

		verify(googleStorageService, times(2)).upload(Mockito.any(MultipartFile.class));
	}

	@Test
	public void testFindFileInGoogleDriveInvalidFile() throws Exception {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("fileName", "test.xml", "text/plain",
				"test data".getBytes());
		Mockito.when(googleStorageService.retrive(Mockito.any(MultipartFile.class)))
				.thenReturn("Sorry! Filename extension invalid ");
		RequestBuilder request = MockMvcRequestBuilders.multipart("/search").file(mockMultipartFile);
		MvcResult result = mock.perform(request).andReturn();

		String expected = "Sorry! Filename extension invalid " + mockMultipartFile.getOriginalFilename();

		assertEquals(expected, result.getResponse().getContentAsString());
	}

	@Test
	public void testFindFileInGoogleDriveValidFile() throws Exception {
		String expected = "File Exists in Google Drive";
		MockMultipartFile mockMultipartFile = new MockMultipartFile("fileName", "test.pdf", "application/pdf",
				"test data".getBytes());

		Mockito.when(googleStorageService.retrive(Mockito.any(MultipartFile.class)))
				.thenReturn("File Exists in Google Drive");

		RequestBuilder request = MockMvcRequestBuilders.multipart("/search").file(mockMultipartFile);
		MvcResult result = mock.perform(request).andReturn();

		assertEquals(expected, result.getResponse().getContentAsString());
	}
}
