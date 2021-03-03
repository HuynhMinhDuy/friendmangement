package com.example.friendmangerment.model;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "friend_management")
@Check(constraints = "PID1 < PID2")
@Data
public class FriendManagement {

    private static final long serialVersionUID = 1L;

    @Embeddable
    @Data
    public static class FriendPk implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long pid1;
        private Long pid2;
    }
    @EmbeddedId
    private FriendPk id = new FriendPk();

    @JoinColumn(name = "PID1")
    @MapsId("pid1")
    @ManyToOne
    private Person person1;

    @JoinColumn(name = "PID2")
    @MapsId("pid2")
    @ManyToOne
    private Person person2;

}
