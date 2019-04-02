package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/*
-Xms512m -Xmx512m -verbose:gc

UseConcMarkSweepGC - 131.540s
'ParNew', quantity=12, time=0 ms
'ConcurrentMarkSweep', quantity=28, time=34 ms

UseG1GC - 106.435s
'G1 Young Generation', quantity=44, time=0 ms
'G1 Old Generation', quantity=10, time=1 ms

UseParallelGC - 1365.834s (не дождался окончания)
Hi proc load
'PS Scavenge', quantity=9, time=1 ms
'PS MarkSweep', quantity=19, time=9 ms

UseSerialGC - 109.816s
'Copy', quantity=8, time=0 ms
'MarkSweepCompact', quantity=12, time=5 ms

Каждый GC хорош в своем (в моей ситуации - кроме Parallel),
но лично в конкретной ситуации мне понравился больше G1.
Он хоть и запускался больше, но при этом был самым быстрым и меньше всего работал.
Parallel нагрузил процессоры так, что аж страшно было
ConcMarkSweep был самым медленным
Serial - уверенный середнячок
 */

public class GcDemo {
    private static final Logger log = LoggerFactory.getLogger(GcDemo.class);
    private static List<GcStat> gcStats;

    public static void main(String... args) throws Exception {
        log.info("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        int size = 5 * 1000 * 1000;
        //int loopCounter = 100;
        int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        mbean.run();

        log.info("time:" + (System.currentTimeMillis() - beginTime) / 1000);
        gcStats.forEach(System.out::println);
    }

    private static void switchOnMonitoring() {
        gcStats = new ArrayList<>();
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            GcStat gcStat = new GcStat(gcBean.getName());
            gcStats.add(gcStat);
            log.info("GC name:" + gcBean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    gcStat.increase(duration);

                    log.info("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    log.info(gcStat.toString());
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }
}