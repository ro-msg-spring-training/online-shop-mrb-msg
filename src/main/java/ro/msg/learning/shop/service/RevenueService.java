package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RevenueService {
    private final RevenueRepository revenueRepository;
    private final LocationRepository locationRepository;

    public List<Revenue> getAllRevenues(LocalDate date) {
        return revenueRepository.findAllByDate(date);
    }

    @Transactional
    @Scheduled(cron = "${revenue.cron.expression}")
    public void saveAllRevenuesForToday() {

        LocalDate today = LocalDate.now();
        var todaySRevenues = revenueRepository.findAllRevenuesPerLocationForDate(today);
        var revenues = todaySRevenues.stream().map(r -> Revenue.builder()
                .location(locationRepository.findById(r.getLocationId()).get())
                .sum(r.getSum())
                .date(today)
                .build()).toList();
        if (!revenues.isEmpty()) {
            revenueRepository.saveAll(revenues);
        }
    }

}