package com.ngen.cosys.impbd.instruction.model;

import javax.validation.constraints.Pattern;

import com.ngen.cosys.framework.model.BaseBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HouseListModel extends BaseBO {
    /**
	 * Default
	 */
	private static final long serialVersionUID = 1L;
	private String houseNumber;
    @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters")
    private String breakdownInstruction;
}
