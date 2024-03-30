package server.api;

import commons.Event;
import commons.Expense;

import commons.ExpenseTag;
import commons.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;
import server.database.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;



public class EventRepositoryTest implements EventRepository{

    public final List<Event> events = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Event> findAll() {
        calledMethods.add("findAll");
        return events;
    }

    @Override
    public List<Event> findAll(Sort sort) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public List<Event> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public <S extends Event> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends Event> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Event> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Event> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub

    }

    @Override
    public Event getOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Event getById(Long id) {
        call("getById");
        return find(id).get();
    }

    @Override
    public Event getReferenceById(Long id) {
        call("getReferenceById");
        return find(id).get();
    }

    private Optional<Event> find(Long id) {
        return events.stream().filter(q -> q.getId() == id).findFirst();
    }

    @Override
    public <S extends Event> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Event> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Event> S save(S entity) {
        call("save");
        entity.setId((long) events.size());
        events.add(entity);
        return entity;
    }

    @Override
    public Optional<Event> findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    @Override
    public long count() {
        return events.size();
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Event entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll(Iterable<? extends Event> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends Event> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Event> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Event> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends Event> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <S extends Event, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * gets the event by its ID
     *
     * @param id the id of the event
     * @return the event of the given id
     */
    @Override
    public Event getEventById(long id) {
        return null;
    }

//    @Override
//    public Event getEventByTitle(String title) {
//        return null;
//    }
//
//    @Override
//    public Event getEventByEventCode(long eventCode) {
//        return null;
//    }


    /**
     * Checks if the event with this ID exists
     *
     * @param id id to check
     * @return true if event with the given ID exists, false otherwise
     */
    @Override
    public Boolean existsById(long id) {
        return null;
    }

    /**
     * gets the creator of a certain event
     *
     * @param id of the event
     * @return the creator
     */
    @Override
    public User getCreatorById(long id) {
        return null;
    }

    /**
     * gets the title of the event by its id
     *
     * @param id of the event
     * @return title
     */
    @Override
    public ResponseEntity<String> getEventTitleById(long id) {
        return null;
    }

    /**
     * gets the expenses from the event
     *
     * @param id of the event
     * @return expenses in a list
     */
    @Override
    public List<Expense> getExpensesById(long id) {
        return null;
    }

    /**
     * gets the description of a event by its id
     *
     * @param id of the event
     * @return the description
     */
    @Override
    public ResponseEntity<String> getDescriptionById(long id) {
        return null;
    }

    @Override
    public List<User> getParticipantsById(long id) {
        return null;
    }

    @Override
    public List<ExpenseTag> getExpenseTagsById(long id) {
        return null;
    }
}
