package com.document.demo.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.document.demo.dto.Document;

@Repository
public class DocumentStorageDaoImpl implements DocumentStorageDao{

	private static final String INSER_FILE_QUERY = "INSERT INTO FILE_UPLOAD (name, mime_type, size, creation_date) VALUES (:fileName, :fileType, :size, :creationDate)";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public int save(Document file) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fileName", file.getFileName());
		parameters.put("fileType", file.getFileType());
		parameters.put("size", file.getSize());
		parameters.put("creationDate", new java.sql.Date(file.getCreationDate().getTime()));
		return jdbcTemplate.update(INSER_FILE_QUERY, parameters);
	}

}
