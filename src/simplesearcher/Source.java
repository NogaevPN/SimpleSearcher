/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesearcher;

/**
 *
 * @author nogaevpn
 */
public class Source {

    public final String ServerName;
    public final int PortNumber;
    public final String DatabaseName;
    public final String User;
    public final String Password;

    public Source(String ServerName, int PortNumber, String DatabaseName, String User, String Password) {
        this.ServerName = ServerName;
        this.PortNumber = PortNumber;
        this.DatabaseName = DatabaseName;
        this.User = User;
        this.Password = Password;
    }

    @Override
    public String toString() {
        return "<" + ServerName + ", " + PortNumber + ", " + DatabaseName + ", " + User + ", " + Password + ">";
    }
}
