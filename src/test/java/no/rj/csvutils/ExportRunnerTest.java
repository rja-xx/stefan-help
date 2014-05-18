package no.rj.csvutils;

import org.testng.annotations.Test;

import java.nio.file.Paths;

public class ExportRunnerTest {


    @Test
    public void whenImportingFile_givenOneFileInSource_thenOneFileShouldBeInResult() throws Exception {
        ExportRunner exportRunner = new ExportRunner(Paths.get("resources/sourceFolder"), Paths.get("resources/targetFolder"));
        exportRunner.convert();
    }
}