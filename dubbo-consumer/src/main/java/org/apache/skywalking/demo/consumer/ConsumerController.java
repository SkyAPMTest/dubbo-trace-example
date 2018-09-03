package org.apache.skywalking.demo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.apache.skywalking.demo.interfaces.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    private static int COUNT = 0;

    @Reference(version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        url = "dubbo://localhost:20880", timeout = 60000)
    private HelloService helloService;

    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable(name = "name") String name) {
        if ((COUNT++) % 3 == 0){
            throw new RuntimeException();
        }
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
        return helloService.sayHello(name);
    }
}
