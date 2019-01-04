package com.document.demo.util;

import java.util.Arrays;

public enum DocumentContentTypes {
	PDF("pdf"), XLSX("xlsx"), XLS("xls"), DOC("doc"), DOCS("docx");

	private String contentType;

	DocumentContentTypes(String contentType) {
		this.contentType = contentType;
	}

	public static boolean isPresent(String contentType) {
		return Arrays.stream(DocumentContentTypes.values()).filter(c -> c.contentType.equals(contentType)).findFirst().isPresent();
	}
}
