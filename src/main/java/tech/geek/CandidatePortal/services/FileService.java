package tech.geek.CandidatePortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.geek.CandidatePortal.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    @Autowired
    private HttpServletRequest request;

    //THIS WILL MOST LIKELY BE CHANGED TO A POST REQUEST
    public String saveResume(MultipartFile file, User user) throws Exception{
        String filePath = "null";
        if (!file.isEmpty()) {
            try {
                //Folder to store resumes
                String uploadsDir = "/POC Resumes/" + user.getUser_id() + "/";

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

    public Map<String, String> pullUserResumes(User user) throws IOException {
        String filepath = "C:/Users/PatricNurczyk/Documents/GitHub/CandidatePortal/src/main/webapp/POC Resumes/" + user.getUser_id() + "/";
        Map<String, String> filePaths = new HashMap<>();
        File folder = new File(filepath);
        File [] files = folder.listFiles();
        if (files == null)
            return null;
        for (File file: files) {
            filePaths.put(file.getName(),file.getPath());
        }
        return filePaths;
    }
}
