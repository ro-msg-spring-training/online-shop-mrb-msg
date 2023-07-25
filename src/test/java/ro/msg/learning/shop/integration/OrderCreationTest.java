package ro.msg.learning.shop.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.ProductQuantityDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.msg.learning.shop.service.TestService.TestService.bread;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties", properties = {"strategy.type=SINGLE"})
@ActiveProfiles("test")
public class OrderCreationTest {

    @Autowired
    public MockMvc mvc;

    @BeforeEach
    public void beforeEachTest() throws Exception {
        mvc.perform(get("/test/populate")).andExpect(status().isOk());
    }

    @AfterEach
    public void after() throws Exception {
        mvc.perform(get("/test/clear")).andExpect(status().isOk());
    }

    @Test
    void testCreateOrder_withoutStock_shouldThrowException() {
        Set<ProductQuantityDto> wantedProducts = Set.of(ProductQuantityDto.builder().productId(bread.getId()).build());

        var newOrder = OrderDto.builder()
                .createdOn(LocalDate.now())
                .build();


    }
}
