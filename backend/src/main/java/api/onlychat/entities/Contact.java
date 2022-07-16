package api.onlychat.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "principal")
    private UserAccount principal;
    @ManyToOne
    @JoinColumn(name = "friend")
    private UserAccount friend;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date_time;

    @JsonIgnore
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "contact_message",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    Set<Message> messages = new HashSet<>();

    public Contact() {
    }

    public Contact(Long id, UserAccount principal, UserAccount friend, LocalDateTime date_time) {
        this.id = id;
        this.principal = principal;
        this.friend = friend;
        this.date_time = date_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public UserAccount getPrincipal() {
        return principal;
    }

    public void setPrincipal(UserAccount principal) {
        this.principal = principal;
    }

    public UserAccount getFriend() {
        return friend;
    }

    public void setFriend(UserAccount friend) {
        this.friend = friend;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
