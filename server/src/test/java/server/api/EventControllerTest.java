package server.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        Event event = new Event("Title", 4, 1234, "Description");
        when(eventService.getAllEvents()).thenReturn(List.of(event));

        mockMvc.perform(get("/event/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(List.of(event).toString()));
    }

    @Test
    public void testGetEventById() throws Exception {
        Event event = new Event("Title", 4, 1234, "Description");
        when(eventService.getEventById(1)).thenReturn(event);

        mockMvc.perform(get("/event/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(event.toString()));
    }

    @Test
    public void testGetEventTitleById() throws Exception {
        Event event = new Event("Title", 4, 1234, "Description");
        when(eventService.getEventTitleById(1)).thenReturn("Title");

        mockMvc.perform(get("/event/1/title"))
                .andExpect(status().isOk())
                .andExpect(content().string("Title"));
    }

    @Test
    public void testGetCreatorById() throws Exception {
        Event event = new Event("Title", 4, 1234, "Description");
        when(eventService.getCreatorById(1)).thenReturn(new User("username", "password"));

        mockMvc.perform(get("/event/1/creator"))
                .andExpect(status().isOk())
                .andExpect(content().string(new User("username", "password").toString()));
    }

    @Test
    public void testGetExpensesById() throws Exception {
        Event event = new Event("Title", 4, 1234, "Description");
        when(eventService.getExpensesByEventId(1)).thenReturn(List.of());

        mockMvc.perform(get("/event/1/expenses"))
                .andExpect(status().isOk())
                .andExpect(content().string(List.of().toString()));
    }

    @Test
    public void testGetDescriptionByEventId() throws Exception {
        Event event = new Event("Title", 4, 1234, "Description");
        when(eventService.getDescriptionByEventId(1)).thenReturn("Description");

        mockMvc.perform(get("/event/1/description"))
                .andExpect(status().isOk())
                .andExpect(content().string("Description"));
    }
}
