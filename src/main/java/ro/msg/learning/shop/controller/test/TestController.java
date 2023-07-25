package ro.msg.learning.shop.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.service.TestService.TestService;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/populate")
    @ResponseStatus(HttpStatus.OK)
    public void populate() {
        testService.populateData();
    }

    @GetMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        testService.clearData();
    }

}
