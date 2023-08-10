package com.springmvcjpa.primerproyectospringmvcyjpa.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileServiceImpl implements IUploadFileService{
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOAD_FOLDER = "uploads";

    @Override
    public Resource load(String filename) {
        return null;
    }

    @Override
    public String copy(MultipartFile file) {
        return null;
    }

    @Override
    public boolean delete(String filename) {
        return false;
    }
}
