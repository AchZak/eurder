package com.eurderproject.eurder;

import com.eurderproject.item.Item;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EurderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    EurderService eurderService;

    @BeforeEach
    void setUp() {
        Item item1 = new Item("Smartphone", "High Tech Smartphone", 1000, 100);
        Item item2 = new Item("Laptop", "High Tech Laptop", 5000, 50);
        ItemGroup itemGroup1 = new ItemGroup(item1.getItemId(), 2);
        ItemGroup itemGroup2 = new ItemGroup(item2.getItemId(),3);
        List<ItemGroup> itemGroups = List.of(itemGroup1,itemGroup2);
        Eurder eurder = new Eurder(UUID.randomUUID(),itemGroups);
        CreateEurderDto createEurderDto = new CreateEurderDto(
                itemGroups,
                17000);
        given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("mariokart", "trophy")
                .body(createEurderDto)
                .post("/eurders");
    }

    @Test
    void shouldCreateEurder_givenValidRequest_ReturnsCreatedStatusAndTotalPrice(){
        Item item1 = new Item("Smartphone", "High Tech Smartphone", 1000, 100);
        Item item2 = new Item("Laptop", "High Tech Laptop", 5000, 50);
        ItemGroup itemGroup1 = new ItemGroup(item1.getItemId(), 2);
        ItemGroup itemGroup2 = new ItemGroup(item2.getItemId(),3);
        List<ItemGroup> itemGroups = List.of(itemGroup1,itemGroup2);
        Eurder eurder = new Eurder(UUID.randomUUID(),itemGroups);
        Response response = given()
                .contentType(JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .when()
                .post("/eurders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        Assertions.assertThat(response.as(EurderService.class).calculateEurderPrice(eurder)).isEqualTo(17000);
    }
}