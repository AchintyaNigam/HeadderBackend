package com.example.docapi.controllers;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RestController
@RequestMapping("/api/docx")
public class DocxController {

    @PostMapping("/upload")
    public ResponseEntity<byte[]> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("header") String headerText) throws IOException {
        try (InputStream is = file.getInputStream()) {
            XWPFDocument document = new XWPFDocument(is);

            // Get or create the header
            XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();
            if (policy == null) {
                policy = new XWPFHeaderFooterPolicy(document);
            }
            XWPFHeader header = policy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);

            if (header != null) {
                // Modify existing header
                header.clearHeaderFooter();
            } else {
                // Create a new header
                header = policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
            }

            XWPFParagraph paragraph = header.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(headerText);

            // Write the modified document to ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.write(baos);

            // Set content type and headers for download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("modified_document.docx")
                    .build());

            // Return the byte array of the modified document
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
