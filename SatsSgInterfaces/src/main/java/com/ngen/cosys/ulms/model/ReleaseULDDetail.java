package com.ngen.cosys.ulms.model;

import com.ngen.cosys.framework.model.BaseBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReleaseULDDetail extends BaseBO {
    private String customerCode;
    private String customerName;
    private Long customerId;
    private LocalDateTime releaseDate;
    private String uldNumbers;
    private String startDate;
    private String endDate;

}
