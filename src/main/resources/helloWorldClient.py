import sys
import grpc

import hello_pb2 as hello_pb2
import hello_pb2_grpc as hello_pb2_grpc


def main():
    firstname = sys.argv[1] if len(sys.argv) > 1 else "Max"
    lastname = sys.argv[2] if len(sys.argv) > 2 else "Mustermann"

    print("Starting Python client to send HelloRequest...")
    # Connect to server
    with grpc.insecure_channel("localhost:50051") as channel:
        stub = hello_pb2_grpc.HelloWorldServiceStub(channel)

        try:
            request = hello_pb2.HelloRequest(firstname=firstname, lastname=lastname)
            response = stub.hello(request)
            print()
            print(response.text)
            print()
        except grpc.RpcError as e:
            print(f"Could not connect to gRPC server: {e}")
            print("Ensure the Java Server (HelloWorldServer.java) is running on port 50051.")

if __name__ == "__main__":
    main()