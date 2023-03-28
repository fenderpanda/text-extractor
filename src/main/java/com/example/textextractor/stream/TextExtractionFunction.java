package com.example.textextractor.stream;

import com.example.textextractor.payload.MessageFileDto;
import com.example.textextractor.service.TextExtractionService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Slf4j
@Component
public class TextExtractionFunction {

    private final TextExtractionService textExtractionService;

    public TextExtractionFunction(TextExtractionService textExtractionService) {
        this.textExtractionService = textExtractionService;
    }

    @Bean
    public Function<MessageFileDto, MessageFileDto> textExtraction() {
        return (message) -> {
            try {
                String extractedText = textExtractionService.extractText(
                        message.getUuid(),
                        message.getExtension());

                textExtractionService.saveExtractedTextToDocx(
                        message.getUuid(),
                        extractedText
                );
            } catch (TesseractException | Docx4JException e) {
                log.error(e.getClass().getName(), e);
                throw new RuntimeException(e);
            }

            return message;
        };
    }
}
