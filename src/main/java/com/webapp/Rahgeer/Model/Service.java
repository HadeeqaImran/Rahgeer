package com.webapp.Rahgeer.Model;

public class Service {
    private int serviceID;
    private String serviceName;

    public Service(int serviceID, String serviceName) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
