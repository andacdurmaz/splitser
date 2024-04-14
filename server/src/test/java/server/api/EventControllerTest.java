package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import commons.Event;
import commons.User;
import server.service.EventService;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;
    @InjectMocks
    private EventController eventController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        EventRepositoryTest repo = new EventRepositoryTest();
        eventController = new EventController(eventService);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testGetAllEvents() throws Exception {
        Event event = new Event("Title", 4, "Description");
        when(eventService.getAllEvents()).thenReturn(List.of(event));

        mockMvc.perform(get("/api/events/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(List.of(event).toString()));
    }



    @Test
    public void testGetEventTitleById() throws Exception {
        Event event = new Event("Title", 4, "Description");
        when(eventService.getEventTitleById(1)).thenReturn(ResponseEntity.ok("Title"));

        mockMvc.perform(get("/api/events/1/title"))
                .andExpect(status().isOk())
                .andExpect(content().string("Title"));
    }


    @Test
    public void testGetExpensesById() throws Exception {
        Event event = new Event("Title", 4, "Description");
        when(eventService.getExpensesByEventId(1)).thenReturn(List.of());

        mockMvc.perform(get("/api/events/1/expenses"))
                .andExpect(status().isOk())
                .andExpect(content().string(List.of().toString()));
    }

    @Test
    public void testGetParticipantsById() throws Exception {
        Event event = new Event("Title", 4, "Description");
        when(eventService.getParticipantsByEventId(1)).thenReturn(List.of());

        mockMvc.perform(get("/api/events/1/participants"))
                .andExpect(status().isOk())
                .andExpect(content().string(List.of().toString()));
    }


    @Test
    public void testGetDescriptionByEventId() throws Exception {
        Event event = new Event("Title", 4, "Description");
        when(eventService.getDescriptionByEventId(1)).thenReturn(ResponseEntity.ok("Description"));

        mockMvc.perform(get("/api/events/1/description"))
                .andExpect(status().isOk())
                .andExpect(content().string("Description"));
    }

    @Test
    public void testAddEvent() throws Exception {
        Event event = new Event("Title", 4, "Description");
        when(eventService.addEvent(event)).thenReturn(ResponseEntity.ok(event));

        mockMvc.perform(post("/api/events/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"eventCode\":0,\"title\":\"Title\",\"amountOfParticipants\":4,\"expenses\":[],\"description\":\"Description\",\"expenseTags\":[],\"sumOfExpenses\":0.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string(event.toString()));
    }


}
