package com.services.file;

import com.google.common.base.MoreObjects;

public class FileDto {
    private String base64;
    private String filename;
    private String filetype;
    private long filesize;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("base64", base64)
                .add("filename", filename)
                .add("filetype", filetype)
                .add("filesize", filesize)
                .toString();
    }
}
