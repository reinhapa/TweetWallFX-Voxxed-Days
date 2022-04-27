package org.tweetwallfx.voxxed.dto;

import org.tweetwallfx.devoxx.api.cfp.client.Room;
import org.tweetwallfx.devoxx.api.cfp.client.Rooms;

import java.util.List;

public class VRoom {
    private int id;
    private String name;
    private int capacity;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Room room() {
        Room room = new Room();
        room.setId(Integer.toString(id));
        room.setName(name);
        room.setCapacity(capacity);
        return room;
    }

    public static Rooms rooms(List<VRoom> vRooms) {
        Rooms rooms = new Rooms();
        rooms.setRooms(vRooms.stream().map(VRoom::room).toList());
        return rooms;
    }
}
