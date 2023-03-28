package com.example.textextractor.service;

import net.sourceforge.tess4j.TesseractException;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public interface TextExtractionService {

    String extractText(String uuid, String extension) throws TesseractException;
    void saveExtractedTextToDocx(String uuid, String text) throws Docx4JException;
}
