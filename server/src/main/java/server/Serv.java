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
    public long id;

    @ElementCollection
    @Column(name = "admins")
    List<Long> admins;

    @ElementCollection
    @Column(name = "events")
    List<Long> events = new ArrayList<>();
    public Serv() {
        admins = new ArrayList<>();
        events = new ArrayList<>();
    }

    public Serv(Admin admin) {
        this.admins = new ArrayList<>();
        admins.add(admin.getId());
        events = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

   /* public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }*/

    public List<Long> getAdmins() {
        return admins;
    }

    protected void setAdmins(List<Long> admins) {
        this.admins = admins;
    }

    public List<Long> getEvents() {
        return events;
    }

    public void setEvents(List<Long> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Serv serv = (Serv) o;

        if (getId() != serv.getId()) return false;
        if (getAdmins() != null ? !getAdmins().equals(serv.getAdmins()) : serv.getAdmins() != null) return false;
        return getEvents() != null ? getEvents().equals(serv.getEvents()) : serv.getEvents() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getAdmins() != null ? getAdmins().hashCode() : 0);
        result = 31 * result + (getEvents() != null ? getEvents().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Serv{" +
                "id=" + id +
                ", admins=" + admins +
                ", events=" + events +
                '}';
    }
}
