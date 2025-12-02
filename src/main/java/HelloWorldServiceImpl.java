import io.grpc.stub.StreamObserver;

public class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Override
    public void hello( Hello.HelloRequest request, StreamObserver<Hello.HelloResponse> responseObserver) {

        System.out.println("Handling hello endpoint: " + request.toString());

        String text = "Hello World, " + request.getFirstname() + " " + request.getLastname();
        Hello.HelloResponse response = Hello.HelloResponse.newBuilder().setText(text).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    // UPDATED METHOD: Implementierung für die Übertragung des WarehouseDataRecord
    @Override
    public void sendWarehouseData(Hello.WarehouseDataRecord request, StreamObserver<Hello.DataResponse> responseObserver) {

        System.out.println("--- NEW WAREHOUSE DATA RECEIVED ---");
        System.out.println("Warehouse ID: " + request.getWarehouseId());
        System.out.println("Warehouse Name: " + request.getWarehouseName());
        System.out.println("Timestamp: " + request.getTimestamp());
        System.out.println("-----------------------------------");

        String msg = "SUCCESS: WarehouseDataRecord received (ID: " + request.getWarehouseId() + ", Name: " + request.getWarehouseName() + ", Timestamp: " + request.getTimestamp() + ")";

        Hello.DataResponse response = Hello.DataResponse.newBuilder()
                .setMessage(msg)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}