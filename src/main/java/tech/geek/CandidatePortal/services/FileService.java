package tech.geek.CandidatePortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private HttpServletRequest request;

    public String saveResume(MultipartFile file) throws Exception{
        String filePath = "null";
        if (!file.isEmpty()) {
            if (!file.isEmpty()) {
                try {
                    String uploadsDir = "/POC Resumes/";
                    //Creates the upload path
                    String realUploadPath = request.getServletContext().getRealPath(uploadsDir);
                    //Checks to see if the folder exists
                    if (!new File(realUploadPath).exists()) {
                        new File(realUploadPath).mkdir();
                    }
                    String fileName = file.getOriginalFilename();
                    //Makes the filepath
                    filePath = realUploadPath + fileName;
                    //Stores the resume on the folder
                    File dest = new File(filePath);
                    file.transferTo(dest);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (IllegalStateException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return filePath;
    }

    public String saveCoverLetter(MultipartFile file){
        //NEED TO IMPLEMENT SAVING THE FILE TO THE APP
        return "filepath";
    }
}
