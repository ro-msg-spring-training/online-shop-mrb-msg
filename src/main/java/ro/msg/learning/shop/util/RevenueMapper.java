package ro.msg.learning.shop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.RevenueDto;
import ro.msg.learning.shop.model.Revenue;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RevenueMapper {

    public RevenueDto toDto(Revenue revenue) {
        return RevenueDto.builder()
                .locationId(revenue.getLocation().getId())
                .sum(revenue.getSum())
                .date(revenue.getDate())
                .build();
    }

    public List<RevenueDto> mapAllToDto(List<Revenue> revenues) {
        return revenues.stream().map(this::toDto).toList();
    }
}
