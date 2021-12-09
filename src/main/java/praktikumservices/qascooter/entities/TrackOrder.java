package praktikumservices.qascooter.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class TrackOrder {
    private int track;

    public TrackOrder setTrackOrder(int track) {
        this.track = track;
        return this;
    }
}
