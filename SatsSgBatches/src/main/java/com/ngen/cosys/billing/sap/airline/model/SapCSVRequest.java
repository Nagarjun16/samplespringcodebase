package com.ngen.cosys.billing.sap.airline.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SapCSVRequest {
private List<Integer> ids;
private LocalDateTime delaytime;
}
