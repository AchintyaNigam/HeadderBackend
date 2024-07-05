package com.example.docapi.service;

import com.example.docapi.utils.DocxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DocxService {

    private final DocxUtils docxUtils;

    @Autowired
    public DocxService(DocxUtils docxUtils)
    {
        this.docxUtils = docxUtils;
    }

    public byte[] updateHeader(MultipartFile file, String headerText) throws IOException
    {
        return docxUtils.updateHeader(file.getInputStream(), headerText);
    }
}
