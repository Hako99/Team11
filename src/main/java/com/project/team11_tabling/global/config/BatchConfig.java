package com.project.team11_tabling.global.config;

import com.project.team11_tabling.domain.shop.Shop;
import com.project.team11_tabling.global.batch.ShopBatchDto;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {

  private final EntityManagerFactory entityManagerFactory;

  private int chunkSize = 100;

  @Bean
  public Job job(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
    return new JobBuilder("myJob", jobRepository)
        .start(step(jobRepository, transactionManager))
        .build();
  }

  @Bean
  public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("myStep", jobRepository)
        .<ShopBatchDto, Shop>chunk(chunkSize, transactionManager)
        .reader(itemReader())
        .processor(itemProcessor())
        .writer(itemWriter())
        .build();
  }

  @Bean
  public FlatFileItemReader<ShopBatchDto> itemReader() {

    FlatFileItemReader<ShopBatchDto> flatFileItemReader = new FlatFileItemReader<>();
    flatFileItemReader.setResource(new ClassPathResource("/static/shop_input.csv"));
    flatFileItemReader.setLinesToSkip(1);

    DefaultLineMapper<ShopBatchDto> defaultLineMapper = new DefaultLineMapper<>();

    DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
    delimitedLineTokenizer.setNames("shopId", "name", "address", "city", "phone", "reviewCount", "openTime", "closeTime");
    delimitedLineTokenizer.setDelimiter("/");

    BeanWrapperFieldSetMapper<ShopBatchDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
    beanWrapperFieldSetMapper.setTargetType(ShopBatchDto.class);

    defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
    defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
    flatFileItemReader.setLineMapper(defaultLineMapper);

    return flatFileItemReader;
  }

  @Bean
  public ItemProcessor<ShopBatchDto, Shop> itemProcessor() {
    return shopBatchDto -> new Shop(shopBatchDto.getShopId(),
        shopBatchDto.getName(),shopBatchDto.getAddress(),shopBatchDto.getCity(),shopBatchDto.getPhone(),shopBatchDto.getReviewCount(),
        LocalTime.parse(shopBatchDto.getOpenTime()),LocalTime.parse(shopBatchDto.getCloseTime()));
  }

  @Bean
  public JpaItemWriter<Shop> itemWriter() {
    JpaItemWriter<Shop> jpaItemWriter = new JpaItemWriter<>();
    jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
    return jpaItemWriter;
  }
}
