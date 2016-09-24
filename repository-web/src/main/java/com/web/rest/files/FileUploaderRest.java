package com.web.rest.files;


import com.services.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/files")
public class FileUploaderRest {

    private static final Logger logger = LoggerFactory.getLogger(FileUploaderRest.class);

    @Autowired
    private FileService fileParserService;

    @RequestMapping(value = "/createUsers", headers = "'Content-Type': 'multipart/form-data'", method = RequestMethod.POST)
    public boolean createUsers(@RequestParam(value = "file") MultipartFile file) {
        String filename = file.getOriginalFilename().toLowerCase();
        try {
            InputStream fileData = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
