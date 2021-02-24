package disqo.pasha.etl.writer;

import disqo.pasha.constant.Dashboard;
import disqo.pasha.domain.Note;
import disqo.pasha.parquet.CustomParquetWriter;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class NoteParquetWriter implements ItemWriter<Note> {

    private MessageType getSchemaForParquetFile() throws IOException {
        File resource = new ClassPathResource("note.schema").getFile();
        String rawSchema = new String(Files.readAllBytes(resource.toPath()));
        return MessageTypeParser.parseMessageType(rawSchema);
    }

    @Override
    public void write(List<? extends Note> list) throws Exception {

        List<List<String>> columns = prepareData(list);
        MessageType schema = getSchemaForParquetFile();
        CustomParquetWriter writer = getParquetWriter(schema);
        for (List<String> column : columns) {
            writer.write(column);
        }
        writer.close();
    }

    private CustomParquetWriter getParquetWriter(MessageType schema) throws IOException {
        File outputParquetFile = new File(Dashboard.DEFAULT_OUTPUT_PARQUET_FILE_PATH);
        Path path = new Path(outputParquetFile.toURI().toString());
        return new CustomParquetWriter(
                path, schema, false, CompressionCodecName.SNAPPY
        );
    }

    private List<List<String>> prepareData(List<? extends Note> list) {
        List<List<String>> output = new ArrayList<>();
        List<String> record = null;
        for (Note note : list) {
            record = new ArrayList<>();
            record.add(note.getTitle());
            record.add(note.getNote());
            record.add(note.getCreateTime().toString());
            record.add(note.getLastUpdateTime().toString());
            output.add(record);
        }
        return output;
    }


}
