package client.services;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
@Service
public class ConfigFileService {

    private static final String CONFIG_PATH = "src/main/resources/CONFIG.json";

    private ServerUtils server;
    /**
     * @param server server
     */
    @Inject
    public ConfigFileService(ServerUtils server) {
        this.server = server;
    }

    /**
     * gets the events that the user has joined from the CONFIG file
     *
     * @param path path to the file
     * @return list of events
     */
    public List<Long> getJoinedEventsIDProvidingPath(String path) {
        List<Long> list = new ArrayList<>();

        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        JSONArray eventsArray = userObject.getJSONArray("Events");

        for (int i = 0; i < eventsArray.length(); i++) {
            JSONObject eventObject = eventsArray.getJSONObject(i);
            long eventId = eventObject.getLong("id");
            list.add(eventId);
        }
        return list;
    }

    /**
     * gets the events that the user has joined from the CONFIG file
     *
     * @return list of events
     */
    public List<Event> getJoinedEvents() {
        return getJoinedEventsProvidingPath(CONFIG_PATH);
    }

    /**
     * checks if the event is in the config file
     *
     * @param event event to be checked
     * @return true if the event is in the config file
     */
    public boolean isEventInConfig(Event event) {
        List<Long> eventIds = getJoinedEventsIDProvidingPath(CONFIG_PATH);
        return eventIds.contains(event.getId());
    }


    /**
     * removes the event from the config file
     * @param event event to be removed
     * @return true if the event is removed
     */
    public boolean deleteEventFromConfig(Event event){
        return deleteEventFromConfigProvidingPath(CONFIG_PATH, event);
    }

    /**
     * removes the event from the config file by path
     * @param path path to the file
     * @param event event to be removed
     * @return true if the event is removed
     */
    public boolean deleteEventFromConfigProvidingPath(String path, Event event) {
        List<Long> eventIds = getJoinedEventsIDProvidingPath(path);
        if (eventIds.contains(event.getId())) {
            JSONObject jsonObject = new JSONObject(readConfigFile(path));
            JSONObject userObject = jsonObject.getJSONObject("User");
            JSONArray eventsArray = userObject.getJSONArray("Events");

            // Find the index of the event object to remove
            int index = -1;
            for (int i = 0; i < eventsArray.length(); i++) {
                JSONObject eventJSON = eventsArray.getJSONObject(i);
                if (eventJSON.getLong("id") == event.getId()) {
                    index = i;
                    break;
                }
            }

            // If the event object is found, remove it from the eventsArray
            if (index != -1) {
                eventsArray.remove(index);
                userObject.put("Events", eventsArray);
                Path filePath = Path.of(path);
                try {
                    Files.writeString(filePath, jsonObject.toString());
                    return true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    /**
     * interacts with the server to get the events that the user has joined
     *
     * @param path path to the file
     * @return list of events
     */
    public List<Event> getJoinedEventsProvidingPath(String path)  {
        List<Long> eventIds = getJoinedEventsIDProvidingPath(path);
        List<Event> events = new ArrayList<>();

        for (Long l : eventIds) {
            events.add(server.getEventById(l));
        }
        return events;
    }

    /**
     * gets the language from the CONFIG file
     * @return the language
     */
    public String getLanguage() {
        return getLanguageProvidingPath(CONFIG_PATH);
    }

    /**
     * gets the language from the CONFIG file by path
     * @param path path to the file
     * @return  the language
     */
    public String getLanguageProvidingPath(String path) {
        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        return userObject.getString("Language");
    }

    /**
     * gets the currency from the CONFIG file
     * @return the currency
     */
    public String getCurrency()  {
        return getCurrencyProvidingPath(CONFIG_PATH);
    }

    /**
     * gets the currency from the CONFIG file by path
     * @param path path to the file
     * @return  the currency
     */
    public String getCurrencyProvidingPath(String path) {
        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        return userObject.getString("Currency");
    }

    /**
     * reads the config file
     *
     * @param filePath path to the file
     * @return the string representation of the file
     */
    public String readConfigFile(String filePath) {
        Path path = Path.of(filePath);
        try {
            String string = Files.readString(path);
            return string;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes to the config file
     * @param event event to be written (IN JSON FORMAT)
     */
    public void writeEventToConfigFile(Event event)  {
        writeEventToConfigFileByPath(CONFIG_PATH, event);
    }

    /**
     * writes to the config file by path
     * @param filePath path to the file
     * @param event event to be written (IN JSON FORMAT)
     */
    public void writeEventToConfigFileByPath(String filePath, Event event) {
        // Read the JSON file
        JSONObject jsonObject = new JSONObject(readConfigFile(filePath));
        // Get the User object
        JSONObject userObject = jsonObject.getJSONObject("User");
        // Get the Events array
        JSONArray eventsArray = new JSONArray();

        // Get the existing events
        JSONArray existingEvents = userObject.getJSONArray("Events");

        // Add the existing events to the new array
        for (int i = 0; i < existingEvents.length(); i++) {
            eventsArray.put(existingEvents.getJSONObject(i));
        }

        // Add the new event to the array
        JSONObject newEvent = new JSONObject(event);

        // Add all events back to the array
        eventsArray.put(newEvent);
        // Add the array back to the user object
        userObject.put("Events", eventsArray);

        // write to file
        Path path = Path.of(filePath);
        try {
            Files.writeString(path, jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes the language to the config file
     * @param language language to be written
     */
    public void writeLanguageToConfigFile(String language) {
        writeLanguageToConfigFileByPath(CONFIG_PATH, language);
    }

    /**
     * writes the language to the config file by path
     * @param filePath path to the file
     * @param language language to be written
     */
    public void writeLanguageToConfigFileByPath(String filePath, String language) {
        JSONObject jsonObject = new JSONObject(readConfigFile(filePath));
        JSONObject userObject = jsonObject.getJSONObject("User");
        userObject.put("Language", language);

        Path path = Path.of(filePath);
        try {
            Files.writeString(path, jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes the currency to the config file
     * @param currency currency to be written
     */
    public void writeCurrencyToConfigFile(String currency)  {
        writeCurrencyToConfigFileByPath(CONFIG_PATH, currency);
    }

    /**
     * writes the currency to the config file by path
     * @param filePath path to the file
     * @param currency currency to be written
     */
    public void writeCurrencyToConfigFileByPath(String filePath, String currency) {
        JSONObject jsonObject = new JSONObject(readConfigFile(filePath));
        JSONObject userObject = jsonObject.getJSONObject("User");
        userObject.put("Currency", currency);

        Path path = Path.of(filePath);
        try {
            Files.writeString(path, jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
