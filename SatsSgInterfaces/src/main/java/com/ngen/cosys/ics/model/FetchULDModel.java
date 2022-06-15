package com.ngen.cosys.ics.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.ngen.cosys.validation.groups.FetchULDValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class FetchULDModel {

   @NotBlank(message = "terminal.id.cant.be.blank", groups = FetchULDValidationGroup.class)
   @Length(max = 10, message = "terminal.id.cant.be.more.than", groups = {
         FetchULDValidationGroup.class })
   private String terminalId;
}