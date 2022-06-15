package com.ngen.cosys.ulms.exception;

import com.ngen.cosys.ulms.model.ServiceStatus;

public class ServiceFaultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String NAMESPACE_URI = "http://www.sats.com.sg/uldAssociationWebservice/exception";

    private ServiceStatus serviceStatus;

    public ServiceFaultException(String message, ServiceStatus serviceStatus) {
        super(message);
        this.serviceStatus = serviceStatus;
    }

    public ServiceFaultException(String message, Throwable e, ServiceStatus serviceStatus) {
        super(message, e);
        this.serviceStatus = serviceStatus;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

}