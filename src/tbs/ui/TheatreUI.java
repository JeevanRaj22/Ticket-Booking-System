package tbs.ui;

import java.io.BufferedReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import tbs.TheatreService;
import tbs.theatre.Theatre;


public class TheatreUI {
    private Theatre theatre;
    private TheatreService tbs;
    

    public TheatreUI(TheatreService tbs,Theatre theatre) {
        this.theatre = theatre;
        this.tbs = tbs;
    }

    public void dashboard(BufferedReader br)throws Exception{
        boolean menu = true;
        while(menu){
            System.out.println("\n\nMenu:");
            System.out.println("1.Add Screens");
            System.out.println("2.View Screens");
            System.out.println("3.Add Show");
            System.out.println("4.View Shows");
            System.out.println("5.Add Movie");
            System.out.println("6.Realese Movie");
            System.out.println("7.Exit");

            System.out.print("\nEnter an option:");
            int choice = Integer.parseInt(br.readLine());

            switch(choice){
                case 1:
                    if(this.addScreen(br))
                        System.out.println("Screen added successfully...");
                    else
                        System.out.println("couldn't add screen...");
                    break;
                case 2:
                    this.tbs.displayScreens(this.theatre.getId());
                    break;
                case 3:
                    if(this.addShow(br))
                        System.out.println("Show added successfully...");
                    else
                        System.out.println("couldn't add show...");
                    break;
                case 4:
                    this.viewShows(br);
                    break;
                case 5:
                    if(this.addMovie(br))
                        System.out.println("Movie added successfully...");
                    else
                        System.out.println("couldn't add Movie...");
                    break;
                case 6:
                    this.releaseMovie(br);
                    break;
                case 7:
                    menu = false;
                    break;
                default:
                    System.out.println("Invlaid option...");
            }
        }
    }

    private boolean addMovie(BufferedReader br)throws Exception{
        System.out.print("\n\nEnter movie name:");
        String name = br.readLine();

        System.out.print("\nEnter description:");
        String description = br.readLine();

        System.out.print("\nEnter duration of movie(in minutes):");
        int minutes = Integer.parseInt(br.readLine());

        return this.tbs.addMovie(name, description, Duration.ofMinutes(minutes));
    }
    
    private boolean addScreen(BufferedReader br)throws Exception{
        System.out.print("Enter screen name:");
        String name = br.readLine();

        System.out.print("\nEnter the total number of seats:");
        int totalSeats = Integer.parseInt(br.readLine());
        if(totalSeats <= 0){
            System.out.println("Invalid Input...");
            return false;
        }
        System.out.print("\nEnter the number of rows:");
        int rows = Integer.parseInt(br.readLine());
        if(rows <= 0){
            System.out.println("Invalid Input...");
            return false;
        }
        List<Integer> seatsPerRow = new ArrayList<Integer>();
        for(int i=0;i<rows;i++){
            System.out.print("\nEnter no of seats for row"+" "+(i+1)+":");
            int seats = Integer.parseInt(br.readLine());
            if(seats <= 0){
                System.out.println("Invalid Input...");
                return false;
            }
            seatsPerRow.add(seats);
        }
        return this.tbs.addScreen(this.theatre.getId() ,name, totalSeats, seatsPerRow);
    }

    private boolean addShow(BufferedReader br)throws Exception{
        if(!this.tbs.displayMovies()){
            return false;
         }

        System.out.print("\nEnter movie id:");
        int movieId = Integer.parseInt(br.readLine());

        if(!this.tbs.displayScreens(this.theatre.getId())){
            return false;
        }

        System.out.print("\nEnter Screen Id:");
        int screenId = Integer.parseInt(br.readLine());

        System.out.print("\nEnter Show date(yyyy-mm-dd):");
        String date = br.readLine();

        LocalDate sDate = LocalDate.parse(date);

        System.out.print("\nEnter Show Time(hh:mm):");
        String time = br.readLine();

        LocalTime sTime = LocalTime.parse(time);

        System.out.print("\nEnter number of premium rows:");
        int premiumRows = Integer.parseInt(br.readLine());
        
        System.out.print("Enter vip seat number(comma seperated(A1,B1)):");
        String vipSeatsArray[] = br.readLine().strip().split(",");

        System.out.print("\nEnter price for premium seat:");
        int premiumPrice = Integer.parseInt(br.readLine());
        if(premiumPrice<=0){
            System.out.println("Invlaid input...");
            return false;
        }

        System.out.print("\nEnter price for normal seat:");
        int normalPrice = Integer.parseInt(br.readLine());
        if(normalPrice<=0){
            System.out.println("Invlaid input...");
            return false;
        }

        return this.tbs.addShow(this.theatre.getId(), screenId, movieId, sDate, sTime, premiumRows, vipSeatsArray, premiumPrice, normalPrice);
    }

    private void viewShows(BufferedReader br)throws Exception{   
        if(!this.tbs.displayScreens(this.theatre.getId()))
            return;

        System.out.print("\nEnter screen ID to view shows:");
        int screenId = Integer.parseInt(br.readLine());

        if(!this.tbs.displayShows(this.theatre.getId(),screenId)){
            System.out.println("No show is available");
            return;
        }

        System.out.print("\nEnter show id to view information:");
        int showId = Integer.parseInt(br.readLine());
        
        this.tbs.displayShow(showId);
    }

    private boolean releaseMovie(BufferedReader br)throws Exception{
        if(!this.tbs.displayMovies()){
            return false;
         }

        System.out.print("\nEnter movie id:");
        int movieId = Integer.parseInt(br.readLine());

        System.out.print("\nEnter movie release date(yyyy-mm-dd):");
        String date = br.readLine();
        LocalDate sDate = LocalDate.parse(date);

        System.out.print("\nEnter no of days:");
        int n = Integer.parseInt(br.readLine());
        if(n<=0){
            System.out.println("\n Invalid no of days...");
            return false;
        }

        for(int i=0;i<n;i++){
            System.out.print("\n\nEnter no of shows on Day "+(i+1)+":");
            int noOfShows = Integer.parseInt(br.readLine());

            for(int j=0;j<noOfShows;j++){
                System.out.println("\nFor Day "+(i+1)+" Show "+(j+1)+":");
                if(!this.tbs.displayScreens(this.theatre.getId())){
                    return false;
                }
                System.out.print("\nEnter Screen Id:");
                int screenId = Integer.parseInt(br.readLine());

                System.out.print("\nEnter Show Time(hh:mm):");
                String time = br.readLine();
        
                LocalTime sTime = LocalTime.parse(time);

                System.out.print("\nEnter number of premium rows:");
                int premiumRows = Integer.parseInt(br.readLine());
                
                System.out.print("Enter vip seat number(comma seperated(A1,B1)):");
                String vipSeatsArray[] = br.readLine().strip().split(",");

                System.out.print("\nEnter price for premium seat:");
                int premiumPrice = Integer.parseInt(br.readLine());
                if(premiumPrice<=0){
                    System.out.println("Invlaid input...");
                    return false;
                }

                System.out.print("\nEnter price for normal seat:");
                int normalPrice = Integer.parseInt(br.readLine());
                if(normalPrice<=0){
                    System.out.println("Invlaid input...");
                    return false;
                }

                if(this.tbs.addShow(this.theatre.getId(), screenId, movieId, sDate.plusDays(i), sTime, premiumRows, vipSeatsArray, premiumPrice, normalPrice)){
                    System.out.println("Day "+(i+1)+" Show "+(j+1)+" added successfully");
                }else{
                    System.out.println("Couldn't add"+" Day "+(i+1)+" Show "+(j+1));
                }
            }

        }
        return true;
    }
}
