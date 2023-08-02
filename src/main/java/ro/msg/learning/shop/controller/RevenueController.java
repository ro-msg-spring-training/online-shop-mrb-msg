package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.RevenueDto;
import ro.msg.learning.shop.service.RevenueService;
import ro.msg.learning.shop.util.RevenueMapper;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/revenues")
public class RevenueController {

    private final RevenueService revenueService;
    private final RevenueMapper revenueMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RevenueDto> getRevenuesForDate(@RequestParam LocalDate date) {
        return revenueMapper.mapAllToDto(revenueService.getAllRevenues(date));
    }

}
