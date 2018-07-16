package cn.mine.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {

    @GetMapping("/test")
    public String test() {
        log.info("test方法执行");
        return "test方法";
    }
}
