package com.example.textextractor.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageFileDto {

    private long fileId;
    private long translationId;
    private long paymentId;
    private String userId;
    private String Uuid;
    private String extension;
}
