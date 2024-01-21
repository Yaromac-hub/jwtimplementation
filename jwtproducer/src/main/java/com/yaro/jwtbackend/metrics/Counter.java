package com.yaro.jwtbackend.metrics;

import javax.management.ObjectName;
import java.util.concurrent.atomic.AtomicLong;

public class Counter implements CountMXBeanInterface{
    private AtomicLong total = new AtomicLong(0);

    public static Counter register(ObjectName objectName){
        return new Counter( objectName);
    }

    private Counter(ObjectName objectName) {
        registerMetric(objectName);
    }

    public long inc() {
        return total.incrementAndGet();
    }

    public long inc(long value) {
        return total.addAndGet(value);
    }

    @Override
    public long getTotal() {
        return total.get();
    }

}
