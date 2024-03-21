package server;

import commons.Admin;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SERV")
@AllArgsConstructor
@DiscriminatorValue("SERV")
public class Serv {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @ElementCollection
    @Column(name = "admins")
    private List<String> admins;

    @ElementCollection
    @Column(name = "events")
    private List<Long> events = new ArrayList<>();

    /**
     * Constructor
     */
    public Serv() {
        admins = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Constructor with admin
     *
     * @param admin admin
     */
    public Serv(Admin admin) {
        this.admins = new ArrayList<>();
        admins.add(admin.getEmail());
        events = new ArrayList<>();
    }

    /**
     * Getter method
     *
     * @return serv id
     */
    public long getId() {
        return id;
    }

    /**
     * Getter method
     *
     * @return all admins of this serv
     */
    public List<String> getAdmins() {
        return admins;
    }

    /**
     * Setter method
     *
     * @param admins admins to set as this serv's admins
     */
    protected void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    /**
     * Getter method
     *
     * @return all events of this serv
     */
    public List<Long> getEvents() {
        return events;
    }

    /**
     * Setter method
     *
     * @param events events to set as this serv's events
     */
    public void setEvents(List<Long> events) {
        this.events = events;
    }

    /**
     * Equals method
     *
     * @param o Object to compare
     * @return true if this is equal to Object o, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Serv serv = (Serv) o;

        if (getId() != serv.getId()) return false;
        if (getAdmins() != null ?
                !getAdmins().equals(serv.getAdmins()) : serv.getAdmins() != null) return false;
        return getEvents() != null ?
                getEvents().equals(serv.getEvents()) : serv.getEvents() == null;
    }

    /**
     * Hash method
     *
     * @return hash value
     */
    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getAdmins() != null ? getAdmins().hashCode() : 0);
        result = 31 * result + (getEvents() != null ? getEvents().hashCode() : 0);
        return result;
    }

    /**
     * toString method
     *
     * @return the string of this serv
     */
    @Override
    public String toString() {
        return "Serv{" +
                "id=" + id +
                ", admins=" + admins +
                ", events=" + events +
                '}';
    }
}
