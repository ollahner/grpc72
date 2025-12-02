Oliver Lahner 4AHIT
## Core Implementation Steps

1. **Phase 1: Grundlagen
    - **Definition:** Created `hello.proto` to define the `HelloWorldService` and the `hello(HelloRequest)` RPC.
    - **Java Implementation:** Implemented the gRPC Server and Client in Java, fulfilling the basic connectivity requirement.

2. **Phase 2: Erweiterte Grundlagen (Service Customization)**
    - **Protocol Update:** Extended `hello.proto` by adding the `WarehouseDataRecord` message (containing `warehouse_id`, `warehouse_name`, and `timestamp`) and the `sendWarehouseData` RPC.
    - **Server Logic:** Updated the Java `HelloWorldServiceImpl` to implement the `sendWarehouseData` RPC, logging the received data fields to the console.
    - **Java Client Update:** Adapted the Java client to demonstrate calling the new `sendWarehouseData` RPC.

3. **Phase 3: Vertiefung (Cross-Language Client)**
    - **Python Setup:** Installed `grpcio` and `grpcio-tools`.
    - **Stub Generation:** Used `py -m grpc_tools.protoc` to generate Python stubs (`hello_pb2.py` and `hello_pb2_grpc.py`) from `hello.proto`.
    - **Python Client:** Developed `python_warehouse_data_client.py`. This client establishes a connection, constructs a `WarehouseDataRecord`, and successfully calls the `sendWarehouseData` RPC on the running Java Server.

##### ``hello.proto`:
```
syntax = "proto3";  
service HelloWorldService {  
  rpc hello(HelloRequest) returns (HelloResponse) {}  
  rpc sendWarehouseData(WarehouseDataRecord) returns (DataResponse) {}  
}  
  
message HelloRequest {  
  string firstname = 1;  
  string lastname = 2;  
}  
  
message HelloResponse {  
  string text = 1;  
}  

message WarehouseDataRecord {  
  int32 warehouse_id = 1;     // Eindeutige ID des Lagers  
  string warehouse_name = 2;  // Name des Lagers (z.B. Region Ost, Hauptlager West)  
  int64 timestamp = 3;        // Zeitstempel der Aufzeichnung im Unix-Epoch-Format (Sekunden)  
}  

message DataResponse {  
  string message = 1;  
}
```
##### Server Implementation for Warehouse:
```java
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
```
##### Java Server Output:
![[Pasted image 20251202170255.png]]
##### Python Client:
![[Pasted image 20251202171433.png]]
## gRPC Questions
1. What is gRPC and why does it work accross languages and platforms?
   gRPC: A high-performance Remote Procedure Call (RPC) framework.  
   Cross-Platform: Uses Protocol Buffers (Protobuf) as a language-neutral Interface Definition Language (IDL). This allows for automatic code generation (stubs and messages) for various languages (Java, Python, Go, etc.) from a single .proto file, ensuring interoperability.

2. Describe the RPC life cycle starting with the RPC client?
   Client calls stub.  
   Client serializes data (Protobuf).  
   Request sent over HTTP/2 to Server.  
   Server deserializes and executes service logic.  
   Server serializes response.  
   Response sent back via HTTP/2.  
   Client deserializes the response for the application.  
   Shutterstock

3. Describe the workflow of Protocol Buffers?
   Define: Structure is written in a .proto file.  
   Compile: The protoc compiler is executed.  
   Generate: Language-specific source code (classes, serialization methods) is automatically created.  
   Use: Generated code is used in the application to manage data.

4. What are the benefits of using protocol buffers?
   Speed & Size: Fast, efficient, and compact binary data format.  
   Type Safety: Schema definition enforces data structure.  
   Evolution: Simple to update schemas while maintaining backward compatibility.

5. When is the use of protocol not recommended?
   When human readability is essential (e.g., public APIs, simple configuration files, or data that needs manual editing), as the binary format is unreadable.

6. List 3 different data types that can be used with protocol buffers?
   string  
   int32  
   bool