package com.lawrenceekale.grpc.server;

import com.lawrenceekale.grpc.entity.MessageEntity;
import com.lawrenceekale.grpc.repository.MessageRepository;
import com.lawrenceekale.message.GetMessageByIdRequest;
import com.lawrenceekale.message.Message;
import com.lawrenceekale.message.MessageResponse;
import com.lawrenceekale.message.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MessageServer extends MessageServiceGrpc.MessageServiceImplBase {
    //Logger logger = LoggerFactory.getLogger(MessageServer.class);
    @Autowired
    private MessageRepository messageRepository;
    @Override
    public void sendMessage(Message request, StreamObserver<MessageResponse> responseObserver) {
        MessageEntity message = MessageEntity.builder()
                .time(new Timestamp(System.currentTimeMillis()))
                .content(request.getContent())
                .senderId(request.getSenderId())
                .recipientId(request.getRecipientId())
                .isRead(false)
                .build();
        messageRepository.save(message);
        responseObserver.onCompleted();
    }

    @Override
    public void getMessageById(GetMessageByIdRequest request, StreamObserver<MessageResponse> responseObserver) {
        List<MessageEntity> messageEntities = messageRepository.findBySenderIdOrRecipientId(request.getRecipientId());
        List<Message> messages = messageEntities.stream()
                .map(this::convertToMessage)
                .collect(Collectors.toList());

        MessageResponse.Builder responseBuilder = MessageResponse.newBuilder();
        responseBuilder.addAllMessages(messages);

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    private Message convertToMessage(MessageEntity messageEntity) {
        return Message.newBuilder()
                .setMessageId(messageEntity.getMessageId())
                .setSenderId(messageEntity.getSenderId())
                .setRecipientId(messageEntity.getRecipientId())
                .setContent(messageEntity.getContent())
                .setIsRead(messageEntity.getIsRead())
                .build();
    }
}
