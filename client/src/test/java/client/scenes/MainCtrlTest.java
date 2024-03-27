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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainCtrlTest {

    private MainCtrl sut;

    /**
     * BeforeEach method
     */
    @BeforeEach
    public void setup() {
        sut = new MainCtrl();
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
    public void testGetJoinedEvents() {
        String testString = "{\n" +
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
        //TODO make a new method that allows the reader to be decoupled from the file.
    }
}