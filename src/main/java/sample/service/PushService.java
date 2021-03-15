package sample.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sample.model.GGRecord;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class PushService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Logger logger = Logger.getLogger(PushService.class.getName());

    @Async("taskExecutor")
    public CompletableFuture<GGRecord> sendToRest(GGRecord ggRecord) {
        final GGRecord record = restTemplate.postForObject("http://localhost:8080/api/ggrecord", ggRecord, GGRecord.class);
        return CompletableFuture.completedFuture(record);
    }

}
