package tbs.theatre.show;

import java.util.List;

public class Ticket {
    private int id;
    private int theatreId;
    private int screenId;
    private int showId;
    private int buyerId;
    private List<String> seatNo;
    private TicketStatus status;
    private int ticketPrice;

    public Ticket(int id,int theatreId,int screenId,int showId,int buyerId,List<String> seatNo,int ticketPrice) {
        this.id = id;
        this.theatreId = theatreId;
        this.screenId = screenId;
        this.showId = showId;
        this.seatNo = seatNo;
        this.buyerId = buyerId;
        this.status = TicketStatus.BOOKED;
        this.ticketPrice = ticketPrice;
    }

    public int getId() {
        return id;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public int getScreenId() {
        return screenId;
    }

    public int getShowId() {
        return showId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public List<String> getSeatNo() {
        return seatNo;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }
    
}
