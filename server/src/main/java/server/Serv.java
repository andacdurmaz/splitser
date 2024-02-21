package server;

import commons.Admin;
import commons.Event;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class Serv {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    public Admin admin;

    List<Admin> admins;
    List<Event> events;
    public Serv() {
        admins = new ArrayList<>();
        events = new ArrayList<>();
    }

    public Serv(Admin admin) {
        this.admins = new ArrayList<>();
        admins.add(admin);
        events = new ArrayList<>();
    }


}
