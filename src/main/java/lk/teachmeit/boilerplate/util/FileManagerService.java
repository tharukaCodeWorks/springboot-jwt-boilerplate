package lk.teachmeit.boilerplate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileManagerService {

    @Autowired
    FileService fileService;

    public boolean deleteImage(String name){
        try {
            String [] fileName = name.split("/");
            fileService.deleteFile(fileName[fileName.length - 1]);
            return true;
        } catch ( Exception  e) {
            return false;
        }
    }
}
