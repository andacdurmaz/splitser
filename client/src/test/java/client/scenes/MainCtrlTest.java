/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Event;
import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainCtrlTest {


    String tempFilePath = "Test.json";
    @InjectMocks
    private MainCtrl mainCtrl;
    @Mock
    ServerUtils serverUtils;

    /**
     * BeforeEach method
     */
    @BeforeEach
    public void setup() {
        mainCtrl = new MainCtrl();
        serverUtils = Mockito.mock(ServerUtils.class);
    }

    /**
     * Test method
     */
    @Test
    public void writeSomeTests() {
        // TODO create replacement objects and write some tests
        // sut.initialize(null, null, null);
    }

    @Test
    public void testReadConfigFile() throws IOException {
        // Create a temporary file with sample JSON data
        String configJson = "{\n" +
                "  \"User\" : {\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"Language\": \"en\",\n" +
                "    \"Currency\": \"USD\",\n" +
                "    \"Events\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"eventCode\": 4434,\n" +
                "        \"title\": \"title\",\n" +
                "        \"amountOfParticipants\": 3,\n" +
                "        \"expenses\": [],\n" +
                "        \"expenseTags\": [],\n" +
                "        \"description\": \"description\",\n" +
                "        \"sumOfExpenses\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"eventCode\": 7239,\n" +
                "        \"title\": \"title 2\",\n" +
                "        \"amountOfParticipants\": 4,\n" +
                "        \"expenses\": [],\n" +
                "        \"expenseTags\": [],\n" +
                "        \"description\": \"description 2\",\n" +
                "        \"sumOfExpenses\": 21.89\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        Files.writeString(path, configJson);

        // Call the method under test
        MainCtrl mainCtrl = new MainCtrl();
        String result = mainCtrl.readConfigFile(tempFilePath);
        assertEquals(configJson, result);

        // Clean up: delete the temporary file
        Files.deleteIfExists(path);
    }



    // only tests if it actually gets the event id from the config. does not interact with DB
    @Test
    public void testGetJoinedEventsID() throws IOException {


        String jsonString = "{\n" +
                "  \"User\" : {\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"Language\": \"en\",\n" +
                "    \"Currency\": \"USD\",\n" +
                "    \"Events\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"title\": \"title\",\n" +
                "        \"amountOfParticipants\": 3,\n" +
                "        \"expenses\": [],\n" +
                "        \"expenseTags\": [],\n" +
                "        \"description\": \"description\",\n" +
                "        \"sumOfExpenses\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"title 2\",\n" +
                "        \"amountOfParticipants\": 4,\n" +
                "        \"expenses\": [],\n" +
                "        \"expenseTags\": [],\n" +
                "        \"description\": \"description 2\",\n" +
                "        \"sumOfExpenses\": 21.89\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerUtils serverUtilsMock = Mockito.mock(ServerUtils.class);
        mainCtrl = Mockito.spy(new MainCtrl());

        // Mock the behavior of the readConfigFile method
        when(mainCtrl.readConfigFile("Test.json")).thenReturn(jsonString);

        // Mock the behavior of the getEventById method
        when(serverUtilsMock.getEventById(0)).thenReturn(new Event("title", 3, "description")); // Assuming this is the event with id 0
        when(serverUtilsMock.getEventById(1)).thenReturn(new Event("title 2", 4, "description 2")); // Assuming this is the event with id 1

        List<Long> result = mainCtrl.getJoinedEventsIDProvidingPath("Test.json");

        // Verify the result
        assertEquals(2, result.size()); // Assuming there are two events in the JSON

        assertEquals(0, result.get(0));
        assertEquals(1, result.get(1));

        // Clean up: delete the temporary file
        Files.deleteIfExists(path);
    }

    @Test
    public void testGetLanguage() throws IOException {

        String jsonString = "{\n" +
                "  \"User\" : {\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"Language\": \"en\",\n" +
                "    \"Currency\": \"USD\",\n" +
                "    \"Events\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"title\": \"title\",\n" +
                "        \"amountOfParticipants\": 3,\n" +
                "        \"expenses\": [],\n" +
                "        \"description\": \"description\",\n" +
                "        \"sumOfExpenses\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"title 2\",\n" +
                "        \"amountOfParticipants\": 4,\n" +
                "        \"expenses\": [],\n" +
                "        \"description\": \"description 2\",\n" +
                "        \"sumOfExpenses\": 21.89\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = mainCtrl.getLanguageProvidingPath(tempFilePath);
        assertEquals("en", result);

        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCurrency() throws IOException {

        String jsonString = "{\n" +
                "  \"User\" : {\n" +
                "    \"name\": \"John Doe\",\n" +
                "    \"Language\": \"en\",\n" +
                "    \"Currency\": \"USD\",\n" +
                "    \"Events\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"title\": \"title\",\n" +
                "        \"amountOfParticipants\": 3,\n" +
                "        \"expenses\": [],\n" +
                "        \"description\": \"description\",\n" +
                "        \"sumOfExpenses\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"title 2\",\n" +
                "        \"amountOfParticipants\": 4,\n" +
                "        \"expenses\": [],\n" +
                "        \"description\": \"description 2\",\n" +
                "        \"sumOfExpenses\": 21.89\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        // Write sample JSON to the temporary file
        Path path = Paths.get(tempFilePath);
        try {
            Files.writeString(path, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = mainCtrl.getCurrencyProvidingPath(tempFilePath);
        assertEquals("USD", result);

        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testWriteEventToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = "{" +
                "  \"User\":{" +
                "    \"name\":\"John Doe\"," +
                "    \"Events\":[" +
                "      {" +
                "        \"id\":0," +
                "        \"title\": \"title\"," +
                "        \"amountOfParticipants\":3," +
                "        \"expenses\":[]," +
                "        \"expenseTags\": []," +
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



        Event newEvent = new Event("title 3", 5, "description 3");
        mainCtrl.writeEventToConfigFileByPath(tempFilePath, newEvent);

        String newContent = mainCtrl.readConfigFile(tempFilePath);
        String expected = "{\"User\":{\"Events\":[{\"expenseTags\":[],\"amountOfParticipants\":3,\"sumOfExpenses\":0,\"description\":\"description\",\"id\":0,\"title\":\"title\",\"expenses\":[]},{\"eventCode\":0,\"expenseTags\":[],\"sumOfExpenses\":0,\"amountOfParticipants\":5,\"description\":\"description 3\",\"id\":0,\"title\":\"title 3\",\"expenses\":[],\"participants\":[]}],\"name\":\"John Doe\"}}";
        assertEquals(objectMapper.readTree(expected), objectMapper.readTree(newContent));

        // Clean up: delete the temporary file
        Files.deleteIfExists(path);
    }

    @Test
    public void testWriteLanguageToConfigFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

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

        mainCtrl.writeLanguageToConfigFileByPath(tempFilePath, "nl");

        String newContent = mainCtrl.readConfigFile(tempFilePath);
        String expected = "{\"User\":{\"Language\":\"nl\",\"Events\":[{\"amountOfParticipants\":3,\"sumOfExpenses\":0,\"description\":\"description\",\"id\":0,\"title\":\"title\",\"expenses\":[]}],\"Currency\":\"USD\",\"name\":\"John Doe\"}}";
        assertEquals(objectMapper.readTree(expected), objectMapper.readTree(newContent));

        // Clean up: delete the temporary file
        Files.deleteIfExists(path);
    }

    @Test
    public void testWriteCurrencyToConfigFile() throws IOException {

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

        mainCtrl.writeCurrencyToConfigFileByPath(tempFilePath, "EUR");

        String newContent = mainCtrl.readConfigFile(tempFilePath);
        String expected = "{\"User\":{\"Language\":\"en\",\"Events\":[{\"amountOfParticipants\":3,\"sumOfExpenses\":0,\"description\":\"description\",\"id\":0,\"title\":\"title\",\"expenses\":[]}],\"Currency\":\"EUR\",\"name\":\"John Doe\"}}";
        assertEquals(expected, newContent);

        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoveEventFromConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

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

        mainCtrl.deleteEventFromConfigProvidingPath(tempFilePath, new Event("title ", 3, "description"));

        String newContent = mainCtrl.readConfigFile(tempFilePath);
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
    public void testRemoveEventFromConfigWrongEvent(){
        ObjectMapper objectMapper = new ObjectMapper();

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

        assertFalse(mainCtrl.deleteEventFromConfigProvidingPath(tempFilePath, new Event("title 3", 5, "description 3")));


        // Clean up: delete the temporary file
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoveEventFromConfigException(){
        ObjectMapper objectMapper = new ObjectMapper();

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
        try {
            mainCtrl.deleteEventFromConfigProvidingPath("wrongPath", new Event("title 2", 4, "description 2"));
        } catch (RuntimeException e) {
            assertEquals("java.nio.file.NoSuchFileException: wrongPath", e.getMessage());
        }
    }

}