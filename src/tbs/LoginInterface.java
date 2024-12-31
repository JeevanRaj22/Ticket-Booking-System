package tbs;

import tbs.theatre.Theatre;
import tbs.users.Buyer;

public interface LoginInterface {
    Buyer userLogin(int userId,String password);
    Theatre theatreLogin(int userId,String password);
}
