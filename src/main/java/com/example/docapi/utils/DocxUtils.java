package com.example.docapi.utils;

import com.example.docapi.config.DocxTemplateConfig;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DocxUtils {

    private final DocxTemplateConfig docxTemplateConfig;

    @Autowired
    public DocxUtils(DocxTemplateConfig docxTemplateConfig)
    {
        this.docxTemplateConfig = docxTemplateConfig;
    }

    public byte[] updateHeader(InputStream is, String headerText) throws IOException
    {
        XWPFDocument document = new XWPFDocument(is);

        // Get or create the header
        XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();
        if (policy == null)
        {
            policy = new XWPFHeaderFooterPolicy(document);
        }
        XWPFHeader header = policy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);

        if (header != null)
        {
            // Clear to modify existing header
            header.clearHeaderFooter();
        }
        else
        {
            // Create a new header
            header = policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
        }

        // Create paragraph.
        XWPFParagraph paragraph = header.createParagraph();
        paragraph.setAlignment(docxTemplateConfig.getHeaderAlignment());
        XWPFRun run = paragraph.createRun();
        run.setText(headerText);

        // Apply template configurations
        run.setFontFamily(docxTemplateConfig.getHeaderFontFamily());
        run.setFontSize(docxTemplateConfig.getHeaderFontSize());
        run.setBold(docxTemplateConfig.isHeaderBold());
        run.setItalic(docxTemplateConfig.isHeaderItalic());

        // Write the modified document to ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.write(baos);
        document.close();

        return baos.toByteArray();
    }
}
