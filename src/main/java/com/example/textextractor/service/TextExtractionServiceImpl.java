package com.example.textextractor.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TextExtractionServiceImpl implements TextExtractionService{
    @Value("${path.tesseract.tessdataDir}")
    private String tessdataDir;
    @Value("${path.uploadsDir}")
    private String uploadsDir;
    private final String DOCX = "docx";
    private final String EXTRACTED = "extracted_";

    @Override
    public String extractText(String uuid, String extension) throws TesseractException {

        String filename = uuid + "." + extension;

        File image = new File(uploadsDir + filename);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessdataDir);
        tesseract.setVariable("user_defined_dpi", "300");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);

        return tesseract.doOCR(image);
    }

    @Override
    public void saveExtractedTextToDocx(String uuid, String text) throws Docx4JException {

        String filename = EXTRACTED + uuid + "." + DOCX;

        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

        int wordAmount = 0;
        String[] result = text.split("\n");

        for (String value : result) {
            wordAmount += value.split("\\s").length;
            mainDocumentPart.addParagraphOfText(value);
        }

        File docxFile = new File(uploadsDir + filename);
        wordPackage.save(docxFile);
    }
}
