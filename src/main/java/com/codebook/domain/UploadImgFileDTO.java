package com.codebook.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadImgFileDTO {

    private String email;
    private String originalFileName;
    private String storedFilePath;
    private Long fileSize;
}
