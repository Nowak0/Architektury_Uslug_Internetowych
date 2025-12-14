package nba.project.client;

import lombok.RequiredArgsConstructor;
import nba.project.entity.Franchise;
import nba.project.service.FranchiseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FranchiseClient {
    private final RestTemplate restTemplate;
    
    @Value("${franchise.app.url}")
    private String franchiseAppUrl;

    public List<Franchise> syncFranchises() {
        String url = franchiseAppUrl + "/api/franchises";
        Franchise[] response =  restTemplate.getForObject(url, Franchise[].class);
        if(response == null) {return null;}
        return List.of(response);
    }
}
