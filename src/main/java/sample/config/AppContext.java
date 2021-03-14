package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sample.model.GGColumn;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppContext {

/*
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
*/

/*
    https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/
*/

    @Bean
    public List<String> publishedValues() {
        String s1 = "Record One";
        String s2 = "Record Two";
        List<String> values = new ArrayList<>();
        values.add(s1);
        values.add(s2);
        return values;
    }

/*
    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Quote quote = restTemplate.getForObject(
                    "https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
            Logger.getLogger(AppContext.class.getName()).info(quote.toString());
        };
    }
*/

    public String printMe() {
        return "It's me";
    }

}
