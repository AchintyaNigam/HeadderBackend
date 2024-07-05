package com.example.docapi.controllers;

import com.example.docapi.service.DocxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/docx")
public class DocxController {

    @Autowired
    private DocxService docxService;

    @PostMapping("/upload")
    public ResponseEntity<byte[]> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("header") String headerText) throws IOException {
        try
        {
            byte[] modifiedDoc = docxService.updateHeader(file, headerText);

            // Extract the original filename and create the new filename
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            String modifiedFilename = "modified_" + (originalFilename != null ? originalFilename : "document.docx");
            System.out.println(modifiedFilename);


            // Set content type and headers for download.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); //Standard media type for binary files.
            headers.setContentDisposition(ContentDisposition.builder("attachment") // No need to set this for inline doc.
                    .filename(modifiedFilename)
                    .build());

            // Return the byte array of the modified document
            return new ResponseEntity<>(modifiedDoc, headers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
