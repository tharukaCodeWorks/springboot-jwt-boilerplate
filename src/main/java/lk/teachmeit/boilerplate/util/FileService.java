package lk.teachmeit.boilerplate.util;

import lk.teachmeit.boilerplate.exceptions.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${upload.path}")
    public String uploadDir;

    public void uploadFile(MultipartFile file, String fileName, String location) {
        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + location + File.separator + StringUtils.cleanPath(fileName));

            File output = new File(String.valueOf(copyLocation));
            file.transferTo(output);
        } catch (Exception e) {
            System.out.println("File upload failed");
        }
    }

    public void uploadImage(BufferedImage image, String fileName, String location) {
        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + location + File.separator + StringUtils.cleanPath(fileName));

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

            ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
            File output = new File(String.valueOf(copyLocation));
            OutputStream out = new FileOutputStream(output);

            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()){
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.5f);
            }

            writer.write(null, new IIOImage(result, null, null), param);

            out.close();
            ios.close();
            writer.dispose();

        } catch (Exception e) {
            System.out.println("Image compression failed");
        }
    }

    public void uploadImage(MultipartFile file, String fileName, String location) {
        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + location + File.separator + StringUtils.cleanPath(fileName));

            BufferedImage image = ImageIO.read(file.getInputStream());
            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

//            BufferedImage image = ImageIO.read(file.getInputStream());
            ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
            File output = new File(String.valueOf(copyLocation));
            OutputStream out = new FileOutputStream(output);

            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()){
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.5f);
            }

            writer.write(null, new IIOImage(result, null, null), param);

            out.close();
            ios.close();
            writer.dispose();

        } catch (Exception e) {
           System.out.println("Image compression failed");
        }
    }

    public Resource loadFileAsResource(String fileLocation ,String fileName) {
         Path fileStorageLocation = Paths.get(uploadDir.concat(fileLocation))
                .toAbsolutePath().normalize();
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public boolean deleteFile(String imagName) {
        try {
            File file = new File(uploadDir.concat("/").concat(imagName));
            if(file.delete()) {
                return true;
            } else {
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public String getFilePath(String fileName){
        String path = "http://api.teachmeit.lk/guest/downloadFile/";
        return path+fileName;
    }
}
