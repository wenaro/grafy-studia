package edu.graphs.configuration;

import edu.graphs.model.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorConfiguration {

    @Bean
    public List<String> colorList() {
        final List<Color> colorList = Arrays.asList(Color.values());
        final List<String> colorStringList = new ArrayList<>(colorList.size());
        colorList.forEach(x -> colorStringList.add(x.getColor()));
        return colorStringList;
    }

}
