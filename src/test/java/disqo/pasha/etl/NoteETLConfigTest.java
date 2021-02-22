package disqo.pasha.etl;

import disqo.pasha.NotesApplication;
import disqo.pasha.configuration.BatchConfig;
import disqo.pasha.configuration.InMemoryTestDbConfig;
import disqo.pasha.constant.Dashboard;
import disqo.pasha.domain.Note;
import disqo.pasha.domain.User;
import disqo.pasha.etl.processor.NoteItemProcessor;
import disqo.pasha.etl.reader.NoteItemReader;
import disqo.pasha.etl.writer.NoteJsonWriter;
import disqo.pasha.repository.NoteRepository;
import disqo.pasha.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {NotesApplication.class, InMemoryTestDbConfig.class, BatchConfig.class, NoteETLConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class NoteETLConfigTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    NoteRepository noteRepository;
    @MockBean
    private NoteItemReader noteItemReader;
    @MockBean
    private NoteJsonWriter noteJsonWriter;
    @MockBean
    private NoteItemProcessor noteItemProcessor;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    public NoteETLConfigTest() {
    }

    @Before
    public void prepareData() {
        List<User> users = new ArrayList<User>();
        User user = null;
        for (int i = 0; i < 10; i++) {
            user = new User("pasha" + i + "@test.com", "pasha123456").createTime(LocalDate.now()).lastUpdateTime(LocalDate.now());
            users.add(user);
        }
        List<User> savedUsers = userRepository.saveAll(users);

        List<Note> notes = new ArrayList<Note>();
        Note note =null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100; j++) {
                note = new Note("title " + i + j).note("My note " + i + j).createTime(LocalDate.now()).lastUpdateTime(LocalDate.now()).user(savedUsers.get(i));
                notes.add(note);
            }
        }
        noteRepository.saveAll(notes);

    }

//    @After
//    public void cleanUp() {
//        jobRepositoryTestUtils.removeJobExecutions();
//    }

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("filePath", Dashboard.DEFAULT_OUTPUT_JSON_FILE_PATH);
        return paramsBuilder.toJobParameters();
    }


    @Test
    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        FileSystemResource expectedResult = new FileSystemResource("EXPECTED_OUTPUT");
        FileSystemResource actualResult = new FileSystemResource("TEST_OUTPUT");

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        assertEquals(actualJobInstance.getJobName(), "dbToFileConverterJob");
        assertEquals(actualJobExitStatus.getExitCode(), "COMPLETED");
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }


}
