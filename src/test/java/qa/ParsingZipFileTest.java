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

    private final ClassLoader cl = ParsingZipFileTest.class.getClassLoader();
    private int count = 0;

    @Test
    @DisplayName("Чтение файла в формате PDF")
    void readFilePDFTest() throws Exception {
        var dowloaded = cl.getResourceAsStream("IntelliJIDEA_ReferenceCard.pdf");
        PDF pdf = new PDF(dowloaded);
        Assertions.assertThat(pdf.text).contains("Windows & Linux keymap");
        System.out.println(pdf.text);
    }

    @Test
    @DisplayName("Чтение файла в формате xlsx")
    void readFileXLSTest() throws Exception {
        var dowloaded = cl.getResourceAsStream("production.xlsx");
        XLS xls = new XLS(dowloaded);
        String product1 = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
        String product2 = xls.excel.getSheetAt(0).getRow(15).getCell(1).getStringCellValue();
        String product3 = xls.excel.getSheetAt(0).getRow(1).getCell(10).getStringCellValue();
        assertThat(product1).contains("Железо");
        assertThat(product2).contains("Бетон");
        assertThat(product3).contains("Медь");
    }

    @Test
    @DisplayName("Чтение файла в формате csv")
    void readFileCSVTest() throws Exception {
        var dowloaded = cl.getResourceAsStream("successfulCheckStateCityCsvFile.csv");
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
    }

    @Test
    @DisplayName("Чтение файла из zip в pdf")
    void readFileZipIsPdfTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("FileZip/FileZip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().equals("IntelliJIDEA_ReferenceCard.pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertThat(pdf.text).contains("Windows & Linux keymap");
                    count++;
                    break;
                }
            }
            if (count == 0) {
                throw new NullPointerException("Файл pdf не найден");
            }
        }
    }

    @Test
    @DisplayName("Чтение файла из zip в xls")
    void readFileZipIsXlsTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("FileZip/FileZip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("production.xlsx")) {
                    XLS xls = new XLS(zis);
                    String product1 = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    String product2 = xls.excel.getSheetAt(0).getRow(15).getCell(1).getStringCellValue();
                    String product3 = xls.excel.getSheetAt(0).getRow(1).getCell(10).getStringCellValue();
                    assertThat(product1).contains("Железо");
                    assertThat(product2).contains("Бетон");
                    assertThat(product3).contains("Медь");
                    count++;
                    break;
                }
            }
            if (count == 0) {
                throw new NullPointerException("Файл xlsx не найден");
            }
        }
    }

    @Test
    @DisplayName("Чтение файла из zip в csv")
    void readFileZipIsCsvTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("FileZip/FileZip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("successfulCheckStateCityCsvFile.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvReader.readAll();
                    org.junit.jupiter.api.Assertions.assertEquals(5, data.size());
                    org.junit.jupiter.api.Assertions.assertArrayEquals(
                            new String[]{"NCR", "Delhi"}, data.get(0)
                    );
                    org.junit.jupiter.api.Assertions.assertArrayEquals(
                            new String[]{"Haryana", "Karnal"}, data.get(3)
                    );
                    count++;
                    break;
                }
            }
            if (count == 0) {
                throw new NullPointerException("Файл csv не найден");
            }
        }
    }
}
