/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesearcher;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author nogaevpn
 */
public class SimpleSearcher {

    /**
     * @param args the command line arguments
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        
        Participant participant = new Participant("Иванов","Иван","Иванович","1111","111111");

        List<Source> sources = ConfigReader.getInstance().sources;
        
        List<ExamResult> examResults = new ArrayList<>();
        for (Source source : sources) {
            examResults.addAll(getResults(source, participant));
        }

        for (ExamResult examResult : examResults){
            System.out.println(examResult);
        }

        //testing sources
        for (Source source : sources) {
            System.out.println("Testing database " + source.DatabaseName + " result " + testSource(source));
        }
    }
    
    public static List<ExamResult> getResults(Source source, Participant participant) {
        // Declare the JDBC objects.
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ExamResult> examResults = new ArrayList<>();
        try {
            // Establish the connection. 
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setIntegratedSecurity(false);
            ds.setServerName(source.ServerName);
            ds.setPortNumber(source.PortNumber);
            ds.setDatabaseName(source.DatabaseName);
            ds.setUser(source.User);
            ds.setPassword(source.Password);
            con = ds.getConnection();
            
            String SQL = "SELECT "
                            + "res_HumanTests.ExamDate, "
                            + "rbdc_ProcessConditions.ProcessConditionName, "
                            + "dat_Subjects.SubjectName, "
                            + "res_Marks.Mark100 "
                        + "FROM "
                            + "rbd_Participants "
                        + "INNER JOIN res_HumanTests "
                            + "ON (res_HumanTests.ParticipantFK=rbd_Participants.ParticipantID) "
                        + "INNER JOIN res_Marks "
                            + "ON (res_Marks.HumanTestId=res_HumanTests.HumanTestId) "
                        + "INNER JOIN dat_Subjects "
                            + "ON (dat_Subjects.SubjectCode=res_HumanTests.SubjectCode) "
                        + "INNER JOIN rbdc_ProcessConditions "
                            + "ON(rbdc_ProcessConditions.ProcessConditionID=res_HumanTests.ProcessCondition) "
                        + "WHERE "
                            + "rbd_Participants.SurName = ? AND "
                            + "rbd_Participants.Name = ? AND "
                            + "rbd_Participants.SecondName = ? AND "
                            + "rbd_Participants.DocumentSeries = ? AND "
                            + "rbd_Participants.DocumentNumber = ?"; 
            
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, participant.SurName);
            pstmt.setString(2, participant.Name);
            pstmt.setString(3, participant.SecondName);
            pstmt.setString(4, participant.DocumentSeries);
            pstmt.setString(5, participant.DocumentNumber);

            rs = pstmt.executeQuery();

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                examResults.add(new ExamResult(rs.getString("ExamDate"),rs.getString("ProcessConditionName"),rs.getString("SubjectName"),rs.getString("Mark100")));
            }
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
        return examResults;
    }
    
    public static Boolean testSource(Source source){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        Boolean hasRows = false;

        try {
            // Establish the connection. 
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setIntegratedSecurity(false);
            ds.setServerName(source.ServerName);
            ds.setPortNumber(source.PortNumber);
            ds.setDatabaseName(source.DatabaseName);
            ds.setUser(source.User);
            ds.setPassword(source.Password);
            con = ds.getConnection();
            
            String SQL = "SELECT ProcessConditionName FROM rbdc_ProcessConditions"; 
            
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                hasRows = true;
            }
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
        
        return hasRows;
    }
}
