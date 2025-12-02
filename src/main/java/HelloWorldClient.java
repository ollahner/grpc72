import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.Instant;

public class HelloWorldClient {

    public static void main(String[] args) {

        String firstname = args.length > 0 ? args[0] : "Max";
        String lastname  = args.length > 1 ? args[1] : "Mustermann";

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        // Erstelle den Blocking Stub (synchroner Client)
        HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub = HelloWorldServiceGrpc.newBlockingStub(channel);

        // 1. Rufe den einfachen 'hello' RPC auf
        Hello.HelloResponse helloResponse = stub.hello(Hello.HelloRequest.newBuilder()
                .setFirstname(firstname)
                .setLastname(lastname)
                .build());
        System.out.println( helloResponse.getText() );
        System.out.println("-------------------------");


        // 2. Rufe den NEUEN 'sendWarehouseData' RPC auf
        // Wir verwenden Instant.now() um einen aktuellen Unix-Zeitstempel (in Sekunden) zu generieren
        long currentTimestamp = Instant.now().getEpochSecond();

        Hello.WarehouseDataRecord warehouseData = Hello.WarehouseDataRecord.newBuilder()
                .setWarehouseId(101)
                .setWarehouseName("Hauptlager Frankfurt")
                .setTimestamp(currentTimestamp)
                .build();

        System.out.println("Sending Warehouse Data: ID " + warehouseData.getWarehouseId() + ", Name: " + warehouseData.getWarehouseName());

        Hello.DataResponse dataResponse = stub.sendWarehouseData(warehouseData);
        System.out.println( dataResponse.getMessage() );

        channel.shutdown();

    }

}