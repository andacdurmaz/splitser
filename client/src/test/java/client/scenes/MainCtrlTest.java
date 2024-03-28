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
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class MainCtrlTest {

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
        String tempFilePath = "CONFIGTest.json";
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
                "        \"description\": \"description\",\n" +
                "        \"sumOfExpenses\": 0.0\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"eventCode\": 7239,\n" +
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
        Files.writeString(path, configJson);

        // Call the method under test
        MainCtrl mainCtrl = new MainCtrl();
        String result = mainCtrl.readConfigFile(tempFilePath);
        assertEquals(configJson, result);

        // Clean up: delete the temporary file
        Files.deleteIfExists(path);
    }


    // NOT WORKING YET
    @Test
    void testGetJoinedEvents() throws IOException {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create a sample CONFIG.json content for testing
        String configJson = "{\"events\":[{\"id\":1},{\"id\":2},{\"id\":3}]}";

        // Mock the behavior of readConfigFile method to return the sample JSON

        MainCtrl mainCtrl = spy(new MainCtrl());
        doReturn(configJson).when(mainCtrl).readConfigFile(anyString());

        // Mock the behavior of serverUtils.getEventById() to return a dummy Event
        when(serverUtils.getEventById(anyLong())).thenReturn(new Event());

        // Call the method under test
        List<Event> joinedEvents = mainCtrl.getJoinedEvents();

        // Verify that serverUtils.getEventById was called the correct number of times
        verify(serverUtils, times(3)).getEventById(anyLong());

        // Assert the size of the returned list
        assertEquals(3, joinedEvents.size());
    }

}