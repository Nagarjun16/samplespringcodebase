package com.ngen.cosys.icms.model.flightbooking;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;

@NgenCosysAppAnnotation
@NgenAudit(entityFieldName = "awb", entityRefFieldName = "awbDate", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FFM, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "awb", entityRefFieldName = "awbDate", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FBL, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTKEY, entityRefFieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTDATE, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ASM, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTKEY, entityRefFieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTDATE, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.SSM, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTKEY, entityRefFieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTDATE, entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_CREATION, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(eventName = NgenAuditEventType.FLIGHT_MAINTAIN_SCHEDULE, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = NgenAuditFieldNameType.FLIGHTKEY, entityType = NgenAuditEntityType.FLIGHT)
public class FlightBookingInfo extends BaseBO {

	private static final long serialVersionUID = 1L;
	private int referenceId;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.CARRIER)
	private String carrier;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_TYPE)
	private String flightType;
	private String flightNo;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTDATEORIGIN)
	private LocalDate flightDate;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTKEY)
	private String flightKey;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDPOINT)
	private String loadingPoint;

	private boolean bookingExists;

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.AIRCRAFTREGISTRATION)
	private String aircraftRegistrationNo;
	private String previousAircraftRegCode;

	private String siType = "I";

	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.ETD)
	private String etd;
	private LocalDateTime flightDateSta;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPOINT)
	private String firstPointArrival;

// Added ASM message
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICETYPE)
	private String serviceType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRCRAFTTYPE)
	private String aircraftType;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKSFORASM)
	private List<String> remarksForASM;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TECHNICALSTOP)
	private boolean technicalStop;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.NOFREIGHT)
	private boolean noFreight;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TERMINALS)
	private List<String> handlingArea;

	private String dateSTD;
	private String dateSTA;

	private BigInteger bookingId;

	private BigInteger flightId;

	private String flightSegmentId;

	private String messageByFlightId;

	private int flightSegmentOrder;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.STATUS)
	private String status;

	private String flightStatus;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTLEGS)
	private List<OperationalFlightLegInfo> flightLegInfo;

	

	private String flightRemark;

	private String ImpFlightEventsId;

	private String ExpFlightEventsId;

	private String inboundAircraftRegNo;
	private String outboundAircraftRegNo;
	private String flightAutoCompleteFlag;
	private String flightCancelFag;

	public int getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightKey() {
		return flightKey;
	}

	public void setFlightKey(String flightKey) {
		this.flightKey = flightKey;
	}

	public String getLoadingPoint() {
		return loadingPoint;
	}

	public void setLoadingPoint(String loadingPoint) {
		this.loadingPoint = loadingPoint;
	}

	public boolean isBookingExists() {
		return bookingExists;
	}

	public void setBookingExists(boolean bookingExists) {
		this.bookingExists = bookingExists;
	}

	public String getAircraftRegistrationNo() {
		return aircraftRegistrationNo;
	}

	public void setAircraftRegistrationNo(String aircraftRegistrationNo) {
		this.aircraftRegistrationNo = aircraftRegistrationNo;
	}

	public String getPreviousAircraftRegCode() {
		return previousAircraftRegCode;
	}

	public void setPreviousAircraftRegCode(String previousAircraftRegCode) {
		this.previousAircraftRegCode = previousAircraftRegCode;
	}

	public String getSiType() {
		return siType;
	}

	public void setSiType(String siType) {
		this.siType = siType;
	}

	public String getEtd() {
		return etd;
	}

	public void setEtd(String etd) {
		this.etd = etd;
	}

	public LocalDateTime getFlightDateSta() {
		return flightDateSta;
	}

	public void setFlightDateSta(LocalDateTime flightDateSta) {
		this.flightDateSta = flightDateSta;
	}

	public String getFirstPointArrival() {
		return firstPointArrival;
	}

	public void setFirstPointArrival(String firstPointArrival) {
		this.firstPointArrival = firstPointArrival;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public List<String> getRemarksForASM() {
		return remarksForASM;
	}

	public void setRemarksForASM(List<String> remarksForASM) {
		this.remarksForASM = remarksForASM;
	}

	public boolean isTechnicalStop() {
		return technicalStop;
	}

	public void setTechnicalStop(boolean technicalStop) {
		this.technicalStop = technicalStop;
	}

	public boolean isNoFreight() {
		return noFreight;
	}

	public void setNoFreight(boolean noFreight) {
		this.noFreight = noFreight;
	}

	public List<String> getHandlingArea() {
		return handlingArea;
	}

	public void setHandlingArea(List<String> handlingArea) {
		this.handlingArea = handlingArea;
	}


	public BigInteger getBookingId() {
		return bookingId;
	}

	public void setBookingId(BigInteger bookingId) {
		this.bookingId = bookingId;
	}

	public BigInteger getFlightId() {
		return flightId;
	}

	public void setFlightId(BigInteger flightId) {
		this.flightId = flightId;
	}

	public String getFlightSegmentId() {
		return flightSegmentId;
	}

	public void setFlightSegmentId(String flightSegmentId) {
		this.flightSegmentId = flightSegmentId;
	}

	public String getMessageByFlightId() {
		return messageByFlightId;
	}

	public void setMessageByFlightId(String messageByFlightId) {
		this.messageByFlightId = messageByFlightId;
	}

	public int getFlightSegmentOrder() {
		return flightSegmentOrder;
	}

	public void setFlightSegmentOrder(int flightSegmentOrder) {
		this.flightSegmentOrder = flightSegmentOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	public List<OperationalFlightLegInfo> getFlightLegInfo() {
		return flightLegInfo;
	}

	public void setFlightLegInfo(List<OperationalFlightLegInfo> flightLegInfo) {
		this.flightLegInfo = flightLegInfo;
	}

	public String getFlightRemark() {
		return flightRemark;
	}

	public void setFlightRemark(String flightRemark) {
		this.flightRemark = flightRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	public String getExpFlightEventsId() {
		return ExpFlightEventsId;
	}

	public void setExpFlightEventsId(String expFlightEventsId) {
		ExpFlightEventsId = expFlightEventsId;
	}

	public String getImpFlightEventsId() {
		return ImpFlightEventsId;
	}

	public void setImpFlightEventsId(String impFlightEventsId) {
		ImpFlightEventsId = impFlightEventsId;
	}

	public String getFlightAutoCompleteFlag() {
		return flightAutoCompleteFlag;
	}

	public void setFlightAutoCompleteFlag(String flightAutoCompleteFlag) {
		this.flightAutoCompleteFlag = flightAutoCompleteFlag;
	}

	public String getOutboundAircraftRegNo() {
		return outboundAircraftRegNo;
	}

	public void setOutboundAircraftRegNo(String outboundAircraftRegNo) {
		this.outboundAircraftRegNo = outboundAircraftRegNo;
	}

	public String getInboundAircraftRegNo() {
		return inboundAircraftRegNo;
	}

	public void setInboundAircraftRegNo(String inboundAircraftRegNo) {
		this.inboundAircraftRegNo = inboundAircraftRegNo;
	}
	
	public String getFlightCancelFag() {
		return flightCancelFag;
	}

	public void setFlightCancelFag(String flightCancelFag) {
		this.flightCancelFag = flightCancelFag;
	}

	public String getDateSTD() {
		return dateSTD;
	}

	public void setDateSTD(String dateSTD) {
		this.dateSTD = dateSTD;
	}

	public String getDateSTA() {
		return dateSTA;
	}

	public void setDateSTA(String dateATD) {
		this.dateSTA = dateATD;
	}

	@Override
	public String toString() {
		return "OperationalFlightInfo [referenceId=" + referenceId + ", carrier=" + carrier + ", flightType="
				+ flightType + ", flightNo=" + flightNo + ", flightDate=" + flightDate + ", flightKey=" + flightKey
				+ ", loadingPoint=" + loadingPoint + ", bookingExists=" + bookingExists + ", aircraftRegistrationNo="
				+ aircraftRegistrationNo + ", previousAircraftRegCode=" + previousAircraftRegCode + ", siType=" + siType
				+ ", etd=" + etd + ", flightDateSta=" + flightDateSta + ", firstPointArrival=" + firstPointArrival
				+ ", serviceType=" + serviceType + ", aircraftType=" + aircraftType + ", remarksForASM=" + remarksForASM
				+ ", technicalStop=" + technicalStop + ", noFreight=" + noFreight + ", handlingArea=" + handlingArea
				+ ",  bookingId=" + bookingId + ", flightId=" + flightId
				+ ", flightSegmentId=" + flightSegmentId + ", messageByFlightId=" + messageByFlightId
				+ ", flightSegmentOrder=" + flightSegmentOrder + ", status=" + status + ", flightStatus=" + flightStatus
				+ ", flightLegInfo=" + flightLegInfo + ", flightRemark=" + flightRemark + "]";
	}

	

}