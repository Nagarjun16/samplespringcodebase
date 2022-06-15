package com.ngen.cosys.satssg.common.dao;

import java.time.LocalDate;

public interface ShipmentDAO {

   LocalDate getShipmentDate(String shipmentNumber);
   
}
