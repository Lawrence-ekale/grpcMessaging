package com.lawrenceekale.messageGRPC;

import com.lawrenceekale.message.GetMessageByIdRequest;
import com.lawrenceekale.message.Message;
import com.lawrenceekale.message.MessageResponse;
import com.lawrenceekale.message.MessageServiceGrpc;
import com.lawrenceekale.message.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MessagegRPCService extends MessageServiceGrpc.MessageServiceImplBase {
    @Override
    public void sendMessage(Message request, StreamObserver<MessageResponse> responseObserver) {

    }

    @Override
    public void getMessageById(GetMessageByIdRequest request, StreamObserver<MessageResponse> responseObserver) {
        super.getMessageById(request, responseObserver);
    }
}
