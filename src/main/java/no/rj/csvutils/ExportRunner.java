package no.rj.csvutils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExportRunner {

    private static Logger logger = LoggerFactory.getLogger(ExportRunner.class);
    private final Path sourceDirectory;
    private final Path targetDirectory;

    public ExportRunner(Path sourceDirectory, Path targetDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
    }


    public static void main(String... args) throws IOException {
        if (args.length != 2) {
            logger.error("Wrong number of arguments!");
            System.exit(-1);
        }
        Path sourceDirectory = Paths.get(args[0]);
        if (!(sourceDirectory.toFile().exists() && sourceDirectory.toFile().isDirectory())) {
            logger.error("Illegal source file!");
            System.exit(-1);
        }
        Path targetDirectory = Paths.get(args[1]);
        if (!(targetDirectory.toFile().exists() && targetDirectory.toFile().isDirectory())) {
            logger.error("Illegal target file!");
            System.exit(-1);
        }
        new ExportRunner(sourceDirectory, targetDirectory).convert();
    }

    public void convert() throws IOException {
        File[] sourceFiles = sourceDirectory.toFile().listFiles();
        for (File sourceFile : sourceFiles) {
            logger.debug("Reading source file: "+sourceFile.getName());
            CSVReader csvReader = new CSVReader(new FileReader(sourceFile));
            List<String[]> lines = csvReader.readAll();
            csvReader.close();

            File target = new File(targetDirectory.toFile().getAbsolutePath() + "/" + sourceFile.getName());
            CSVWriter writer = new CSVWriter(new FileWriter(target));
            logger.debug("Writing to target file: " + target.getAbsolutePath());
            writer.writeAll(lines);
            writer.flush();
            writer.close();
        }
    }

}
