/**
 * 
 * CellAlignment.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 MAR, 2019   NIIT      -
 */
package com.ngen.cosys.report.service.poi.util;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cell Alignments
 *
 * @author NIIT Technologies Ltd
 * 
 */
@Getter
@Setter
@NoArgsConstructor
public class CellAlignment {

   private HorizontalAlignment horizontal;
   private VerticalAlignment vertical;
   private boolean wrapText;
   
}
