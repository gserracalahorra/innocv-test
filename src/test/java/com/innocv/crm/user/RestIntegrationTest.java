package com.innocv.crm.user;

import com.innocv.crm.user.controller.model.User;
import com.innocv.crm.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestIntegrationTest {

    public static final User USER = new User("123", "Guillem", LocalDate.now());
    @LocalServerPort
    private int port;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getAllUsersOkTest() {

        when(userRepository.findAll()).thenReturn(Flux.just(USER));

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    public void getAllUsersNotFoundTest() {

        when(userRepository.findAll()).thenReturn(Flux.empty());

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .statusCode(is(204));
    }

    @Test
    public void getAllUsersInternalServerErrorTest() {

        when(userRepository.findAll()).thenThrow(mock(RuntimeException.class));

        given().port(port).get("/v1/crm/user/all").then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void getUserOkTest() {

        when(userRepository.findById(anyString())).thenReturn(Mono.just(USER));

        given().port(port).get("/v1/crm/user/12345678").then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    public void getUserResourceNotFoundTest() {

        when(userRepository.findById(anyString())).thenReturn(Mono.empty());

        given().port(port).get("/v1/crm/user/12345678").then()
                .assertThat()
                .statusCode(is(404));
    }

    @Test
    public void getUserInternalServerErrorTest() throws Exception {

        when(userRepository.findById(anyString())).thenThrow(mock(RuntimeException.class));

        given().port(port).get("/v1/crm/user/12345678").then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void saveSuccessTest() {

        when(userRepository.save(any())).thenReturn(Mono.just(USER));

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new User(null, "Guillem Serra", LocalDate.now()))
                .post("/v1/crm/user")
                .then()
                .assertThat()
                .statusCode(is(201));
    }

    @Test
    public void saveInternalServerErrorTest() {

        when(userRepository.save(any())).thenThrow(mock(RuntimeException.class));

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new User(null, "Guillem Serra", LocalDate.now()))
                .post("/v1/crm/user")
                .then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void updateSuccessTest() {

        when(userRepository.findById(anyString())).thenReturn(Mono.just(USER));

        when(userRepository.save(any())).thenReturn(Mono.just(USER));

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new User(null, "Guillem Serra", LocalDate.now()))
                .put("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    public void updateNotFoundTest() {

        when(userRepository.findById(anyString())).thenReturn(Mono.empty());

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new User(null, "Guillem Serra", LocalDate.now()))
                .put("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(404));
    }

    @Test
    public void updateInternalServerErrorTest() {

        when(userRepository.save(any())).thenThrow(mock(RuntimeException.class));

        given().port(port)
                .header("Content-Type", "application/json")
                .body(new User(null, "Guillem Serra", LocalDate.now()))
                .put("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(500));
    }

    @Test
    public void deleteSuccessTest() {

        when(userRepository.delete(any())).thenReturn(Mono.empty());

        given().port(port)
                .delete("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    public void deleteInternalServerErrorTest() {

        when(userRepository.deleteById(anyString())).thenThrow(mock(RuntimeException.class));

        given().port(port)
                .delete("/v1/crm/user/12345678")
                .then()
                .assertThat()
                .statusCode(is(500));
    }

}