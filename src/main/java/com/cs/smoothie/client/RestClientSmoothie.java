package com.cs.smoothie.client;

import com.cs.smoothie.model.Smoothie;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestClientSmoothie {
    public static final String URL = "http://localhost:8080/api/smoothie/";
    //    private RestTemplate client = new RestTemplate();
//    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public RestClientSmoothie() {
    }


    public List<Smoothie> getAll(String url) {
//        restTemplate.getForObject(URL + id, Smoothie.class);
        ResponseEntity<List> response = restTemplate.getForEntity(URL + url, List.class);
        List<Smoothie> smoothies = response.getBody();
        return smoothies;
    }

    public Smoothie get(String url, long id) {
        Smoothie response = restTemplate.getForObject(URL + url + "/" + id, Smoothie.class);
        return response;
    }

    public void post(String url, Smoothie smoothie) {
        System.out.println("doPost");
        HttpEntity<Smoothie> request = new HttpEntity<>(smoothie);
        ResponseEntity<Smoothie> response = restTemplate.postForEntity(
                URL + url, request, Smoothie.class);
    }

    public void submitForm(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
        map.add("id", id);
        HttpEntity<MultiValueMap<String, Long>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Smoothie> response = restTemplate.postForEntity(
                URL + "form", request, Smoothie.class);
    }

    public void put(String action, Smoothie smoothie) {
        HttpHeaders headers = new HttpHeaders();
        String resourceUrl = URL + action + "/" + smoothie.getId();
        HttpEntity<Smoothie> requestUpdate = new HttpEntity<>(smoothie, headers);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
    }

    public void delete(String url, Long id) {
        String entityUrl = URL + url + "/" + id;
        restTemplate.delete(entityUrl);
    }

//    @Bean
//    protected RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
}
