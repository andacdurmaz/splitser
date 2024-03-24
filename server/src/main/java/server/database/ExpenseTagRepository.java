package server.database;

import commons.ExpenseTag;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface ExpenseTagRepository extends JpaRepository<ExpenseTag, Long> {

    /**
     *
     */
    @Override
    default void flush() {

    }

    /**
     *
     * @param entity entity to be saved. Must not be {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> S saveAndFlush(S entity) {
        return null;
    }

    /**
     *
     * @param entities entities to be saved. Must not be {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     *
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    default void deleteAllInBatch(Iterable<ExpenseTag> entities) {

    }

    /**
     *
     * @param longs the ids of the entities to be deleted. Must not be {@literal null}.
     */
    @Override
    default void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    /**
     *
     */
    @Override
    default void deleteAllInBatch() {

    }

    /**
     *
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    default ExpenseTag getOne(Long aLong) {
        return null;
    }

    /**
     *
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    default ExpenseTag getById(Long aLong) {
        return null;
    }

    /**
     *
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    default ExpenseTag getReferenceById(Long aLong) {
        return null;
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @param sort the {@link Sort} specification to sort the results by,
     *             may be {@link Sort#unsorted()}, must not be
     *          {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     *
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    default List<ExpenseTag> findAll() {
        return null;
    }

    /**
     *
     * @param longs must not be {@literal null} nor contain any {@literal null} values.
     * @return
     */
    @Override
    default List<ExpenseTag> findAllById(Iterable<Long> longs) {
        return null;
    }

    /**
     *
     * @param entity must not be {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> S save(S entity) {
        return null;
    }

    /**
     *
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    default Optional<ExpenseTag> findById(Long aLong) {
        return Optional.empty();
    }

    /**
     *
     * @param aLong must not be {@literal null}.
     * @return
     */
    @Override
    default boolean existsById(Long aLong) {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    default long count() {
        return 0;
    }

    /**
     *
     * @param aLong must not be {@literal null}.
     */
    @Override
    default void deleteById(Long aLong) {

    }

    /**
     *
     * @param entity must not be {@literal null}.
     */
    @Override
    default void delete(ExpenseTag entity) {

    }

    /**
     *
     * @param longs must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    default void deleteAllById(Iterable<? extends Long> longs) {

    }

    /**
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    default void deleteAll(Iterable<? extends ExpenseTag> entities) {

    }

    /**
     *
     */
    @Override
    default void deleteAll() {

    }

    /**
     *
     * @param sort the {@link Sort} specification to sort the results by,
     *             can be {@link Sort#unsorted()}, must not be
     *          {@literal null}.
     * @return
     */
    @Override
    default List<ExpenseTag> findAll(Sort sort) {
        return null;
    }

    /**
     *
     * @param pageable the pageable to request a paged result,
     *                can be {@link Pageable#unpaged()}, must not be
     *          {@literal null}.
     * @return
     */
    @Override
    default Page<ExpenseTag> findAll(Pageable pageable) {
        return null;
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @param pageable the pageable to request a paged result,
     *                can be {@link Pageable#unpaged()}, must not be
     *          {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     *
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> long count(Example<S> example) {
        return 0;
    }

    /**
     *
     * @param example the {@link Example} to use for
     *               the existence check. Must not be {@literal null}.
     * @return
     * @param <S>
     */
    @Override
    default <S extends ExpenseTag> boolean exists(Example<S> example) {
        return false;
    }

    /**
     *
     * @param example must not be {@literal null}.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @return null
     * @param <S>
     * @param <R>
     */
    @Override
    default <S extends ExpenseTag, R> R findBy(Example<S> example,
                                               Function<FluentQuery.FetchableFluentQuery<S>, R>
            queryFunction) {
        return null;
    }
}
