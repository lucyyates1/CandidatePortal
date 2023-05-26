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

    //THIS WILL MOST LIKELY BE CHANGED TO A POST REQUEST
    public String saveResume(MultipartFile file) throws Exception{
        String filePath = "null";
        if (!file.isEmpty()) {
            try {
                //Folder to store resumes
                String uploadsDir = "/POC Resumes/";

                //Creates the upload path
                String uploadPath = request.getServletContext().getRealPath(uploadsDir);

                //Checks to see if the folder exists in the path (Creates it if false)
                if (!new File(uploadPath).exists()) {
                    new File(uploadPath).mkdir();
                }

                //Grabs the filename (Ex: SomeGuyResume2023.pdf)
                String fileName = file.getOriginalFilename();

                //adds the filename to the filepath
                filePath = uploadPath + fileName;

                //Stores the resume to the folder
                File dest = new File(filePath);
                file.transferTo(dest);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (IllegalStateException e) {
                throw new RuntimeException(e);
            }
        }
        return filePath;
    }

    public String saveCoverLetter(MultipartFile file){
        //NEED TO IMPLEMENT COVER LETTERS TO THE CANDIDATE TABLE
        return "filepath";
    }
}
