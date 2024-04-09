package com.project.team11_tabling.global.batch;

import com.project.team11_tabling.domain.booking.entity.Booking;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BookingBatch {

  private final EntityManagerFactory entityManagerFactory;

  private final int chunkSize = 10;

  @Bean
  public Job job(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
    return new JobBuilder("bookingJob", jobRepository)
        .start(step(jobRepository, transactionManager))
        .build();
  }

  @Bean
  public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("bookingStep", jobRepository)
        .<Booking, BookingCsvDto>chunk(chunkSize, transactionManager)
        .reader(bookingItemReader())
        .processor(bookingItemProcessor())
        .writer(bookingItemWriter())
        .build();
  }

  @Bean
  public JpaPagingItemReader<Booking> bookingItemReader() {
    return new JpaPagingItemReaderBuilder<Booking>()
        .name("bookingItemReader")
        .entityManagerFactory(entityManagerFactory)
        .pageSize(chunkSize)
        .queryString("SELECT b FROM Booking b")
        .build();
  }

  @Bean
  public ItemProcessor<Booking, BookingCsvDto> bookingItemProcessor() {
    return Booking -> new BookingCsvDto(Booking.getUserId(),Booking.getShopId(),Booking.getTicketNumber(),Booking.getState(),Booking.getReservedDatetime(),Booking.getReservedParty());
  }

  @Bean
  public FlatFileItemWriter<BookingCsvDto> bookingItemWriter() {
    BeanWrapperFieldExtractor<BookingCsvDto> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
    beanWrapperFieldExtractor.setNames(new String[]{"userId","shopId","ticketNumber","state", "reservedDatetime","reservedParty"});

    DelimitedLineAggregator<BookingCsvDto> delimitedLineAggregator = new DelimitedLineAggregator<>();
    delimitedLineAggregator.setDelimiter(",");
    delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

    return new FlatFileItemWriterBuilder<BookingCsvDto>()
        .name("bookingItemWriter")
        .append(true)
        .resource(new FileSystemResource("src/main/resources/static/booking_output.csv"))
        .lineAggregator(delimitedLineAggregator)
        .headerCallback(writer -> writer.write("userId, shopId, ticketNumber, state, reservedDatetime, reservedParty"))
        .footerCallback(writer -> writer.write(LocalDate.now() + "----------\n"))
        .build();
  }
}
