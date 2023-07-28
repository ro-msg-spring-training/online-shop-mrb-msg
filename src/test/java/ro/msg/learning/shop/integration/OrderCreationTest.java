package ro.msg.learning.shop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ro.msg.learning.shop.ShopApplication;
import ro.msg.learning.shop.dto.CreateOrderDto;
import ro.msg.learning.shop.dto.ProductQuantityDto;
import ro.msg.learning.shop.service.OrderService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    public ObjectMapper mapper;

    @Before
    public void beforeEachTest() throws Exception {

        mvc.perform(post("/test/populate")
                .with(httpBasic("Mira", "1234"))
                .with(csrf()));
    }

    @After
    public void after() throws Exception {

        mvc.perform(get("/test/clear")
                .with(httpBasic("Mira", "1234")));
    }

    @Test
    public void testCreateOrder_withoutStock_shouldThrowException() throws Exception {

        List<ProductQuantityDto> wantedProducts = List.of(ProductQuantityDto.builder().productId(bread.getId()).quantity(200).build());

        var createOrderDto = CreateOrderDto.builder()
                .createdOn(LocalDate.now())
                .products(wantedProducts)
                .country("ro")
                .city("ar")
                .details("")
                .build();

        var ow = mapper.writer().withDefaultPrettyPrinter();
        var request = ow.writeValueAsString(createOrderDto);

        var result = mvc.perform(post("/orders")
                .with(httpBasic("Mira", "1234"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)).andReturn();

        String response = result.getResponse().getContentAsString();

        Assert.assertEquals(response, "No stocks available for the requested products.");

    }

    @Test
    public void testCreateOrder_withStock_shouldCreateOrder() throws Exception {

        List<ProductQuantityDto> wantedProducts = List.of(ProductQuantityDto.builder().productId(bread.getId()).quantity(1).build());

        var createOrderDto = CreateOrderDto.builder()
                .createdOn(LocalDate.now())
                .products(wantedProducts)
                .country("ro")
                .city("ar")
                .details("")
                .build();

        var ow = mapper.writer().withDefaultPrettyPrinter();
        var request = ow.writeValueAsString(createOrderDto);
        var result = mvc.perform(post("/orders")
                        .with(httpBasic("Mira", "1234"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        var createdOrder = mapper.readValue(result.getResponse().getContentAsString(), ro.msg.learning.shop.dto.OrderDto.class);

        Assert.assertNotNull(createdOrder);

    }
}
