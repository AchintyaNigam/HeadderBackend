package com.example.docapi.config;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocxTemplateConfig {

    private String headerFontFamily = "Arial";
    private int headerFontSize = 12;
    private boolean headerBold = true;
    private boolean headerItalic = false;
    private ParagraphAlignment headerAlignment = ParagraphAlignment.CENTER;

    public String getHeaderFontFamily() {
        return headerFontFamily;
    }

    public void setHeaderFontFamily(String headerFontFamily) {
        this.headerFontFamily = headerFontFamily;
    }

    public int getHeaderFontSize() {
        return headerFontSize;
    }

    public void setHeaderFontSize(int headerFontSize) {
        this.headerFontSize = headerFontSize;
    }

    public boolean isHeaderBold() {
        return headerBold;
    }

    public void setHeaderBold(boolean headerBold) {
        this.headerBold = headerBold;
    }

    public boolean isHeaderItalic() {
        return headerItalic;
    }

    public void setHeaderItalic(boolean headerItalic) {
        this.headerItalic = headerItalic;
    }

    public ParagraphAlignment getHeaderAlignment() {
        return headerAlignment;
    }

    public void setHeaderAlignment(ParagraphAlignment headerAlignment) {
        this.headerAlignment = headerAlignment;
    }
}
