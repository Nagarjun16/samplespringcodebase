package com.ngen.cosys.satssg.interfaces.psn.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.satssg.interfaces.util.MessageEnvelop;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class PsnMessageModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private List<AirwayBillIdentification> airwayBillIdentifications;
   private String identifiers;
   private String acknowledge;
   private String ackCode;
   private String hldAckCode;
   private String ackDes;
   private String cusStdCod;
   private String cusStdNo;
   private String ipsnDat;

   private String ipsnHLdDes;

   private String noOfPieces;

   private String transDateTime;
   private LocalDateTime transactionDateTime;
   private String entryType;

   private String entryNo;

   private String remark;

   private MessageEnvelop messageEnvelop;

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "PsnMessageModel [airwayBillIdentifications=" + airwayBillIdentifications + ", identifiers=" + identifiers
            + ", acknowledge=" + acknowledge + ", ackCode=" + ackCode + ", hldAckCode=" + hldAckCode + ", ackDes="
            + ackDes + ", cusStdCod=" + cusStdCod + ", cusStdNo=" + cusStdNo + ", ipsnDat=" + ipsnDat + ", ipsnHLdDes="
            + ipsnHLdDes + ", noOfPieces=" + noOfPieces + ", transDateTime=" + transDateTime + ", entryType="
            + entryType + ", entryNo=" + entryNo + ", remark=" + remark + "]";
   }
}
