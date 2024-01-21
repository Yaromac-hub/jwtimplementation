package com.yaro.jwtbackend.metrics;

import javax.management.ObjectName;
import java.util.concurrent.atomic.AtomicLong;

public class Gauge implements GaugeMXBeanInterface{
    private AtomicLong value = new AtomicLong(0);

    public static Gauge register(ObjectName objectName){
        return new Gauge(objectName);
    }

    private Gauge(ObjectName objectName) {
        registerMetric(objectName);
    }

    public long inc() {
        return value.incrementAndGet();
    }

    public long inc(long iVal) {
        return value.addAndGet(iVal);
    }

    public long dec() {
        return value.decrementAndGet();
    }

    public long dec(long dVal) {
        return value.addAndGet(-dVal);
    }

    @Override
    public long getValue() {
        return value.get();
    }

}
