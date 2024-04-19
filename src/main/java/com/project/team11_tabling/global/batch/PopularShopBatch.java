package com.project.team11_tabling.global.batch;

import com.project.team11_tabling.domain.shop.entity.Shop;
import com.project.team11_tabling.global.batch.dto.PopularShopDto;
import com.project.team11_tabling.global.batch.dto.ShopBookingCountDto;
import com.project.team11_tabling.global.batch.tasklet.InitializeShopTasklet;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class PopularShopBatch {

  private final EntityManager entityManager;
  private final InitializeShopTasklet initializeShopTasklet;
  private final int chunkSize = 100;

  @Bean
  public Job popularShopJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, DataSource dataSource) {
    return new JobBuilder("popularShopJob", jobRepository)
        .start(initializeShopStep(jobRepository,transactionManager))
        .next(popularShopStep(jobRepository, transactionManager,dataSource))
        .build();
  }

  @Bean
  public Step initializeShopStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("initializeShopStep",jobRepository)
        .tasklet(initializeShopTasklet,transactionManager)
        .build();
  }

  @Bean
  public Step popularShopStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, DataSource dataSource) {
    return new StepBuilder("popularShopStep", jobRepository)
        .<ShopBookingCountDto, Shop>chunk(chunkSize, transactionManager)
        .reader(popularShopItemReader(dataSource))
        .processor(popularShopCompositeProcessor())
        .writer(popularShopItemWriter())
        .build();
  }

  @Bean
  public JdbcCursorItemReader<ShopBookingCountDto> popularShopItemReader(DataSource dataSource) {
    return new JdbcCursorItemReaderBuilder<ShopBookingCountDto>()
        .name("popularShopItemReader")
        .dataSource(dataSource)
        .sql("select shop_id, count(shop_id) as shop_booking_count "
            + "from booking "
            + "group by shop_id")
        .rowMapper(new BeanPropertyRowMapper<>(ShopBookingCountDto.class))
        .build();
  }

  @Bean
  public CompositeItemProcessor<ShopBookingCountDto, Shop> popularShopCompositeProcessor() {
    List<ItemProcessor<?,?>> delegates = new ArrayList<>(2);
    delegates.add(popularShopItemProcessor1());
    delegates.add(popularShopItemProcessor2());

    CompositeItemProcessor<ShopBookingCountDto,Shop> processor = new CompositeItemProcessor<>();

    processor.setDelegates(delegates);

    return processor;
  }

  @Bean
  public ItemProcessor<ShopBookingCountDto, PopularShopDto> popularShopItemProcessor1() {
    return item -> {
      boolean popularShop = false;
      if (item.getShopBookingCount() >= 50) {
        popularShop = true;
      }

      return new PopularShopDto(item.getShopId(), popularShop);
    };
  }

  @Bean
  public ItemProcessor<PopularShopDto, Shop> popularShopItemProcessor2() {
    return item -> {
      Shop shop = findByShopId(item.getShopId());

      if (shop != null) {
        shop.popularShopUpdate(item.isPopularShop());
      }

      return shop;
    };
  }

  @Bean
  public JpaItemWriter<Shop> popularShopItemWriter() {
    return new JpaItemWriterBuilder<Shop>()
        .entityManagerFactory(entityManager.getEntityManagerFactory())
        .build();
  }

  public Shop findByShopId(Object value) {
    String jpql = "SELECT s FROM Shop s WHERE s.shopId" + "= :value";
    return entityManager.createQuery(jpql, Shop.class)
        .setParameter("value", value)
        .getSingleResult();
  }
}