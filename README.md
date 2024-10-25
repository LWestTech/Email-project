# Description

Simple demonstration of seperate client-server processes communicating with sockets writen in Java with frontend applications for both services

# Dependancies

JDK: I am using Liberica JDK 21 full, but anything with JavaFX should work

# Structure

The <ConnectionHandler> class is in charge of constantly sending <ServerPackets> to the client socket, and constantly checking for recieved <ClientPackets> to see if the client is alive and listening on the other end.

<ServerPackets> and <ClientPackets> should both include an <Instant> to measure ping, a <data> parameter to contain actual data, and a hash code parameter used to confirm messages as recieved.

**Client** has object

**Client** stores hash of object

**Client** sends <ClientPacket>

**Server** recieves <ClientPacket> and hashes the object recieved

**Server** sends <ServerPacket> back to **Client** with the hash parameter containing the recieved object's hash.

**Client** recieves <ServerPacket> with the hash.

**Client** discards any saved hashes and coorisponding objects that match the hash returned in the ServerPacket