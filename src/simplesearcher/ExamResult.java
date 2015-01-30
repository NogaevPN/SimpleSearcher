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
public class ExamResult {
    public final String ExamDate;
    public final String ProcessCondition;
    public final String Subject;
    public final String Mark100;
    
    public ExamResult(String examDate,String processCondition,String subject,String mark100){
        this.ExamDate = examDate;
        this.ProcessCondition = processCondition;
        this.Subject = subject;
        this.Mark100 = mark100;
    }
    
    @Override
    public String toString() {
        return "<" + ExamDate + ", " + ProcessCondition + ", " + Subject + ", " + Mark100  + ">";
    }
}
