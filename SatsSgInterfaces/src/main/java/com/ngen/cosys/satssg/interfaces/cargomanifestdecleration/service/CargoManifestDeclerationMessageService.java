package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface CargoManifestDeclerationMessageService {

   public ResponseEntity<String> processMessages(String customIncomingCargoManifestDeclarationContentModel,
         HttpServletRequest request) throws Exception;

}