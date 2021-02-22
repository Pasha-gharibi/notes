package disqo.pasha.etl.reader;

import disqo.pasha.domain.Note;
import disqo.pasha.repository.NoteRepository;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@JobScope
public class NoteItemReader extends RepositoryItemReader<Note> {

    public NoteItemReader(@Autowired NoteRepository noteRepository) {
        this.setRepository(noteRepository);
        this.setMethodName("findAll");
        Map sorts = new HashMap();
        sorts.put("id", Sort.Direction.ASC);
        this.setSort(sorts);
    }

}
