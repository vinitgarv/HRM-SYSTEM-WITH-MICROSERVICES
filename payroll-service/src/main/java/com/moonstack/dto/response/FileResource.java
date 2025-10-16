package com.moonstack.dto.response;

import lombok.*;
import org.springframework.core.io.Resource;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResource {
    private Resource resource;
    private  String contentType;
}