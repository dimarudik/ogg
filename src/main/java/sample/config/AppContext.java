package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppContext {

    @Bean
    public List<String> publishedValues() {
        String s1 = "Record One";
        String s2 = "Record Two";
        List<String> values = new ArrayList<>();
        values.add(s1);
        values.add(s2);
        return values;
    }

    public String printMe() {
        return "It's me";
    }

}
