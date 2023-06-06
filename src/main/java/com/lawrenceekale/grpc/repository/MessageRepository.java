package com.lawrenceekale.grpc.repository;


import com.lawrenceekale.grpc.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    @Query("SELECT m FROM MessageEntity m WHERE m.senderId = :recipientId OR m.recipientId = :recipientId")
    List<MessageEntity> findBySenderIdOrRecipientId(long recipientId);
}
