package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ConfigFileServiceTest {
    @Mock
    private ServerUtils server;
    @InjectMocks
    private ConfigFileService service;
    @Mock
    ServerUtils serverUtils;

    /**
     * BeforeEach method
     */
    @BeforeEach
    public void setup() {
        service = new ConfigFileService(null);
        serverUtils = Mockito.mock(ServerUtils.class);
    }

    @Test
    void getJoinedEventsIDProvidingPath() {
    }

    @Test
    void getJoinedEvents() {
    }

    @Test
    void isEventInConfig() {
    }

    @Test
    void deleteEventFromConfig() {
    }

    @Test
    void deleteEventFromConfigProvidingPath() {
    }

    @Test
    void getJoinedEventsProvidingPath() {
    }

    @Test
    void getLanguage() {
    }

    @Test
    void getLanguageProvidingPath() {
    }

    @Test
    void getCurrency() {
    }

    @Test
    void getCurrencyProvidingPath() {
    }

    @Test
    void readConfigFile() {
    }

    @Test
    void writeEventToConfigFile() {
    }

    @Test
    void writeEventToConfigFileByPath() {
    }

    @Test
    void writeLanguageToConfigFile() {
    }

    @Test
    void writeLanguageToConfigFileByPath() {
    }

    @Test
    void writeCurrencyToConfigFile() {
    }

    @Test
    void writeCurrencyToConfigFileByPath() {
    }

    @Test
    public void testWriteServerAddressToConfig(){
        String tempFilePath = "CONFIGTest.json";
        String jsonString = "{" +
                "  \"User\":{" +
                "    \"name\":\"John Doe\"," +
                "    \"Language\":\"en\"," +
                "    \"Currency\":\"USD\"," +
                "    \"Events\":[" +
                "      {" +
                "        \"id\":0," +
                "        \"title\": \"title\"," +
                "        \"amountOfParticipants\":3," +
                "        \"expenses\":[]," +
                "        \"description\":\"description\"," +
                "        \"sumOfExpenses\":0.0" +
                "      }" +
                "    ]" +
                "  }" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        service.writeServerAddressToConfigFileByPath(tempFilePath, "localhost:8080");

        String newContent = service.readConfigFile(tempFilePath);
        String expected = "{\"User\":{\"Language\":\"en\",\"Events\":[{\"amountOfParticipants\":3,\"sumOfExpenses\":0,\"description\":\"description\",\"id\":0,\"title\":\"title\",\"expenses\":[]}],\"Currency\":\"USD\",\"name\":\"John Doe\",\"ServerAddress\":\"localhost:8080\"}}";
        assertEquals(expected, newContent);

        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetServerAddressFromConfig(){
        String tempFilePath = "CONFIGTest.json";
        String jsonString = "{" +
                "  \"User\":{" +
                "    \"name\":\"John Doe\"," +
                "    \"Language\":\"en\"," +
                "    \"Currency\":\"USD\"," +
                "    \"Events\":[" +
                "      {" +
                "        \"id\":0," +
                "        \"title\": \"title\"," +
                "        \"amountOfParticipants\":3," +
                "        \"expenses\":[]," +
                "        \"description\":\"description\"," +
                "        \"sumOfExpenses\":0.0" +
                "      }" +
                "    ]," +
                "  \"ServerAddress\":\"localhost:8080\"" +
                "  }" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = service.getServerAddressProvidingPath(tempFilePath);
        assertEquals("localhost:8080", result);

        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoveEventFromConfigByID() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String tempFilePath = "CONFIGTest.json";
        String jsonString = "{" +
                "  \"User\":{" +
                "    \"name\":\"John Doe\"," +
                "    \"Language\":\"en\"," +
                "    \"Currency\":\"USD\"," +
                "    \"Events\":[" +
                "      {" +
                "        \"id\":0," +
                "        \"title\": \"title\"," +
                "        \"amountOfParticipants\":3," +
                "        \"expenses\":[]," +
                "        \"description\":\"description\"," +
                "        \"sumOfExpenses\":0.0" +
                "      }," +
                "      {" +
                "        \"id\":1," +
                "        \"title\": \"title 2\"," +
                "        \"amountOfParticipants\":4," +
                "        \"expenses\":[]," +
                "        \"description\":\"description 2\"," +
                "        \"sumOfExpenses\":21.89" +
                "      }" +
                "    ]" +
                "  }" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        service.deleteEventFromConfigByIDProvidingPath(tempFilePath, 0);

        String newContent = service.readConfigFile(tempFilePath);
        String expected = "{\"User\":{\"Language\":\"en\",\"Events\":[{\"amountOfParticipants\":4,\"sumOfExpenses\":21.89,\"description\":\"description 2\",\"id\":1,\"title\":\"title 2\",\"expenses\":[]}],\"Currency\":\"USD\",\"name\":\"John Doe\"}}";
        assertEquals(objectMapper.readTree(expected), objectMapper.readTree(newContent));

        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoveEventFromConfigByIDWrongEvent(){
        ObjectMapper objectMapper = new ObjectMapper();
        String tempFilePath = "CONFIGTest.json";
        String jsonString = "{" +
                "  \"User\":{" +
                "    \"name\":\"John Doe\"," +
                "    \"Language\":\"en\"," +
                "    \"Currency\":\"USD\"," +
                "    \"Events\":[" +
                "      {" +
                "        \"id\":1," +
                "        \"title\": \"title\"," +
                "        \"amountOfParticipants\":3," +
                "        \"expenses\":[]," +
                "        \"description\":\"description\"," +
                "        \"sumOfExpenses\":0.0" +
                "      }," +
                "      {" +
                "        \"id\":2," +
                "        \"title\": \"title 2\"," +
                "        \"amountOfParticipants\":4," +
                "        \"expenses\":[]," +
                "        \"description\":\"description 2\"," +
                "        \"sumOfExpenses\":21.89" +
                "      }" +
                "    ]" +
                "  }" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertFalse(service.deleteEventFromConfigByIDProvidingPath(tempFilePath, 500));


        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteAllEventsFromConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String tempFilePath = "CONFIGTest.json";
        String jsonString = "{" +
                "  \"User\":{" +
                "    \"name\":\"John Doe\"," +
                "    \"Language\":\"en\"," +
                "    \"Currency\":\"USD\"," +
                "    \"Events\":[" +
                "      {" +
                "        \"id\":1," +
                "        \"title\": \"title\"," +
                "        \"amountOfParticipants\":3," +
                "        \"expenses\":[]," +
                "        \"description\":\"description\"," +
                "        \"sumOfExpenses\":0.0" +
                "      }," +
                "      {" +
                "        \"id\":2," +
                "        \"title\": \"title 2\"," +
                "        \"amountOfParticipants\":4," +
                "        \"expenses\":[]," +
                "        \"description\":\"description 2\"," +
                "        \"sumOfExpenses\":21.89" +
                "      }" +
                "    ]" +
                "  }" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        service.deleteAllEventsFromConfigProvidingPath(tempFilePath);

        String newContent = service.readConfigFile(tempFilePath);
        String expected = "{\"User\":{\"Language\":\"en\",\"Events\":[],\"Currency\":\"USD\",\"name\":\"John Doe\"}}";
        assertEquals(objectMapper.readTree(expected), objectMapper.readTree(newContent));

        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}