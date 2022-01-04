package jandi.server.model;

import jandi.server.util.BooleanToYNConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Attendance extends Timestamped {

    @Id
    @Column(name = "attendance_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "date")
    private LocalDate date;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "is_checked")
    private boolean is_checked;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "commit")
    private boolean commit;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "til")
    private boolean til;

    public void setUser(User user) {
        this.user = user;
        user.getAttendances().add(this);
    }

    public void setIs_checkedOn() {
        this.is_checked = true;
    }

    public void setCommitOn() {
        this.commit = true;
    }

    public void setTilOn() {
        this.til = true;
    }
}
