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
import java.util.function.Consumer;

import commons.*;
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

    private static final String SERVER = "http://localhost:8080/";
    private static StompSession session;

    /**
     * Sets session
     */
    public void setSession() {
        //session = connect("ws://localhost:8080/websocket");
        session = connect("ws://" + address + "/websocket");
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @throws IOException        exception
     * @throws URISyntaxException exception
     */
    public void getQuotesTheHardWay() throws IOException, URISyntaxException {
        var url = new URI("http://localhost:8080/api/quotes").toURL();
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
                .target(SERVER).path("api/quotes") //
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
                .target(SERVER).path("api/quotes") //
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
                .target(SERVER)
                .path("api/events/all")
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
                .target(SERVER)
                .path("api/events/" + id)
                .request(APPLICATION_JSON).accept(APPLICATION_JSON)
                .get(new GenericType<Event>() {
                });
    }
    /**
     * Adds event
     *
     * @param event event to add
     */
    public void addEvent(Event event) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/events/add") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

    /**
     * Adds expense
     * @param expense to add
     */
    public void addExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/expenses/addOrEdit")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    /**
     * Updates the given expense
     *
     * @param expense expense to update
     */
    public void updateExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/expenses/update/" + expense.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(expense, APPLICATION_JSON), Expense.class);
    }

    /**
     * user to add
     * @param user
     */
    public void addUser(User user) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/users/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON), User.class);
    }

    /**
     * update user
     * @param user
     */
    public void updateUser(User user) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/users/update/" + user.getUserID())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(user, APPLICATION_JSON), User.class);
    }

    /**
     * updates given event
     *
     * @param event event to update
     */
    public void updateEvent(Event event) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/events/update/" + event.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(event, APPLICATION_JSON), Event.class);
    }

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
}