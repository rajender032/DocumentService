package com.document.demo.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.document.demo.dao.DocumentStorageDaoImpl;
import com.document.demo.dto.Document;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DocumentStorageDaoTest {

	@Mock
	private NamedParameterJdbcTemplate jdbcTemplateMock;
	
	@InjectMocks
	private DocumentStorageDaoImpl documentStorageDaoImpl;
	
	@Test
	public void testSaveUpdateinRecordDB() {
		Mockito.when(jdbcTemplateMock.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
		int actualResult = documentStorageDaoImpl.save(getFileResponseRequestDate());
		assertEquals(1, actualResult);
	}
	
	@Test
	public void testSaveNotUpdateRecordDB() {
		Mockito.when(jdbcTemplateMock.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
		int actualResult = documentStorageDaoImpl.save(getFileResponseRequestDate());
		assertEquals(0, actualResult);
	}
	
	private static Document getFileResponseRequestDate() {
		Document request = new Document();
		request.setFileName("test.pdf");
		request.setFileType("application/pdf");
		request.setSize(34825);
		request.setCreationDate(new Date());
		return request;
	}
}
