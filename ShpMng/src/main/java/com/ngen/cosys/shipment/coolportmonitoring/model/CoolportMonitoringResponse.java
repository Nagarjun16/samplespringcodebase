package com.ngen.cosys.shipment.coolportmonitoring.model;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class CoolportMonitoringResponse extends BaseBO {
   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 1L;

   private List<CoolportMonitoringDetail> coolportMonitoringDetailData;
}