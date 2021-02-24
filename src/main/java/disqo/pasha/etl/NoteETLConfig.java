package disqo.pasha.etl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import disqo.pasha.constant.Dashboard;
import disqo.pasha.domain.Note;
import disqo.pasha.etl.processor.NoteItemProcessor;
import disqo.pasha.etl.reader.NoteItemReader;
import disqo.pasha.etl.writer.NoteJsonWriter;
import disqo.pasha.etl.writer.NoteParquetWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.web.jackson2.WebServletJackson2Module;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class NoteETLConfig {

    JobBuilderFactory jobBuilderFactory;
    StepBuilderFactory stepBuilderFactory;
    NoteItemReader noteItemReader;
    NoteJsonWriter noteJsonWriter;
    NoteParquetWriter noteParquetWriter;
    NoteItemProcessor noteItemProcessor;

    public NoteETLConfig(@Autowired JobBuilderFactory jobBuilderFactory,
                         @Autowired StepBuilderFactory stepBuilderFactory,
                         @Autowired NoteItemReader noteItemReader,
                         @Autowired NoteJsonWriter noteJsonWriter,
                         @Autowired NoteParquetWriter noteParquetWriter,
                         @Autowired NoteItemProcessor noteItemProcessor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.noteItemReader = noteItemReader;
        this.noteJsonWriter = noteJsonWriter;
        this.noteItemProcessor = noteItemProcessor;
        this.noteParquetWriter = noteParquetWriter;
    }

    @Bean(name = "dbToFileConverterJob")
    public Job dbToFileConverterJob() {
        return jobBuilderFactory.get("dbToFileConverterJob")
                .incrementer(new RunIdIncrementer())
                .flow(noteJsonConvertStep())
                .next(noteParquetConvertStep())
                .end()
                .build();
    }

    @Bean(name = "noteJsonConvertStep")
    public Step noteJsonConvertStep() {
        return stepBuilderFactory.get("noteJsonConvertStep")
                .<Note, Note>chunk(Dashboard.NOTE_TO_JSON_STEP_CHUNK_SIZE)
                .reader(noteItemReader)
                .processor(noteItemProcessor)
                .writer(noteJsonWriter)
                .build();
    }

    @Bean(name = "noteParquetConvertStep")
    public Step noteParquetConvertStep() {
        return stepBuilderFactory.get("noteParquetConvertStep")
                .<Note, Note>chunk(Dashboard.NOTE_TO_PARQUET_STEP_CHUNK_SIZE)
                .reader(noteItemReader)
                .processor(noteItemProcessor)
                .writer(noteParquetWriter)
                .build();
    }


    @Bean("noteJsonWriterResource")
    Resource noteJsonWriterResource() {
        return new FileSystemResource(Dashboard.DEFAULT_OUTPUT_JSON_FILE_PATH);
    }

    @Bean("noteJsonMarshaller")
    JsonObjectMarshaller noteJsonMarshaller() {
        JacksonJsonObjectMarshaller jacksonJsonObjectMarshaller = new JacksonJsonObjectMarshaller<Note>();
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setDateFormat(new SimpleDateFormat("yyyyMMdd hh:mm", Locale.getDefault()));
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        jacksonJsonObjectMarshaller.setObjectMapper(mapper);
        return jacksonJsonObjectMarshaller;
    }

}
