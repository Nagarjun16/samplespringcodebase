package com.ngen.cosys.impbd.model;

import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.model.SegmentModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class ArrivalManifestModel extends BaseBO {
   
   private static final long serialVersionUID = 1L;
   
   private String flightNumber;
   private LocalDate flightDate;
   private List<SegmentModel> segments;
   
}
