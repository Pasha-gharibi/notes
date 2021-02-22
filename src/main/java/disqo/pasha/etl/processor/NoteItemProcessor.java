package disqo.pasha.etl.processor;

import disqo.pasha.domain.Note;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class NoteItemProcessor implements ItemProcessor<Note, Note> {

    @Override
    public Note process(Note note) throws Exception {
        System.out.println(note.toString());
        return note;
    }
}
