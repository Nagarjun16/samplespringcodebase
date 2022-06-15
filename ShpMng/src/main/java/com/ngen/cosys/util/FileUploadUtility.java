package com.ngen.cosys.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;

import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.framework.exception.SystemException;

public class FileUploadUtility {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(FileUploadUtility.class);

   private static final char[] hexChars = "0123456789ABCDEF".toCharArray();
   private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
   private static final String FILE_PATH = TEMP_DIR;
   //private static final String SOURCE_FILE = TEMP_DIR;
   private static final String TARGET_FILE = TEMP_DIR;

   private FileUploadUtility() {
      // Blank Constructor
   }

   public static synchronized String generateUUID() {
      // Creates a new random (version 4) UUID
      UUID tokenId = UUID.randomUUID();
      MessageDigest salt = null;
      try {
         //
         salt = MessageDigest.getInstance("SHA-256");
         //
         salt.update(tokenId.toString().getBytes("UTF-8"));
      } catch (Exception e) {
         lOgger.error(EXCEPTION, e);
      }
      if (null == salt) {
         throw new SystemException("MessageDigest cannot be NULL");
      }
      return bytesToHex(salt.digest());
   }

   public static String bytesToHex(byte[] bytes) {
      char[] hash = new char[bytes.length * 2];
      for (int j = 0; j < bytes.length; j++) {
         int v = bytes[j] & 0xFF;
         hash[j * 2] = hexChars[v >>> 4];
         hash[j * 2 + 1] = hexChars[v & 0x0F];
      }
      return new String(hash);
   }

   //
   public static Base64.Encoder getEncodeType(String enctype) {
      //
      Base64.Encoder encode = null;
      if (Objects.equals(enctype, "BASIC")) {
         // Basic Encoder
         encode = Base64.getEncoder();
      } else if (Objects.equals(enctype, "MIME")) {
         // Mime Encoder
         encode = Base64.getMimeEncoder();
      } else if (Objects.equals(enctype, "URL")) {
         // URL & FileName Safe Encoder
         encode = Base64.getUrlEncoder();
      }
      return encode;
   }

   //
   public static Base64.Decoder getDecodeType(String enctype) {
      //
      Base64.Decoder decode = null;
      if (Objects.equals(enctype, "BASIC")) {
         // Basic Decoder
         decode = Base64.getDecoder();
      } else if (Objects.equals(enctype, "MIME")) {
         // Mime Decoder
         decode = Base64.getMimeDecoder();
      } else if (Objects.equals(enctype, "URL")) {
         // URL & FileName Safe Decoder
         decode = Base64.getUrlDecoder();
      }
      return decode;
   }

   //
   public static byte[] getBytes(String fileName) {
      //
      Path path = Paths.get(fileName);
      File file = new File(fileName);
      //
      int length = (int) file.length();
      byte[] bytes = new byte[length];
      try (FileInputStream fis = new FileInputStream(file);
            BufferedReader br = Files.newBufferedReader(path);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
         // I
         int buffer;
         while ((buffer = fis.read(bytes)) != -1) {
            byteArray.write(bytes, 0, buffer);
         }
      } catch (IOException ioe) {
         lOgger.error(EXCEPTION, ioe);
      }
      return bytes;
   }

   // File encode using Base64
   public static String encodeFile(String fileName) {
      //
      Base64.Encoder encoder = getEncodeType("URL");
      if (null == encoder) {
         throw new SystemException("Base64.Encoder cannot be NULL");
      }
      byte[] encbytes = encoder.encode(getBytes(fileName));
      // Temp to file write
      try (FileOutputStream fos = new FileOutputStream(TARGET_FILE);
            BufferedOutputStream writer = new BufferedOutputStream(fos)) {
         writer.write(encbytes);
      } catch (IOException ioe) {
         lOgger.error(EXCEPTION, ioe);
      }
      //
      try (FileOutputStream fos = new FileOutputStream(FILE_PATH.concat("pdf"));
            BufferedOutputStream writer = new BufferedOutputStream(fos)) {
         encbytes = Base64.getUrlDecoder().decode(encbytes);
         writer.write(encbytes);
      } catch (IOException ioe) {
         lOgger.error(EXCEPTION, ioe);
      }
      return null;
   }

   // File decode using Base64
   public static File decodeFile(FileUpload fileUpload) {
      //
      Base64.Decoder decoder = getDecodeType("URL");
      if (null == decoder) {
         throw new SystemException("Base64.Decoder cannot be NULL");
      }
      byte[] bytes = getBytes(fileUpload.getDocument());
      //
      String fileName = FILE_PATH.concat(fileUpload.getDocumentType());
      //
      try (FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream writer = new BufferedOutputStream(fos)) {
         bytes = decoder.decode(bytes);
         writer.write(bytes);
      } catch (IOException ioe) {
         lOgger.error(EXCEPTION, ioe);
      }
      return Optional.ofNullable(fileName).isPresent() ? new File(fileName) : null;
   }

   // File decode bytes
   public static byte[] decodeFileBytes(FileUpload fileUpload) {
      String document = null;
      //
      if (fileUpload.getDocument().substring(0, 4).equals("data")) {
         document = fileUpload.getDocument().substring(fileUpload.getDocument().indexOf(',') + 1);
      } else {
         document = fileUpload.getDocument();
      }
      return Base64.getDecoder().decode(document);
   }

   public static InputStreamResource getFileInputStreamResource(byte[] bytes) {
      InputStreamResource resource = null;
      try {
         InputStream inputStream = new ByteArrayInputStream(bytes);
         resource = new InputStreamResource(inputStream);
      } catch (Exception e) {
         lOgger.error(EXCEPTION, e);
         lOgger.debug("InputStreamResource exeption : %s%n", e);
      }
      return resource;
   }

   public static FileUpload getFileData() {
      FileUpload fileUpload = new FileUpload();
      fileUpload.setDocumentName(TARGET_FILE);
      fileUpload.setDocumentType("pdf");
      return fileUpload;
   }
}