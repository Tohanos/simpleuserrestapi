package com.example.simplemessengerapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void messageRestTest() throws URISyntaxException {
        String url = "http://localhost:" + port + "/user";
        URI uri = new URI(url);
        String messageToSend = "{\n" +
                "  \"name\" : \"Petya\",\n" +
                "  \"password\" : \"22222\"\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(messageToSend, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, httpEntity, String.class);

        String[] responseStrings = result.getBody().split(":");
        String token = responseStrings[1].replace("\"", "").replace("}", "");

        url = "http://localhost:" + port + "/message";
        uri = new URI(url);
        messageToSend = "{\n" +
                "  \"name\" : \"Petya\",\n" +
                "  \"message\" : \"Alright then\"\n" +
                "}";
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Bearer", token);

        httpEntity = new HttpEntity<>(messageToSend, headers);
        result = this.restTemplate.postForEntity(uri, httpEntity, String.class);
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
