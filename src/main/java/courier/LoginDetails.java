package courier;
import java.security.Principal;
public class LoginDetails implements org.apache.http.auth.Credentials
{
    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    private String login;

    private String password;

    public LoginDetails(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public static LoginDetails from(Courier courier)
    {
        return new LoginDetails(courier.getLogin(), courier.getPassword());
    }

    @Override
    public Principal getUserPrincipal() {return null;}

    public String getPassword()
    {return password;}

    public void setPassword(String password) {this.password = password;}
}
