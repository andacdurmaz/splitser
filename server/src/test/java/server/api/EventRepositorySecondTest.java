package server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import commons.Event;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import server.database.EventRepository;

public abstract class EventRepositorySecondTest implements EventRepository {

    public List<Event> events = new ArrayList<>();
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

    }

    @Override
    public <S extends Event> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Event> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Event> entities) {
        for (Event deleted : entities) {
            events = events.stream().filter(q -> !q.equals(deleted)).toList();
        }
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        for (Long id : ids) {
            events = events.stream().filter(q -> q.getId() != id).toList();
        }
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
        return null;
    }

    @Override
    public <S extends Event> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Event> S save(S entity) {
        call("New event saved");
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
    }

    @Override
    public void delete(Event entity) {

    }


    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Event> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }


}