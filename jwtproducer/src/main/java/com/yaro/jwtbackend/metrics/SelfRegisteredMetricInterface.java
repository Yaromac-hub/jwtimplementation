package com.yaro.jwtbackend.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public interface SelfRegisteredMetricInterface {

    Logger log = LoggerFactory.getLogger(SelfRegisteredMetricInterface.class);

    /**
     * Registers the metric with the provided ObjectName.
     *
     * @param objName The ObjectName to register.
     */
    default void registerMetric(ObjectName objName){
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            if(!server.isRegistered(objName)) {
                server.registerMBean(this, objName);
            }
            else if(server.getObjectInstance(objName).getClassName().equals(this.getClass().getName())){
                server.unregisterMBean(objName);
                server.registerMBean(this, objName);
                log.warn( "Metric with the same name already exist in the context - "+ objName.getCanonicalName());
            }
            else{
                log.error( "Metric with the same name already exist in the context - "+ objName.getCanonicalName());
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

}
