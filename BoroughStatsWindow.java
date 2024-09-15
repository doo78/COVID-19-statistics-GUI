import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
/**
 * Mini pop-up window that displays statistics specific to a selected borough.
 * Is opened from a button on the Map Panel
 *
 * @Danny Tierney k23081869
 * @27/03/2024
 */
public class BoroughStatsWindow
{
    //GUI elements:
    private JFrame frame;
    private JPanel panel;
    private JLabel boroughName;
    private JLabel dateRangeLabel;
    private JTextArea statisticsArea;
    
    //variables for each piece of data
    private int retailRecreation;
    private int groceryPharmacy;
    private int parks;
    private int transit;
    private int workplaces;
    private int residential;
    private int newCases;
    private int totalCases;
    private int newDeaths;
    
    /**
     * Constructor for objects of class BoroughStatsWindow
     */
    public BoroughStatsWindow(ArrayList<CovidData> data, ArrayList<Integer> dateIndexes, 
                              String borough, String startDate, String endDate)
    {
        //generate GUI
        frame = new JFrame(borough);
        
        //spacing
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(6, 6, 6, 6));
        panel.setLayout(new BorderLayout());
        
        //process data prior to displaying it
        processTotalData(data, dateIndexes, borough);
        
        //set each label to display its data
        boroughName = new JLabel("Stats for " + borough + ":");
        boroughName.setHorizontalAlignment(JLabel.CENTER);
        panel.add(boroughName, BorderLayout.NORTH);
        
        dateRangeLabel = new JLabel(startDate + " - " + endDate);
        dateRangeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(dateRangeLabel, BorderLayout.CENTER);
        
        //long text field for the area
        statisticsArea = new JTextArea("- Google Mobility Data -" +
        "\nRetail and Recreation: " + String.valueOf(retailRecreation) +
        "\nGrocery and Pharmacy: " + String.valueOf(groceryPharmacy) +
        "\nPark Vists: " + String.valueOf(parks) +
        "\nTransit Stations: " + String.valueOf(transit) +
        "\nWorkplaces: " + String.valueOf(workplaces) +
        "\nResidential: " + String.valueOf(residential) +
        "\n\n- Infection Data -\nNew Cases: " + String.valueOf(newCases) +
        "\nTotal Cases (end of period): " + String.valueOf(totalCases) +
        "\nNew Deaths: " + String.valueOf(newDeaths));
        statisticsArea.setEditable(false);
        panel.add(statisticsArea, BorderLayout.SOUTH);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        
        //Put the window in the centre of the screen
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        
        frame.setSize(250, 275);
        frame.setVisible(true);
    }
    
    /**
     * Go through covid data, totalling all values and storing them in
     * different elements of an array.
     * 
     * Parameters are provided from MapPanel.
     */
    public void processTotalData(ArrayList<CovidData> data, ArrayList<Integer> dateIndexes, String borough)
    {
        String checkBorough;
        for(int index : dateIndexes){
            checkBorough = data.get(index).getBorough();
            if(checkBorough.equals(borough)){ //right borough name
                //add each piece of data to the respective variable
                retailRecreation += data.get(index).getRetailRecreationGMR();
                groceryPharmacy += data.get(index).getGroceryPharmacyGMR();
                parks += data.get(index).getParksGMR();
                transit += data.get(index).getTransitGMR();
                workplaces += data.get(index).getWorkplacesGMR();
                residential += data.get(index).getResidentialGMR();
                newCases += data.get(index).getNewCases();
                //total cases data is cumulative rather than daily
                if(totalCases < data.get(index).getTotalCases()){
                    totalCases = data.get(index).getTotalCases();
                } //sets it to highest value within date range
                newDeaths += data.get(index).getNewDeaths();
            }
        }
    }
}
