package com.document.demo.drive;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.document.demo.dao.DocumentStorageDao;
import com.document.demo.dto.Document;
import com.document.demo.exception.DocumentServiceException;
import com.document.demo.util.DocumentContentTypes;
import com.document.demo.util.GoogleDriveConfiguration;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

@Component
public class GoogleDriveService {

	@Inject
	private DocumentStorageDao documentStorageDao;

	public Document upload(MultipartFile file) {
		Document document = null;
		String fileName = file.getOriginalFilename();
		String fileContentType = fileName.substring(fileName.lastIndexOf('.') + 1);
		if (DocumentContentTypes.isPresent(fileContentType)) {
			try {
				Drive driveService = GoogleDriveConfiguration.newInstance().getDriveService();
				String contentType = file.getContentType();
				byte[] uploadData = file.getBytes();

				AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(contentType, uploadData);

				File fileMetadata = new File();
				fileMetadata.setName(fileName);

				File driveFile = driveService.files().create(fileMetadata, uploadStreamContent)
						.setFields("id, webContentLink, webViewLink, parents").execute();
				if (!driveFile.isEmpty()) {
					document = uploadFile(file, new Date());
					documentStorageDao.save(document);
				}
			} catch (IOException e) {
				throw new DocumentServiceException(
						"Could not store file " + fileName + " In Google Drive. Please try again!", e);
			}
		} else {
			document = new Document();
			document.setFileName(fileName);
			document.setErrorMsg("Sorry! Filename extension invalid sequence " + fileName);
		}

		return document;
	}

	public String findFileInGoogleDrive(MultipartFile file) {

		String fileName = file.getOriginalFilename();
		String query = "name contains '" + fileName + "' and mimeType !='application/vnd.google-apps.folder'";

		FileList files;
		try {
			Drive driveService = GoogleDriveConfiguration.newInstance().getDriveService();

			files = driveService.files().list().setQ(query).setSpaces("drive")
					.setFields("nextPageToken, files(id, name, createdTime, mimeType)").execute();

			if (files.getFiles().size() == 0) {
				return "File not exists in Google Drive " + fileName;
			}
			files.getFiles().forEach(Orginalfile -> {
				documentStorageDao.save(uploadFile(file, new Date(Orginalfile.getCreatedTime().getValue())));
			});

			return "File Exists in Google Drive " + fileName;
		} catch (IOException e) {
			throw new DocumentServiceException("Could not store file " + fileName + ". Please try again!", e);
		}
	}

	private Document uploadFile(MultipartFile file, Date createdDateTime) {
		Document document = new Document();
		document.setFileName(file.getOriginalFilename());
		document.setSize(file.getSize());
		String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
		document.setFileType(fileExtension);
		document.setCreationDate(createdDateTime);
		return document;
	}

}
