package training.tasks.transactions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.User;
import training.tasks.transactions.model.dto.UserDto;
import training.tasks.transactions.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static training.tasks.transactions.constant.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    @Autowired
    private UserService userService;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;
    @LocalServerPort
    private int port;

    @Test
    public void getTest() throws JsonProcessingException {
        User john = userService.save(new User(JOHN, JOHN_EMAIL));

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + TEMPLATE_URI_FOR_GET_USER, port, john.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<>() {});

        String example = mapper.writeValueAsString(UserDto.Builder.takeFrom(john)
                .id()
                .name()
                .email()
                .andTurnToDto());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(example, response.getBody());

        dropUsers(john.getId());
    }

    @Test
    public void get_NoSuchEntityTest() {
        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + TEMPLATE_URI_FOR_GET_USER, port, 100L);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<>() {});

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @Transactional
    public void getDetailsTest() throws JsonProcessingException {
        User bob = userService.save(new User(BOB, BOB_EMAIL, BOB_BIO, BOB_TELEGRAM, BOB_OTHER_CONTACTS, new ArrayList<>()));

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + TEMPLATE_URI_FOR_GET_USER_DETAILS, port, bob.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<>() {});

        String example = mapper.writeValueAsString(UserDto.Builder.takeFrom(bob)
                .all()
                .andTurnToDto());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(example, response.getBody());
    }

    //TODO: FIX THIS TEST
    @Test
    @Transactional
    public void getAllTest() throws JsonProcessingException {
        User john = userService.save(new User(JOHN, JOHN_EMAIL));
        User bob = userService.save(new User(BOB, BOB_EMAIL, BOB_BIO, BOB_TELEGRAM, BOB_OTHER_CONTACTS, new ArrayList<>()));

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + USERS_URI, port);
        String response = restTemplate.getForObject(url, String.class);
        String example = mapper.writeValueAsString(Stream.of(john, bob)
                .map(user -> UserDto.Builder.takeFrom(user)
                        .id()
                        .name()
                        .email()
                        .andTurnToDto()));
        assertEquals(example, response);
    }

    @Test
    void saveNewUser_SuccessfulTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(new UserDto(ALICE, ALICE_EMAIL), headers);

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + USERS_URI, port);
        ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.POST,
                request, new ParameterizedTypeReference<>() {});

        assertEquals( 200, response.getStatusCode().value());

        Long id = response.getBody().getId();
        userService.getOne(id);

        dropUsers(id);
    }

    @Test
    @Transactional
    void updateUser_SuccessfulTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        User alice = userService.save(new User(ALICE, ALICE_EMAIL));

        HttpEntity<UserDto> request = new HttpEntity<>(new UserDto(alice.getId(),
                ALICE,
                ALICE_EMAIL,
                ALICE_BIO,
                ALICE_TELEGRAM,
                ALICE_OTHER_CONTACTS), headers);

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + USERS_URI, port);
        ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.POST, request,
                new ParameterizedTypeReference<>() {});

        assertEquals(200, response.getStatusCode().value());

        Long id = response.getBody().getId();

        assertNotNull(userService.getOne(id).getTelegram());
    }

    @Test
    void saveNewUser_NameLengthConstraintViolationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(new UserDto(ALICE_LONG_NAME, ALICE_EMAIL), headers);

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + USERS_URI, port);
        ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void saveNewUser_NameEmptyConstraintViolationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(new UserDto("", ALICE_EMAIL), headers);

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + USERS_URI, port);
        ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void saveNewUser_SanitizationViolationTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<UserDto> request = new HttpEntity<>(new UserDto(ALICE_RUDE_NAME, ALICE_EMAIL), headers);

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + USERS_URI, port);
        ResponseEntity<UserDto> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});

        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    void delete_SuccessfulTest() {
        Long aliceId = userService.save(new User(ALICE, ALICE_EMAIL)).getId();

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + TEMPLATE_URI_FOR_GET_USER, port, aliceId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity request = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

        assertEquals(200, response.getStatusCode().value());

        assertFalse(userService.exists(aliceId));
    }

    @Test
    void delete_NoSuchEntityTest() {
        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + TEMPLATE_URI_FOR_GET_USER, port, 100);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity request = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

        assertEquals(404, response.getStatusCode().value());
    }

    //TODO: FIX THIS TEST
    @Test
    @Transactional
    void follow_SuccessfulTest() {
        User john = userService.save(new User(JOHN, JOHN_EMAIL));
        User bob = userService.save(new User(BOB, BOB_EMAIL, BOB_BIO, BOB_TELEGRAM, BOB_OTHER_CONTACTS, new ArrayList<>()));

        String url = String.format(TEMPLATE_URL_FOR_LOCAL_TEST + TEMPLATE_URI_FOR_FOLLOW, port, john.getId(), bob.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity request = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        assertEquals(200, response.getStatusCode().value());

        assertTrue(userService.getOne(john.getId()).getSubscriptions().stream()
                .anyMatch(u -> u.getId().equals(bob.getId())));

        dropUsers(john.getId(), bob.getId());
    }

    private void dropUsers(Long... ids) {
        Arrays.stream(ids).forEach(id -> userService.delete(id));
    }
}