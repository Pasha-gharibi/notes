package disqo.pasha.etl.writer;

import disqo.pasha.domain.Note;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class NoteJsonWriter extends JsonFileItemWriter<Note> {

    public NoteJsonWriter(@Qualifier("noteJsonWriterResource") Resource resource,
                          @Qualifier("noteJsonMarshaller") JsonObjectMarshaller<Note> jsonObjectMarshaller
//                         , @Value("#{jobParameters}") Map jobParameters
    ) {
//        super(new FileSystemResource(jobParameters.get("filePath").toString()), jsonObjectMarshaller);
        super(resource, jsonObjectMarshaller);
        this.setJsonObjectMarshaller(jsonObjectMarshaller);
    }

}
