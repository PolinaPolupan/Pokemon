package example.pokemon.batch;

import example.pokemon.dto.StudentDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.support.JdbcTransactionManager;

import javax.sql.DataSource;

@Configuration
public class PokemonJobConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("PokemonJob", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public JdbcTransactionManager transactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean
    public Step step1(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        return new StepBuilder("filePreparation", jobRepository)
                .tasklet(new FilePreparationTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Step step2(
            JobRepository jobRepository, JdbcTransactionManager transactionManager,
            ItemReader<StudentDto> billingDataFileReader, ItemWriter<StudentDto> billingDataTableWriter) {
        return new StepBuilder("fileIngestion", jobRepository)
                .<StudentDto, StudentDto>chunk(100, transactionManager)
                .reader(billingDataFileReader)
                .writer(billingDataTableWriter)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<StudentDto> billingDataFileReader(@Value("#{jobParameters['input.file']}") String inputFile) {
        return new FlatFileItemReaderBuilder<StudentDto>()
                .name("billingDataFileReader")
                .resource(new FileSystemResource(inputFile))
                .delimited()
                .names("firstName", "lastName", "studentGroup")
                .targetType(StudentDto.class)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<StudentDto> billingDataTableWriter(DataSource dataSource) {
        String sql = "insert into pokemon.students values (gen_random_uuid(), :firstName, :lastName, :studentGroup)";
        return new JdbcBatchItemWriterBuilder<StudentDto>()
                .dataSource(dataSource)
                .sql(sql)
                .beanMapped()
                .build();
    }
}