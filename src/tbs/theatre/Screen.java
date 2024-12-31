package tbs.theatre;

import java.util.List;

public class Screen{
    private int id;
    private int theatreId;
    private String name;
    private int totalCapacity;
    private List<Integer> seatPerRows;
   

    public Screen(int id,int theatreId,String name, int totalCapacity, List<Integer> seatPerRows) {
        this.id = id;
        this.theatreId = theatreId;
        this.name = name;
        this.totalCapacity = totalCapacity;
        this.seatPerRows = seatPerRows;
    }
    
    public int getTheatreId() {
        return theatreId;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getTotalCapacity() {
        return totalCapacity;
    }
    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }
    public List<Integer> getSeatPerRows() {
        return seatPerRows;
    }
    public void setSeatPerRows(List<Integer> seatPerRows) {
        this.seatPerRows = seatPerRows;
    }  
}