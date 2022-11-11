package com.kodilla.rental_frontend.service;

import com.kodilla.rental_frontend.config.BackEndConfig;
import com.kodilla.rental_frontend.domain.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static ModelService modelService;


    public static ModelService getInstance() {
        if (modelService == null) {
            modelService = new ModelService();
        }
        return modelService;
    }

    private URI getUrl() {
        return UriComponentsBuilder.fromHttpUrl(BackEndConfig.getModels())
                .build()
                .encode()
                .toUri();
    }

    public List<Model> getModels() {
        URI url = getUrl();
        try {
            Model[] response = restTemplate.getForObject(url, Model[].class);
            return Arrays.asList(ofNullable(response).orElse(new Model[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Model getModel(long modelId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getModels() + "/" + modelId)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(url, Model.class);
        } catch (RestClientException e) {
            return new Model();
        }
    }

    public List<Model> findByName(String name) {
        return getModels().stream().filter(m -> m.getName().contains(name)).collect(Collectors.toList());
    }

    public Model createModel(Model model) throws IOException {
        try {
            return restTemplate.postForObject(getUrl(), model, Model.class);
        } catch (RestClientException e) {
            return new Model();
        }
    }

    public void updateModel(Model model) throws IOException {
        try {
            restTemplate.put(getUrl(), model);
        } catch (RestClientException e) {
        }
    }

    public void deleteModel(long modelId) {
        URI url = UriComponentsBuilder.fromHttpUrl(BackEndConfig.getModels() + "/" + modelId)
                .build()
                .encode()
                .toUri();
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
        }
    }
}
