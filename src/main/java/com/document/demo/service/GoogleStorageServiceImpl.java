package com.document.demo.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.document.demo.drive.GoogleDriveService;
import com.document.demo.dto.Document;

@Service
public class GoogleStorageServiceImpl implements GoogleStorageService {

	@Inject
	private GoogleDriveService serivce;

	@Override
	public Document upload(MultipartFile file) {
		return serivce.upload(file);
	}

	@Override
	public String retrive(MultipartFile fileName) {
		return serivce.findFileInGoogleDrive(fileName);
	}

}
