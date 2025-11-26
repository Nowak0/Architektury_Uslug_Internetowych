package nba.project.client;

import lombok.RequiredArgsConstructor;
import nba.project.entity.Franchise;
import nba.project.service.FranchiseService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FranchiseClient {
    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8083/api/franchises";

    public List<Franchise> syncFranchises() {
        Franchise[] response =  restTemplate.getForObject(url, Franchise[].class);
        if(response == null) {return null;}
        return List.of(response);
    }
}
