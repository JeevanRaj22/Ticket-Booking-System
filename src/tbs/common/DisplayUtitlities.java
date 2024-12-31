package tbs.common;

import java.util.List;

import tbs.movie.Movie;
import tbs.theatre.show.Seat;

public class DisplayUtitlities {
    public static void displaySeats(List<List<Seat>> seats){
        if(seats.isEmpty())
            return;
        for(List<Seat> rows: seats){
            System.out.print("\nRow "+rows.get(0).getSeatNo().charAt(0)+" : ");
            for(Seat x: rows){
                System.out.print(x.getSeatNo()+" ");
            }
        }
    }

    public static void printMovies(List<Movie> movies){
        System.out.println("\n\nChoose A Movie:");
        System.out.println("ID\tMovie\tRuntime\tDescription");
        System.out.println("--------------------------------------");
        movies.stream()
            .map(m->m.getId()+"\t"+m.getName()+"\t"+m.getRuntime()+"\t"+m.getDescription())
            .forEach(System.out :: println);
    }
    
    
}
