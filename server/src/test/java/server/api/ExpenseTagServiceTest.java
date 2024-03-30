package server.api;

import commons.Event;
import commons.ExpenseTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.database.EventRepository;
import server.database.ExpenseTagRepository;
import server.service.EventService;
import server.service.ExpenseTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseTagServiceTest {

    private ExpenseTagRepository expenseTagRepository;
    private ExpenseTagService expenseTagService;

    @BeforeEach
    void setUp() {
        expenseTagRepository = mock(ExpenseTagRepository.class);
        expenseTagService = new ExpenseTagService(expenseTagRepository);
    }

    @Test
    void getAllExpenseTags() {
        List<ExpenseTag> expenseTags = new ArrayList<>();
        expenseTags.add(new ExpenseTag( "Food", "#FFFFFF"));
        expenseTags.add(new ExpenseTag( "Travel", "#00FF00"));

        when(expenseTagRepository.findAll()).thenReturn(expenseTags);

        List<ExpenseTag> result = expenseTagService.getAllExpenseTags();

        assertEquals(2, result.size());
        assertEquals(expenseTags, result);
    }

    @Test
    void getExpenseTagById() {
        ExpenseTag expenseTag = new ExpenseTag( "Food", "#FFFFFF");

        when(expenseTagRepository.getById(1L)).thenReturn(expenseTag);

        ExpenseTag result = expenseTagService.getExpenseTagById(1L);

        assertEquals(expenseTag, result);
    }

    @Test
    void addExpenseTag() {
        ExpenseTag expenseTag = new ExpenseTag("Food", "#FFFFFF");
        ExpenseTagRepository expenseTagRepository = mock(ExpenseTagRepository.class);
        ExpenseTagService expenseTagService = new ExpenseTagService(expenseTagRepository);

        when(expenseTagRepository.save(expenseTag)).thenReturn(expenseTag);
        ResponseEntity<ExpenseTag> savedExpenseTag= expenseTagService.addExpenseTag(expenseTag);
        assertEquals(expenseTag, savedExpenseTag.getBody());
    }

    @Test
    void updateExpenseTag() {
        ExpenseTag expenseTag = new ExpenseTag( "Food", "#FFFFFF");

        when(expenseTagRepository.save(expenseTag)).thenReturn(expenseTag);

        ExpenseTag result = expenseTagService.updateExpenseTag(expenseTag);

        assertEquals(expenseTag, result);
    }

    @Test
    void deleteExpenseTag_existingId() {
        long id = 1L;

        when(expenseTagRepository.existsById(id)).thenReturn(true);

        ResponseEntity<ExpenseTag> result = expenseTagService.deleteExpenseTag(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(expenseTagRepository).deleteById(id);
    }

    @Test
    void deleteExpenseTag_nonExistingId() {
        long id = 1L;

        when(expenseTagRepository.existsById(id)).thenReturn(false);

        ResponseEntity<ExpenseTag> result = expenseTagService.deleteExpenseTag(id);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        verify(expenseTagRepository, never()).deleteById(id);
    }
}