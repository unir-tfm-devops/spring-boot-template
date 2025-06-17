package com.unir.template.config;

import com.unir.template.repository.BooksRepository;
import com.unir.template.service.BooksService;
import com.unir.template.service.BooksServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

  @Bean
  public BooksService booksService(BooksRepository booksRepository) {
    return new BooksServiceImpl(booksRepository);
  }
}
