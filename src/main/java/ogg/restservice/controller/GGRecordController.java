package ogg.restservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ogg.restservice.model.GGRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class GGRecordController {

    private final Logger logger = Logger.getLogger(GGRecordController.class.getName());
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/ggrecord")
    public GGRecord newGGRecord(@RequestBody GGRecord ggRecord) {
        try {
            String jsonString = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(ggRecord);
            //logger.info(jsonString);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return ggRecord;
    }

}
