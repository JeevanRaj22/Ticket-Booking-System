package tbs.movie;

import java.time.Duration;

public class Movie{
    private int id;
    private String name;
    private String description;
    private Duration runtime;
    private MovieStatus status;

    public Movie(int id,String name, String description, Duration runtime,MovieStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.runtime = runtime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
   
    public String getDescription() {
        return description;
    }
    
    public Duration getRuntime() {
        return runtime;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }
}
