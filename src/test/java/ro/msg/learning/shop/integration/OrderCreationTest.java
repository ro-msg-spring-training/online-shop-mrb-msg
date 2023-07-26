package ro.msg.learning.shop.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ro.msg.learning.shop.ShopApplication;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.exception.NoStocksAvailableException;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.service.OrderService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.msg.learning.shop.util.TestDataUtil.bread;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShopApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class OrderCreationTest {

    @Autowired
    public MockMvc mvc;
    @Autowired
    private OrderService orderService;

    @Before
    public void beforeEachTest() throws Exception {
        mvc.perform(get("/test/populate")).andExpect(status().isOk());
    }

    @After
    public void after() throws Exception {
        mvc.perform(get("/test/clear")).andExpect(status().isOk());
    }

    @Test(expected = NoStocksAvailableException.class)
    public void testCreateOrder_withoutStock_shouldThrowException() {

        List<ProductQuantityDto> wantedProducts = List.of(ProductQuantityDto.builder().productId(bread.getId()).quantity(200).build());

        Set<OrderDetail> orderDetails = new HashSet<>();

        orderDetails.add(OrderDetail.builder().product(bread).quantity(200).build());

        var createOrder = Order.builder()
                .createdOn(LocalDate.now())
                .orderDetails(orderDetails)
                .deliveryAddress(Address
                        .builder()
                        .country("ro")
                        .city("ar")
                        .details("")
                        .build())
                .build();

        orderService.createOrder(createOrder, wantedProducts);

    }

    @Test
    public void testCreateOrder_withStock_shouldCreateOrder() {

        List<ProductQuantityDto> wantedProducts = List.of(ProductQuantityDto.builder().productId(bread.getId()).quantity(1).build());

        Set<OrderDetail> orderDetails = new HashSet<>();

        orderDetails.add(OrderDetail.builder().product(bread).quantity(1).build());

        var createOrder = Order.builder()
                .createdOn(LocalDate.now())
                .orderDetails(orderDetails)
                .deliveryAddress(Address
                        .builder()
                        .country("ro")
                        .city("ar")
                        .details("")
                        .build())
                .build();

        Order createdOrder = orderService.createOrder(createOrder, wantedProducts);

        Assert.assertNotNull(createdOrder);

    }
}
