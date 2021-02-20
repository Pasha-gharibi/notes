package disqo.pasha.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A User.
 */
@Entity
@Table(name = "t_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User(@Email(message = "Email should be valid") String email, @Size(min = 8) String password) {
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Email(message = "Email should be valid")
    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Size(min = 8)
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name = "last_update_time")
    private LocalDate lastUpdateTime;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Note> notes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public User createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public User lastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public User notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public User addNote(Note note) {
        this.notes.add(note);
        note.setUser(this);
        return this;
    }

    public User removeNote(Note note) {
        this.notes.remove(note);
        note.setUser(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                ", createTime='" + getCreateTime() + "'" +
                ", lastUpdateTime='" + getLastUpdateTime() + "'" +
                "}";
    }

}
