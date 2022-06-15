package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.parsers;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.cxml.response.ParserError;
import com.ngen.cosys.message.enums.TextMessageConstants;
import com.ngen.cosys.satssg.interfaces.util.MessageTypeIdentifier;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;

@Component("fnaOutboundConstructor")
public class FNAOutboundConstructor {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(FNAOutboundConstructor.class);
         
   /**
    * Method to construct FNA messsage
    * 
    * @param fna
    * @return String
    */
   public String constructFNA(String fna, MessageParsingException e, MessageTypeIdentifier messageTypeIdentifier) {
      // CMA & FNA Recipient Address Logic
      String cmaAddress = messageTypeIdentifier.getCMARecipientAddress();
      String fnaAddress = messageTypeIdentifier.getFNARecipientAddress();
      //
      CharSequence crlfCharSequence = new String(new char[] { 0x0d, 0x0a });
      String header = fna.substring(0, fna.indexOf("CMD"));
      String withoutHeader = fna.substring(fna.indexOf("CMD"));
      // Replace 
      if (!Objects.equals(cmaAddress, fnaAddress)) {
         header.replace(cmaAddress, fnaAddress);
      }
      StringBuilder fnaStringBuilder = new StringBuilder(header);
      // Add line identifier
      fnaStringBuilder.append(TextMessageConstants.FNA_IDENTIFIER.getValue());
      fnaStringBuilder.append(crlfCharSequence);
      // ACK identifier7
      fnaStringBuilder.append(TextMessageConstants.ACK_IDENTIFIER.getValue());
      // Add all errors recorded duing message parsing
      for (ParserError t : e.getParserError()) {
         fnaStringBuilder.append(TextMessageConstants.SLANT_IDENTIFIER.getValue());
         fnaStringBuilder.append(t.getDescription());
         fnaStringBuilder.append(crlfCharSequence);
      }
      //fnaStringBuilder.append(withoutHeader);
      //Generate list of string for a given message
      String[] lines = withoutHeader.split("\\r?\\n");      
      for (String line : lines) {
         fnaStringBuilder.append(StringUtils.isEmpty(line) ? line : line.trim());
         fnaStringBuilder.append(crlfCharSequence);
      }
      //String fnaMessage = fnaStringBuilder.toString();
      //LOGGER.warn("FNA Message Constructor String - Before Apply Regex :: {}", fnaMessage);
      //fnaMessage = fnaMessage.replaceAll("(?m)^[ \t]*\r?\n", "");
      LOGGER.warn("FNA Message Constructor String - After Apply Regex :: {}", fnaStringBuilder.toString());
      return fnaStringBuilder.toString();
   }
   
}
