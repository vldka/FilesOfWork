package qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ParsingZipFileTest {

    private ClassLoader cl = ParsingZipFileTest.class.getClassLoader();

    @Test
    @DisplayName("Чтение файла в формате PDF")
    void ReadFilePDFTest() throws Exception {
        var dowloaded = cl.getResourceAsStream("FileZip/IntelliJIDEA_ReferenceCard.pdf");
        PDF pdf = new PDF(dowloaded);
        Assertions.assertThat(pdf.text).contains("Windows & Linux keymap");
        System.out.println(pdf.text);
        //String dataAsString = FileUtils.readFileToString(dowloaded,StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Чтение файла в формате xlsx")
    void ReadFileXLSTest() throws Exception {
        var dowloaded = cl.getResourceAsStream("FileZip/production.xlsx");
        XLS xls = new XLS(dowloaded);
        String product1 = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
        String product2 = xls.excel.getSheetAt(0).getRow(15).getCell(1).getStringCellValue();
        String product3 = xls.excel.getSheetAt(0).getRow(1).getCell(10).getStringCellValue();
        assertThat(product1).contains("Железо");
        assertThat(product2).contains("Бетон");
        assertThat(product3).contains("Медь");
        //String dataAsString = FileUtils.readFileToString(dowloaded,StandardCharsets.UTF_8);
        //Действия
    }

    @Test
    @DisplayName("Чтение файла в формате csv")
    void ReadFileCSVTest() throws Exception {
        var dowloaded = cl.getResourceAsStream("FileZip/successfulCheckStateCityCsvFile.csv");
        try (dowloaded;
             CSVReader csvReader = new CSVReader(new InputStreamReader(dowloaded))) {
            List<String[]> data = csvReader.readAll();
            org.junit.jupiter.api.Assertions.assertEquals(5, data.size());
            org.junit.jupiter.api.Assertions.assertArrayEquals(
                    new String[]{"NCR", "Delhi"}, data.get(0)
            );
            org.junit.jupiter.api.Assertions.assertArrayEquals(
                    new String[]{"Haryana", "Karnal"}, data.get(3)
            );
        }

        //String dataAsString = FileUtils.readFileToString(dowloaded,StandardCharsets.UTF_8);
        //Действия
    }

    @Test
    @DisplayName("Чтение файла из zip")
    void ReadFileZipTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("FileZip/FileZip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if(entry.getName().equals("IntelliJIDEA_ReferenceCard.pdf"))
                {
                    PDF pdf = new PDF(zis);
                    Assertions.assertThat(pdf.text).contains("Windows & Linux keymap");
                }
                if(entry.getName().equals("production.xlsx"))
                {
                    XLS xls = new XLS(zis);
                    String product1 = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    String product2 = xls.excel.getSheetAt(0).getRow(15).getCell(1).getStringCellValue();
                    String product3 = xls.excel.getSheetAt(0).getRow(1).getCell(10).getStringCellValue();
                    assertThat(product1).contains("Железо");
                    assertThat(product2).contains("Бетон");
                    assertThat(product3).contains("Медь");
                }
                if(entry.getName().equals("successfulCheckStateCityCsvFile.csv"))
                {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvReader.readAll();
                    org.junit.jupiter.api.Assertions.assertEquals(5, data.size());
                    org.junit.jupiter.api.Assertions.assertArrayEquals(
                            new String[]{"NCR", "Delhi"}, data.get(0)
                    );
                    org.junit.jupiter.api.Assertions.assertArrayEquals(
                            new String[]{"Haryana", "Karnal"}, data.get(3)
                    );
                }
            }
        }

        //String dataAsString = FileUtils.readFileToString(dowloaded,StandardCharsets.UTF_8);
        //Действия
    }
}
