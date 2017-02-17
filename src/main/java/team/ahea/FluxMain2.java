package team.ahea;

import ch.qos.logback.core.util.TimeUtil;
import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxMain2 {

    public static void main(String[] args) throws InterruptedException {

        Flux.interval(Duration.ofMillis(500))
            .subscribe(s->log.debug("onNext:{}", s));

        log.debug("exit");

        TimeUnit.SECONDS.sleep(5);

    }

}
