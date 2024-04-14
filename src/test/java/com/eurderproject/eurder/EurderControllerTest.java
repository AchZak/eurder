package com.eurderproject.eurder;

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


import java.time.LocalDate;
import java.util.Base64;

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
        CreateEurderDto createMemberDto = new CreateEurderDto(
                "member",
                5000
                )

        );
        given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@admin.com", "admin")
                .body(createEurderDto)
                .post("/users/members");

        CreateEurderDto createEurderDto = new CreateEurderDto(
                "librarian",
                "librarian",
                "librarian@email.com",
                "password"

        );
        given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("admin@admin.com", "admin")
                .body(createUserDto)
                .post("/users/librarians");
    }

    @Test
    void shouldCreateEurder_givenValidRequest_ReturnsCreatedStatusAndTotalPrice() throws Exception {
        // Mock your dependencies if needed
        EurderService eurderServiceMock = Mockito.mock(EurderService.class);
        Mockito.when(eurderServiceMock.createEurder(any(), any())).thenReturn(createMockEurderDto());

        // Define the request body
        CreateEurderDto requestDto = new CreateEurderDto(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());

        // Perform the request and validate the response
        Response response = given()
                .contentType(JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth()
                .preemptive()
                .basic("member@email.com", "password")
                .body(LocalDate.of(2024,4,15))
                .when()
                .post("/lendings/isbn")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        Assertions.assertThat(response.as(LendingDto.class).dateDue()).isEqualTo(LocalDate.of(2024,4,15));
    }
    }


}