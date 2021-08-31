package com.codebook.handler;

import com.codebook.domain.UploadImgFileDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ImgFileHandler {

    public UploadImgFileDTO parseImgFile(String email, MultipartFile multipartFile) throws IOException {
        UploadImgFileDTO uploadImgFile = new UploadImgFileDTO();
        if (multipartFile.isEmpty()) {
            return uploadImgFile;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(new Date());
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "./src/main/resources/static/uploadedfiles/images/";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        if(!multipartFile.isEmpty()){
            String contentType = multipartFile.getContentType();
            String originalFileExtension = null;
            if (ObjectUtils.isNotEmpty(contentType)){
                if(contentType.contains("image/jpeg")){
                    originalFileExtension = ".jpg";
                }else if(contentType.contains("image/png")){
                    originalFileExtension = ".png";
                }else if(contentType.contains("image/gif")){
                    originalFileExtension = ".gif";
                }else {
                    return null;
                }
            }else{
                return null;
            }
            String newFileName = email + System.nanoTime() + originalFileExtension;
            uploadImgFile.setEmail(email);
            uploadImgFile.setOriginalFileName(multipartFile.getOriginalFilename());
            uploadImgFile.setStoredFilePath(newFileName);
            uploadImgFile.setFileSize(multipartFile.getSize());
            File saveFile = new File(absolutePath + path + "/" + newFileName);
            multipartFile.transferTo(saveFile);
        }
        return uploadImgFile;
    }
}
