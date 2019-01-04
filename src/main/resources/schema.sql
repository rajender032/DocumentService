CREATE TABLE IF NOT EXISTS FILE_UPLOAD( name VARCHAR2(256) NOT NULL,
  mime_type      VARCHAR2(128),
  size       NUMBER,
  creation_date   DATETIME
  );