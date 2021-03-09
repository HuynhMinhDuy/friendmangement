package com.example.friendmangerment.dao;

import com.example.friendmangerment.model.FriendManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendManagement, FriendManagement.FriendPk> {
    @Query(nativeQuery = true, value
            = "select p2.email_address \n"
            + "  from friend_management f \n"
            + "  join people p1 on f.PID1 = p1.ID \n"
            + "  join people p2 on f.PID2 = p2.ID \n"
            + "  where p1.email_address = :email \n"
            + "union \n"
            + "select p1.email_address \n"
            + "  from friend_management f \n"
            + "  join people p1 on f.PID1 = p1.ID \n"
            + "  join people p2 on f.PID2 = p2.ID \n"
            + "  where p2.email_address = :email")
    List<String> findAllFriendsEmail(@Param("email") String email);

}
