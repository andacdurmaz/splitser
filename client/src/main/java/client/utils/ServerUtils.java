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
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import commons.*;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.springframework.core.NestedRuntimeException;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ServerUtils extends Util {


    /**
     * @return the current server address
     */
    public static String getServer() {
        return serverAddress;
    }

    /**
     * @param server setter for the server address parameter
     */
    public static void setServer(String server) {
        ServerUtils.serverAddress = server;
    }

    private static StompSession session;

    /**
     * Sets session
     */
    public void setSession() {
        session = connect("ws://localhost:8080/websocket");
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @throws IOException        exception
     * @throws URISyntaxException exception
     */
    public void getQuotesTheHardWay() throws IOException, URISyntaxException {
        var url = new URI(serverAddress + "api/quotes").toURL();
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return quotes
     */
    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {
                });
    }

    /**
     * Adds quotes
     *
     * @param quote quote to add
     * @return added quote
     */
    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }

    /**
     * Gets events the hard way
     *
     * @throws IOException        exception
     * @throws URISyntaxException exception
     */
    public void getEventsTheHardWay() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/api/events").toURL();
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Get events method
     *
     * @return events
     */
    public List<Event> getEvents() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("api/events/all")
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .get(new GenericType<List<Event>>() {
                });
    }

    /**
     * Get event by id
     * @param id of the event
     * @return the event from the id
     */
    public Event getEventById(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress)
                .path("api/events/" + id)
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .get(new GenericType<Event>() {
                });
    }
    /**
     * Adds event
     *
     * @param event event to add
     * @return the added event
     */

    public Event addEvent(Event event) {
        Response response = ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/events/add") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON));
        Event event1 = response.readEntity(new GenericType<>() {
        });
        return event1;



    }

    /**
     * Delete event
     *
     * @param event event to add
     */
    public void deleteEvent(Event event) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/events/delete/" + event.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete();
    }

    /**
     * Delete event

     * @param expense to delete
     */
    public void deleteExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/expenses/delete/" + expense.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete();
    }

    /**
     * Delete debt
     * @param debt to delete
     */
    public void deleteDebts(Debt debt) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/debts/delete/" + debt.getId()) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete();
    }

    /**
     * Adds expense
     * @param expense to add
     * @return the added expense
     */
    public Expense addExpense(Expense expense) {
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("api/expenses/add")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(expense, APPLICATION_JSON));
        Expense expense1 = response.readEntity(new GenericType<>() {
        });
        return expense1;
    }

    /**
     * Updates the given expense
     *
     * @param expense expense to update
     */
    public void updateExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress)
                .path("api/expenses/update/" + expense.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * Register a consumer (function) to execute when new event is added to the db
     * @param consumer
     */
    public void registerForEventUpdates(Consumer<Expense> consumer) {
        EXEC.submit(() -> {
            while (!Thread.interrupted()) {
                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(serverAddress).path("api/expenses/add/updates")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);

                if (res.getStatus() == 204) { // NO_CONTENT
                    continue;
                }

                var e = res.readEntity(Expense.class);
                consumer.accept(e);
            }
        });
    }



    /**
     * adds expense tag
     * @param expenseTag
     * @return adds an expense tag
     */
    public ExpenseTag addExpenseTag(ExpenseTag expenseTag) {
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("api/tags/add")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(expenseTag, APPLICATION_JSON));
        ExpenseTag newExpenseTag = response.readEntity(new GenericType<>() {
        });
        return newExpenseTag;

    }

    /**
     * user to add
     * @param user
     * @return the added User
     */

    public User addUser(User user) {
        Response response = ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/users/add") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(user, APPLICATION_JSON));
        User user1 = response.readEntity(new GenericType<>() {
        });
        return user1;

    }

    /**
     * update user
     * @param user
     */
    public void updateUser(User user) {
        ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress)
                .path("api/users/update/" + user.getUserID())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(user, APPLICATION_JSON), User.class);
    }


    /**
     * deletes a user from the database
     * @param user the deleted user
     */
    public void deleteUser(User user) {
        ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress)
                .path("api/users/delete/" + user.getUserID())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(user, APPLICATION_JSON), User.class);
    }


    /**
     * Get event by id
     * @param id of the event
     * @return the event from the id
     */
    public User getUserById(long id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress)
                .path("api/users/" + id)
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .get(new GenericType<User>() {
                });
    }




    /**
     * updates given event
     *
     * @param event event to update
     */
    public void updateEvent(Event event) {
        ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress)
                .path("api/events/update/" + event.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(event, APPLICATION_JSON), Response.class);
    }


//    public void updateEventByCode(Event event) {
//        ClientBuilder.newClient(new ClientConfig())
//                .target(SERVER)
//                .path("api/events/update/" + event.getEventCode())
//                .request(APPLICATION_JSON)
//                .accept(APPLICATION_JSON)
//                .put(Entity.entity(event, APPLICATION_JSON), Event.class);
//    }

    /**
     * @param targetUrl url of the server
     * @return StompSession
     * @throws InterruptedException connection with server was interrupted
     * @throws Exception            connection with server failed
     */
    private StompSession connect(String targetUrl) throws NestedRuntimeException {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(targetUrl, new StompSessionHandlerAdapter() {
            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    /**
     * subscribes client for messages on a specific server address
     *
     * @param destination    server address we want to subscribe to
     * @param packetType     the type of object we will be receiving
     * @param packetConsumer a class to store ant iterate over objects
     *                       received from the server
     * @param <T>            generic parameter, allowing for any number of
     *                       classes to utilize it
     */
    public <T> void registerForSocketMessages(String destination,
                                              Class<T> packetType, Consumer<T> packetConsumer) {
        session.subscribe(destination, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return packetType;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                packetConsumer.accept((T) payload);
            }
        });
    }

    /**
     * sends an object to a destination address
     *
     * @param destinationAddress server address to which we want
     *                           to send an updated object
     * @param o                  Object to be sent
     * @return a string useful for testing
     */
    public String send(String destinationAddress, Object o) {
        session.send(destinationAddress, o);
        return destinationAddress + o;
    }

    /**
     * @param password the password that was submitted
     * @return the boolean of the success of the login attempt
     */
    public int checkCredentials(String password) {

        Response ans = ClientBuilder
                .newClient(new ClientConfig())
                .target(serverAddress)
                .path("/api/servers/login")
                .queryParam("password", password)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Response.class);
        return ans.getStatusInfo().getStatusCode();

    }


    //private static final ExecutorService DELEXPENSE = Executors.newSingleThreadExecutor();

    /**
     * Creates the long polling connection that registers
     * and notifies when expenses are deleted
     * @param consumer buffer that keeps created expenses
     */
    public void regDeleteExpenses(Consumer<Expense> consumer) {
        session.subscribe("/updates/delete/expenses", new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Expense.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((Expense) payload);
            }
        });
    }


    //private static final ExecutorService ADDEXPENSE = Executors.newSingleThreadExecutor();

    /**
     * Creates the long polling connection that registers
     * and notifies when new expenses are created
     * @param consumer buffer that keeps created expenses
     */
    public void regEditExpenses(Consumer<Expense> consumer) {
        session.subscribe("/updates/edit/expenses", new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Expense.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((Expense) payload);
            }
        });
    }

    /**
     * shuts down the listeners
     *
     public void stop(){
     ADDEXPENSE.shutdownNow();
     DELEXPENSE.shutdownNow();
     }
     */
    /**
     * adds a debt to the database
     * @param debt the added debt
     * @return the added debt
     */
    public Debt addDebt(Debt debt) {
        Response response = ClientBuilder.newClient(new ClientConfig()) //
                .target(serverAddress).path("api/debts/add") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(debt, APPLICATION_JSON));
        Debt debt1 = response.readEntity(new GenericType<>() {
        });
        return debt1;
    }

    /**
     * gets all the debts from the database
     * @return the list of the debts
     */
    public List<Debt> getDebts() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("api/debts")
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .get(new GenericType<List<Debt>>() {
                });
    }

}