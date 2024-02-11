package br.com.giannatech.giannaschool.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  @Bean
  ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();
    modelMapper
        .getConfiguration()
        .setSkipNullEnabled(true)
        .setMatchingStrategy(MatchingStrategies.LOOSE);

    return modelMapper;
  }
}
