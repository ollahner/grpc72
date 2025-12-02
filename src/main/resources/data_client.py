import grpc
import hello_pb2
import hello_pb2_grpc
import time

def run():
    print("Starting Python client to send Warehouse Data...")
    # Verbindung zum Java-Server
    channel = grpc.insecure_channel('localhost:50051')
    stub = hello_pb2_grpc.HelloWorldServiceStub(channel)

    # Aktuellen Unix-Timestamp in Sekunden erstellen
    current_timestamp = int(time.time())

    # WarehouseDataRecord erstellen
    record = hello_pb2.WarehouseDataRecord(
        warehouse_id=205,
        warehouse_name="Zentrallager Berlin",
        timestamp=current_timestamp
    )

    print(f"Sending record ID: {record.warehouse_id}, Name: {record.warehouse_name}, Timestamp: {record.timestamp}")

    try:
        # RPC aufrufen: sendWarehouseData
        response = stub.sendWarehouseData(record)
        print("-------------------------")
        print("Server response:", response.message)
        print("-------------------------")
    except grpc.RpcError as e:
        print(f"Could not connect to gRPC server: {e}")
        print("Ensure the Java Server (HelloWorldServer.java) is running on port 50051.")


if __name__ == "__main__":
    run()