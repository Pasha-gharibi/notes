package disqo.pasha.constant;

import java.util.Date;

public class Dashboard {
    public static final String DEFAULT_OUTPUT_JSON_FILE_PATH ="d://notes"+ new Date().getTime() + ".json" ;
    public static final String DEFAULT_OUTPUT_PARQUET_FILE_PATH ="d://notes"+ new Date().getTime() + ".parquet" ;
    public static final Integer NOTE_TO_JSON_STEP_CHUNK_SIZE = 100;
    public static final Integer NOTE_TO_PARQUET_STEP_CHUNK_SIZE = 100;
}
