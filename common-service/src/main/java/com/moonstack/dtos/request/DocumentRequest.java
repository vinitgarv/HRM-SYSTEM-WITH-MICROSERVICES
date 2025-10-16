package com.moonstack.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    private String fileName;
    private String fileType;
}
