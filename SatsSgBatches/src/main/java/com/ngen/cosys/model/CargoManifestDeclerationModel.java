package com.ngen.cosys.model;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.validation.annotation.Validated;
import com.ngen.cosys.framework.model.BaseBO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class CargoManifestDeclerationModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;
   private String shipmentNumber;
   private BigInteger customsFlightId;
   private BigInteger LarType;
   private BigInteger Pieces;
}
