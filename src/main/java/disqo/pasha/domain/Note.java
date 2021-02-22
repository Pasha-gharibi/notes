package disqo.pasha.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Note.
 */
@Entity
@Table(name = "t_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 50)
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 1000)
    @Column(name = "note")
    private String note;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "create_time")
    private LocalDate createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "last_update_time")
    private LocalDate lastUpdateTime;

    @ManyToOne
    @JsonIgnoreProperties(value = "notes", allowSetters = true)
    private User user;

    public Note() {
    }

    public Note(@Size(max = 50) String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Note note(String note) {
        this.note = note;
        return this;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public Note createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Note lastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Note user(User user) {
        this.user = user;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Note)) {
            return false;
        }
        return id != null && id.equals(((Note) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Note{" +
                "id=" + getId() +
                ", title=" + getTitle() +
                ", note='" + getNote() + "'" +
                ", createTime='" + getCreateTime() + "'" +
                ", lastUpdateTime='" + getLastUpdateTime() + "'" +
                "}";
    }
}
