package com.ngen.cosys.damage.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
//import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;

import com.ngen.cosys.damage.dao.CaptureDamageDAO;
import com.ngen.cosys.damage.model.CaptureDamageDetails;
import com.ngen.cosys.damage.model.CaptureDamageModel;
import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.damage.model.FileUploadModel;
import com.ngen.cosys.damage.model.MailingDetails;
import com.ngen.cosys.damage.model.SearchDamageDetails;
import com.ngen.cosys.damage.model.UploadedFiles;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.AttachmentStream;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.util.FileUploadUtility;

@Service
public class DamageServiceImpl implements DamageService {

   private static final Logger LOGGER = LoggerFactory.getLogger(DamageService.class);

   //private static final String HANDLED_BY_HOUSE = "H";
   
   @Autowired
   private HAWBHandlingHelper hawbHandlingHelper;

   @Autowired
   private SendEmailEventProducer publisher;

   @Autowired
   private CaptureDamageDAO damagedao;
   

   @Override
   //@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = CustomException.class)
   public CaptureDamageModel save(CaptureDamageModel captureDamageModel) throws CustomException {
      // checking for peices it can not be 0
      for (CaptureDamageDetails item : captureDamageModel.getCaptureDetails()) {
         if (item.getDamagePieces().compareTo(BigInteger.valueOf(0)) == 0) {
            throw new CustomException("Damage_pieces", "captureDamageForm", ErrorType.ERROR);
         }
      }

      // checking for duplicate nature of damage pattern
      Map<String, String> duplicateMap = new HashedMap<>();
      for (CaptureDamageDetails item : captureDamageModel.getCaptureDetails()) {
         String key = item.getListNatureOfDamage() != null
               ? item.getListNatureOfDamage().toString() + item.getOccurrence()
               : "";
         //
         if (duplicateMap.get(key) != null) {
            throw new CustomException("duplicate.nature.of.goods", "captureDamageForm", ErrorType.ERROR);
         } else {
            duplicateMap.put(key, "Y");
         }
      }

      damagedao.save(captureDamageModel);

      List<CaptureDamageDetails> createdDamages = captureDamageModel.getCaptureDetails().stream()
            .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())).collect(Collectors.toList());
      
      captureDamageModel.setCaptureDetails(captureDamageModel.getCaptureDetails().stream()
            .filter(a -> a.getFlagCRUD().equalsIgnoreCase(Action.UPDATE.toString())).collect(Collectors.toList()));

      damagedao.delupdCaptureDamageConditions(captureDamageModel);
      if(!CollectionUtils.isEmpty(captureDamageModel.getCaptureDetails())) {
         captureDamageModel.getCaptureDetails().addAll(createdDamages);
      } else {
         captureDamageModel.setCaptureDetails(createdDamages);
      }
      return captureDamageModel;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = CustomException.class)
   public void delete(CaptureDamageDetails captureDamageDetails) throws CustomException {
      damagedao.delete(captureDamageDetails);
   }

   @Override
   public CaptureDamageModel fetch(SearchDamageDetails captureDamageModel) throws CustomException {
      return damagedao.fetch(captureDamageModel);
   }

   @Override
   public List<FileUpload> storeFiles(List<FileUpload> uploadFiles) throws CustomException {
	   for (FileUpload fileUpload : uploadFiles) {
		 
		  
         if (Optional.ofNullable(fileUpload.getEntityKey()).isPresent()) {
            damagedao.delete(fileUpload);
        	 break;
         }
      }
      // Generate Token
      String tokenID = generateFileToken();
      //
      for (FileUpload file : uploadFiles) {
         file.setReferenceId(tokenID.substring(1, 25));
         
      }
      damagedao.storeFiles(uploadFiles);
      return uploadFiles;
   }

   public String generateFileToken() {
      String fileToken = FileUploadUtility.generateUUID();
      try {
         if (damagedao.isTokenExist(fileToken)) {
            // Recursive
            fileToken = generateFileToken();
         }
      } catch (CustomException ex) {
         LOGGER.debug("Generate file Token Stack Overflow Exception: ", ex);
      }
      return fileToken;
   }

   @Override
   public FileUpload deleteFile(FileUpload deleteFile) throws CustomException {
      return damagedao.deleteAFile(deleteFile);
   }

   @Override
   public List<FileUpload> loadListOfFiles(FileUploadModel fileUploadModel) throws CustomException {
      //
      FileUpload file = new FileUpload();
      file.setEntityType(fileUploadModel.getEntityType());
      file.setEntityKey(fileUploadModel.getEntityKey());
      return damagedao.loadFiles(file);
   }

   public void sendEmail(MailingDetails fileUploadModel) throws CustomException {
      publisher.publish(constructMailModel(fileUploadModel));
   }

   private EMailEvent constructMailModel(MailingDetails fileUploadModel) throws CustomException {
      EMailEvent emailEvent = new EMailEvent();

      String[] mailaddressArray = new String[fileUploadModel.getTo().size()];
      emailEvent.setMailToAddress(fileUploadModel.getTo().toArray(mailaddressArray));      
      emailEvent.setMailSubject("INFO ON UPLOADED DOCUMENT FOR " + fileUploadModel.getShipmentNumber());

      Map<String, AttachmentStream> attachments = new HashMap<>();
      AttachmentStream attachment = null;
      //
      for (UploadedFiles file : fileUploadModel.getUploadedFiles()) {
         attachment = new AttachmentStream();
         attachment.setFileId(file.getFileId());
         attachment.setFileName(file.getFileName());
         attachments.put(file.getFileName() + "" + file.getFileId(), attachment);
      }
      emailEvent.setMailAttachments(attachments);
      Map<String, String> params = new HashMap<>();
      params.put("ShipmentNumber", fileUploadModel.getShipmentNumber());
      
      //Get the document name and remarks
      List<CaptureDamageDetails> remarks = damagedao.getDocumentRemarks(fileUploadModel);
      

      // Template Params
    //  TemplateBO template = new TemplateBO();
      //template.setTemplateName("UPLOAD DOCUMENT");
      //template.setTemplateParams(params);
      //emailEvent.setTemplate(template);
     	StringBuilder finalsb = new StringBuilder();
		finalsb.append(TextConstants.HTML);
		finalsb.append(TextConstants.HEAD);
		finalsb.append(TextConstants.STYLE);
		finalsb.append(TextConstants.TABLE);
		finalsb.append(TextConstants.CLOSESTYLE);
		finalsb.append(TextConstants.CLOSE_HEAD);
		finalsb.append(TextConstants.BODY);
		finalsb.append(TextConstants.DIV); 	
		finalsb.append("PLEASE FIND THE ATTACHED DOCUMENTS FOR "+fileUploadModel.getShipmentNumber());
		finalsb.append(TextConstants.CLOSE_DIV);
		finalsb.append("</br></br></br>");
		finalsb.append("*** THIS IS COMPUTER GENERATED EMAIL, PLEASE DO NOT REPLY ***");
		if(!ObjectUtils.isEmpty(remarks)) {
		finalsb.append(TextConstants.DIV);
		finalsb.append(TextConstants.INNERTABLE);
		finalsb.append(TextConstants.THEAD);
		finalsb.append(TextConstants.CLOSE_THEAD);
		
		finalsb.append(TextConstants.ROW);
		finalsb.append(TextConstants.TH + TextConstants.H1 + TextConstants.CLOSE_TH);
		finalsb.append(TextConstants.TH + "Photo Name" + TextConstants.CLOSE_TH);
		finalsb.append(TextConstants.TH + "Remarks" + TextConstants.CLOSE_TH);
		finalsb.append(TextConstants.CLOSEROW);
		int i=1;
		finalsb.append(TextConstants.TBODY);
		for(UploadedFiles data1: fileUploadModel.getUploadedFiles()) {
			
			
			finalsb.append(TextConstants.ROW);
			finalsb.append(TextConstants.COL + i + TextConstants.CLOSE_COL);
			finalsb.append(TextConstants.COL + (data1.getFileName() !=null ? data1.getFileName() : TextConstants.EMPTY )+ TextConstants.CLOSE_COL);
			finalsb.append(TextConstants.COL +( data1.getRemarks() !=null ? data1.getRemarks()  : TextConstants.EMPTY) + TextConstants.CLOSE_COL);
			
			finalsb.append(TextConstants.CLOSEROW);
			
			i++;
		}
		
		finalsb.append(TextConstants.CLOSE_TBODY);
		}
		finalsb.append(TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV); 	
		
		finalsb.append(TextConstants.CLOSE_DIV);
		
		
		finalsb.append(TextConstants.CLOSEBODY);
		finalsb.append(TextConstants.CLOSEHTML);
		
		emailEvent.setMailBody(finalsb.toString());
	
      return emailEvent;
   }

   @Override
   public List<CaptureDamageModel> fetchManifestFlightDetails(SearchDamageDetails captureDamageModel) throws CustomException {
      return damagedao.fetchManifestFlightDetails(captureDamageModel);
   }
   
   @Override
   public List<CaptureDamageModel> fetchManifestFlightDetailsMobile(SearchDamageDetails captureDamageModel) throws CustomException {
      return damagedao.fetchManifestFlightDetailsMobile(captureDamageModel);
   }

   @Override
   public String getCarrierCodeForAMailBag(String mailBag) throws CustomException {

      return damagedao.getCarrierCodeForAMailBag(mailBag);
   }

// send damage info with attached damaged documents!!
	@Override
	public void sendEmailForDamage(MailingDetails uploadedFile) {
		publisher.publish(constructMailModelForDamage(uploadedFile));

	}

	private EMailEvent constructMailModelForDamage(MailingDetails uploadedFile) {
		EMailEvent emailEvent = new EMailEvent();

		String[] mailaddressArray = new String[uploadedFile.getTo().size()];
		emailEvent.setMailToAddress(uploadedFile.getTo().toArray(mailaddressArray));
		emailEvent.setMailSubject("CAPTURE DAMAGE INFO FOR:  " + uploadedFile.getShipmentNumber());

		 Map<String, AttachmentStream> attachments = new HashMap<>();
		 AttachmentStream attachment = null;
		 //
		 for (UploadedFiles file : uploadedFile.getUploadedFiles()) {
		 attachment = new AttachmentStream();
		 attachment.setFileId(file.getFileId());
		 attachment.setFileName(file.getFileName());
		 attachments.put(file.getFileName() + "" + file.getFileId(), attachment);
		 }
		 emailEvent.setMailAttachments(attachments);
		 Map<String, String> params = new HashMap<>();
		 params.put("ShipmentNumber", uploadedFile.getShipmentNumber());

		SearchDamageDetails searchDamageDetails = new SearchDamageDetails();
		searchDamageDetails.setEntityKey(uploadedFile.getShipmentNumber());
		searchDamageDetails.setEntityType(uploadedFile.getType());
		searchDamageDetails.setFlight(uploadedFile.getFlight());
		searchDamageDetails.setFlightDate(uploadedFile.getFlightdate());
		searchDamageDetails.setEntityDate(uploadedFile.getEntityDate());
		try {
			if(isHandledByHouse(searchDamageDetails)) {
				searchDamageDetails.setSubEntityKey(uploadedFile.getSubEntityKey());
			}
		} catch (CustomException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

		CaptureDamageModel captureDamageModel = null;
		String registrationNumber ="";
		try {

			captureDamageModel = fetch(searchDamageDetails);
			if(isHandledByHouse(searchDamageDetails)){
				captureDamageModel.setSubEntityKey(searchDamageDetails.getSubEntityKey());
			}
			
			if(!ObjectUtils.isEmpty(captureDamageModel)) {
			 registrationNumber = damagedao.getRegistrationNumber();
			 }
			
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!ObjectUtils.isEmpty(captureDamageModel)) {
		StringBuilder finalsb = new StringBuilder();
		finalsb.append(TextConstants.HTML);
		finalsb.append(TextConstants.HEAD);
		finalsb.append(TextConstants.STYLE);
		finalsb.append(TextConstants.TABLE);
		finalsb.append(TextConstants.CLOSESTYLE);
		finalsb.append(TextConstants.CLOSE_HEAD);
		finalsb.append(TextConstants.BODY);
		finalsb.append(TextConstants.DIV + TextConstants.EMAILHEAD + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.EMAILHEAD1 + TextConstants.CLOSE_DIV);
	    finalsb.append(TextConstants.DIV+"REGISTRATION NO.  ("+registrationNumber!=null ? registrationNumber : TextConstants.EMPTY  +")"+TextConstants.CLOSE_DIV);

		finalsb.append(TextConstants.CRLF);
		finalsb.append("<div align=\"center\">" + "    " + "REPORT DATE:"
				+TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.getTenantId()).format(MultiTenantUtility.getTenantDateTimeFormat()).toUpperCase()
				+ TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + " " + TextConstants.CLOSE_DIV);
		finalsb.append("<div align=\"center\">" + "     " + "CARGO DAMAGE REPORT" + TextConstants.CLOSE_DIV);
		
		finalsb.append(TextConstants.DIV + "FLIGHT NO/DATE:" + (uploadedFile.getFlight()!=null ? (uploadedFile.getFlight() + "/"): TextConstants.EMPTY) + (uploadedFile.getFlightdate()!= null ? uploadedFile.getFlightdate().format(MultiTenantUtility.getTenantDateFormat()).toUpperCase(): TextConstants.EMPTY)+ TextConstants.CLOSE_DIV);
		
		/// table starts from here.........
		StringBuilder sb = new StringBuilder();
		finalsb.append(TextConstants.CRLF);
		sb.append(TextConstants.HTML);
		sb.append(TextConstants.HEAD);
		sb.append(TextConstants.STYLE);
		sb.append(TextConstants.TABLE1);
		sb.append(TextConstants.CLOSESTYLE);
		sb.append(TextConstants.CLOSE_HEAD);
		sb.append(TextConstants.BODY);
		sb.append(TextConstants.DIV);
		sb.append(TextConstants.INNERTABLE);
		sb.append(TextConstants.THEAD);
		sb.append(TextConstants.CLOSE_THEAD);
		sb.append(TextConstants.THEAD);
		sb.append(TextConstants.ROW);
		sb.append(TextConstants.TH + TextConstants.H1 + TextConstants.CLOSE_TH);
		sb.append(TextConstants.TH + TextConstants.H2 + TextConstants.CLOSE_TH);
		try {
			if(isHandledByHouse(searchDamageDetails)) {
			sb.append(TextConstants.TH + TextConstants.H3 + TextConstants.CLOSE_TH);
			}
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append(TextConstants.TH + TextConstants.H4 + TextConstants.CLOSE_TH);
		sb.append(TextConstants.TH + TextConstants.H5 + TextConstants.CLOSE_TH);
		sb.append(TextConstants.TH + TextConstants.H6 + TextConstants.CLOSE_TH);
		sb.append(TextConstants.TH + TextConstants.H7 + TextConstants.CLOSE_TH);
		sb.append(TextConstants.CLOSEROW);
		sb.append(TextConstants.ROW);
		sb.append(TextConstants.TH + TextConstants.H8 + TextConstants.CLOSE_TH);
		sb.append(TextConstants.CLOSEROW);
		sb.append(TextConstants.CLOSE_THEAD);

		BigInteger SID = BigInteger.ZERO;
        if(!ObjectUtils.isEmpty(captureDamageModel.getCaptureDetails())) {
		for (CaptureDamageDetails ddata : captureDamageModel.getCaptureDetails()) {

			String natureOfDamage = ddata.getListNatureOfDamage().stream().map(s -> s.substring(0, 1))
					.collect(Collectors.joining("/"));

			SID = SID.add(BigInteger.ONE);
			sb.append(TextConstants.TBODY);
			sb.append(TextConstants.ROW);
			sb.append(TextConstants.COL + SID + TextConstants.CLOSE_COL);
			sb.append(TextConstants.COL + captureDamageModel.getEntityKey() + TextConstants.CLOSE_COL);
			try {
				if(isHandledByHouse(searchDamageDetails)) {
				sb.append(TextConstants.COL + captureDamageModel.getSubEntityKey() + TextConstants.CLOSE_COL);
				}
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sb.append(TextConstants.COL + ddata.getDamagePieces() + TextConstants.CLOSE_COL);
			sb.append(TextConstants.COL + captureDamageModel.getContent() + TextConstants.CLOSE_COL);
			sb.append(TextConstants.COL + (ddata.getSeverity() != null ? ddata.getSeverity() : TextConstants.EMPTY)
					+ "/" + (ddata.getOccurrence() != null ? ddata.getOccurrence() : TextConstants.EMPTY)
					+ TextConstants.CLOSE_COL);
			sb.append(TextConstants.COL + natureOfDamage + TextConstants.CLOSE_COL);
			sb.append(TextConstants.CLOSEROW);
			sb.append(TextConstants.DIV);
			
			 sb.append(TextConstants.COL1 
						+ (ddata.getLineRemarks() != null ?
			 ddata.getLineRemarks() : TextConstants.EMPTY) + TextConstants.CLOSE_COL);
			 sb.append(TextConstants.CLOSE_DIV);

		}
        }
		sb.append(TextConstants.CLOSE_TBODY);
		sb.append(TextConstants.CLOSEINNERTABLE);
		sb.append(TextConstants.CLOSE_DIV);
		sb.append(TextConstants.CLOSEBODY);
		sb.append(TextConstants.CLOSEHTML);
		finalsb.append(sb);
		finalsb.append(TextConstants.CRLF);
		finalsb.append(TextConstants.DIV + TextConstants.FOOTER_CONTENT1 + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.CRLF);
		finalsb.append(TextConstants.DIV + TextConstants.LEGEND + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.CRUSHED + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.FOREIGNTAPING + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.OTHERS + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.PUNCTURED + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.OPENED + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.TORN + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.WET + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + " " + TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + "REPORT PREPARED BY :" + uploadedFile.getUser() + " "
				+ TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.getTenantId()).format(MultiTenantUtility.getTenantDateTimeFormat()).toUpperCase() + " "
				+ TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.CRLF);
		finalsb.append(TextConstants.DIV + TextConstants.FOOTER_CONTENT2 + TextConstants.FOOTER_CONTENT3
				+ TextConstants.CLOSE_DIV);
		finalsb.append(TextConstants.DIV + TextConstants.END + TextConstants.CLOSE_DIV);
		//
		finalsb.append(TextConstants.CLOSEBODY);
		finalsb.append(TextConstants.CLOSEHTML);
		emailEvent.setMailBody(finalsb.toString());
		}
		return emailEvent;

	}

   @Override
   public String getDamageCodeForMail(String natureOfDamage) throws CustomException {
      // TODO Auto-generated method stub
      return damagedao.getDamageCodeForMail(natureOfDamage);
   }
   
   
   // To check if handled by house or handled by master
	@Override
	public boolean isHandledByHouse(SearchDamageDetails searchDamageDetails) throws CustomException { 
		
		HAWBHandlingHelperRequest hAWBHandlingHelperRequest = new HAWBHandlingHelperRequest();
		hAWBHandlingHelperRequest.setShipmentNumber(searchDamageDetails.getEntityKey());
		hAWBHandlingHelperRequest.setShipmentDate(searchDamageDetails.getEntityDate());
		return hawbHandlingHelper.isHandledByHouse(hAWBHandlingHelperRequest);
}


}