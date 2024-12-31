import java.io.BufferedReader;
import java.io.InputStreamReader;

import tbs.LoginInterface;
import tbs.TbsApp;
import tbs.theatre.Theatre;
import tbs.users.Buyer;

public class App {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TbsApp app = new TbsApp();
        LoginInterface auth = app.getLoginInterface();
        System.out.println("\n\nTicket Booking System");
        while(true){
            System.out.println("\nMenu:");
            System.out.println("1.User login"); 
            System.out.println("2.Theatre login");
            System.out.println("3.Exit");
            System.out.println("\nChoose an option:");

            int option = Integer.parseInt(br.readLine());

            if(option == 1){
                System.out.print("\n\nEnter userId:");
                int userId = Integer.parseInt(br.readLine());
                System.out.print("\nEnter password:");
                String password = br.readLine();
                
                Buyer user = auth.userLogin(userId, password);
                if(user != null){
                    app.runInterface(user,br);
                }
            }else if(option == 2){
                System.out.print("\n\nEnter userId:");
                int userId = Integer.parseInt(br.readLine());
                System.out.print("\nEnter password:");
                String password = br.readLine();
                
                Theatre theatre = auth.theatreLogin(userId, password);
                if(theatre != null){
                    app.runInterface(theatre,br);
                }
            }
            else if(option == 3){
                break;
            }else{
                System.out.println("Invalid option...");
            }
        }
    }
    
}
