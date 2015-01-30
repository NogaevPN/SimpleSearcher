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
public class Participant {
    public final String SurName;
    public final String Name;
    public final String SecondName;
    public final String DocumentSeries;
    public final String DocumentNumber;
    
    public Participant(String surName, String name, String secondName, String documentSeries, String documentNumber) {
        this.SurName = surName;
        this.Name = name;
        this.SecondName = secondName;
        this.DocumentSeries = documentSeries;
        this.DocumentNumber = documentNumber;
    }
    
    @Override
    public String toString() {
        return "<" + SurName + ", " + Name + ", " + SecondName + ", " + DocumentSeries + ", " + DocumentNumber + ">";
    }
}
