package qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.JsonBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class ParsingJsonFileTest {
    private final ClassLoader cl = ParsingZipFileTest.class.getClassLoader();

    @Test
    @DisplayName("Чтение файла json")
    void jsonFileParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("filetest.json")) {
            ObjectMapper mapper = new ObjectMapper();
            JsonBody fileTest = mapper.readValue(is, JsonBody.class);
            Assertions.assertThat(fileTest.getLast_name()).isEqualTo("Ivanov");
            Assertions.assertThat(fileTest.getFirst_name()).isEqualTo("Ivan");
            Assertions.assertThat(fileTest.getPatronymic()).isEqualTo("Ivanovich");

            Assertions.assertThat(fileTest.getGroup_number().get(0).getValue()).isEqualTo(1);

            Assertions.assertThat(fileTest.getGroup_number().get(1).getValue()).isEqualTo(2);

        }
    }
}
