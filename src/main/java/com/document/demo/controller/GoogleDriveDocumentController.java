package com.document.demo.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.document.demo.dto.Document;
import com.document.demo.service.GoogleStorageService;
import com.document.demo.util.DocumentContentTypes;

@RestController
public class GoogleDriveDocumentController {

	@Inject
	private GoogleStorageService googleStorageService;

	@PostMapping(value="/document",produces=MediaType.APPLICATION_JSON_VALUE)
	public Document uploadDocument(@RequestParam("file") MultipartFile file) {
		if (file.getOriginalFilename().isEmpty()) {
			return new Document(null, null, 0, null, "Please select document need to upload in google drive");
		}
		Document response = googleStorageService.upload(file);
		return new Document(response.getFileName(), file.getContentType(), file.getSize(),
				response.getCreationDate(), response.getErrorMsg());
	}
	
	@PostMapping("/documents")
	public List<Document> uploadDocuments(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadDocument(file)).collect(Collectors.toList());
	}

	@PostMapping(value = "/search")
	public String findDocument(@RequestPart("fileName") MultipartFile fileName) throws IOException {
		String fileContentType = fileName.getOriginalFilename()
				.substring(fileName.getOriginalFilename().lastIndexOf('.') + 1);

		if (!DocumentContentTypes.isPresent(fileContentType)) {
			return "Sorry! Filename extension invalid " + fileName.getOriginalFilename();
		}
		return googleStorageService.retrive(fileName);
	}

}
