package uk.ac.ncl.cs.groupproject.dao.impl;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.cs.groupproject.dao.FileDao;
import uk.ac.ncl.cs.groupproject.dao.FileEntity;

import java.io.*;

/**
 * @Auther: Li Zequn
 * Date: 11/03/14
 */
@Repository
public class FileDaoImpl implements FileDao {
    private String bucketName = "docstore123";
    private Logger log = Logger.getLogger(FileDaoImpl.class);
    private AmazonS3 s3;
    public FileDaoImpl(){
        s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
        Region region = Region.getRegion(Regions.EU_WEST_1);
        s3.setRegion(region);
    }
//    public void createBucket(){
//        s3.createBucket(bucketName);
//    }
    @Override
    public void storeFile(String key,FileEntity entity) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        log.info("store: "+entity.getFileName());
        objectMetadata.addUserMetadata("filename", entity.getFileName());
        s3.putObject(new PutObjectRequest(bucketName,key,entity.getInputStream(),objectMetadata));
    }

    @Override
    public FileEntity getFile(String key) {
        S3Object s3Object = s3.getObject(new GetObjectRequest(bucketName, key));
        FileEntity entity = new FileEntity();
        String fileName = s3Object.getObjectMetadata().getUserMetadata().get("filename");
        log.info("get: "+s3Object.getObjectMetadata().getUserMetadata());
        entity.setFileName(fileName);
        entity.setInputStream(s3Object.getObjectContent());
        return entity;
    }

    @Override
    public InputStream test() {
        S3Object s3Object = s3.getObject(new GetObjectRequest(bucketName, "aaaa"));
        return s3Object.getObjectContent();
    }

    @Override
    public void deleteFile(String key) {
        s3.deleteObject(bucketName,key);
    }
    //    public static void main(String [] args) throws IOException {
//        File file = new File("test.txt");
//        InputStream inputStream = new FileInputStream(file);
//        FileDaoImpl fileDao = new FileDaoImpl();
//        //fileDao.createBucket();
//        fileDao.storeFile("aaaa",inputStream);
//        fileDao.getFile();
//    }
}
