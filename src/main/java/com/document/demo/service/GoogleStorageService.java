package com.document.demo.service;

import org.springframework.web.multipart.MultipartFile;

import com.document.demo.dto.Document;

public interface GoogleStorageService {

	Document upload(MultipartFile files);

	String retrive(MultipartFile fileName);

}
