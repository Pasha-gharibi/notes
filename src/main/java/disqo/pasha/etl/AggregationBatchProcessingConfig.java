package disqo.pasha.etl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregationBatchProcessingConfig {

//    @Autowired
//    JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    FNPSWBillReader fNPSWBillReader;
//
//    @Autowired
//    FNPSWBillWriter fNPSWBillWriter;
//
//    @Autowired
//    FNPSWBillProcessor fNPSWBillProcessor;
//
//    @Bean(name = "fnpswBillJob")
//    public Job fnpswBillJob() {
//        return jobBuilderFactory.get("fnpswBillJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(updateLoadJobListener)
//                .listener(updateSubutilityIdJobListener)
//                .flow(fnpswBillStep())
//                .end()
//                .build();
//    }
//
//    @Bean(name = "fnpswBillStep")
//    public Step fnpswBillStep() {
//        return stepBuilderFactory.get("fnpswBillStep")
//                .<BillDTO, BillDTO>chunk(Dashboard.FNPSW_BILL_STEP_CHUNK_SIZE)
//                .reader(fNPSWBillReader)
//                .processor(fNPSWBillProcessor)
//                .writer(fNPSWBillWriter)
//                .build();
//    }


}
