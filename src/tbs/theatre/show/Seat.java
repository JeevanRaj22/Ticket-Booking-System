package tbs.theatre.show;

public class Seat {
    private String seatNo;
    private SeatStatus status;
    private SeatType type;

    public Seat(String seatNo, SeatStatus status, SeatType type) {
        this.seatNo = seatNo;
        this.status = status;
        this.type = type;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public SeatType getType() {
        return type;
    }

    void setType(SeatType type) {
        this.type = type;
    }
}
