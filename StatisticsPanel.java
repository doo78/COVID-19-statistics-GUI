

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Displays statistics for the given date interval
 *
 * @Ricky Gordon k23075676
 * @15/03/2024
 */
public class StatisticsPanel
{
    //start and end dates
    private String startDate;
    private String endDate;
    
    //loads the data immediately
    private CovidDataLoader x = new CovidDataLoader();
    private ArrayList<CovidData> records = x.load();
    
    //Initialises (processes) the data 
    private int totalTotalDeaths = -1; //-1 detects that data is not loaded
    private int parks;
    private int workplaces;
    private int averageCases;
    
    //for the text display
    @FXML
    private Label statisticTitle;
    @FXML
    private Label statisticDisplay;
    


    /**
     * Processes all the statistics
     * The type parameter specifies which statistic is being processed
     * That parameter will affect the output and the processing accordingly
     * For the average total cases, type should be "total cases"
     * For the percentage change in visits to parks, type should be "parks"
     * For the percentage change in visits to workplaces, type should be "workplaces"
     * For the total amount of total deaths, type should be "totalTotalDeaths"
     */
    
    public int processStatistic(String type){
        
        //temporary variables for calculating the statistics
        int count = 0;
        int total = 0;
        
        //Adds to the total and increments the count if its in the correct date range
        for (CovidData data: records){
            System.out.println(startDate + endDate);
            if(data.inDateRange(startDate, endDate)){
                count++;
                System.out.println(" " + count);
                if (type.equals("total cases")){
                    total+= data.getTotalCases();
                }
                else if (type.equals("parks")){
                    total+= data.getParksGMR();
                }
                else if (type.equals("workplaces")){
                    total+= data.getWorkplacesGMR();
                }
                else if (type.equals("totalTotalDeaths")){
                    total+= data.getTotalDeaths();
                }
            }
        }
        
        int average = total/count;
        
        if (type.equals("totalTotalDeaths")){
            //Total deaths is a total instead of average
            return total;
        }
        else{
            return average;   
        }
    }
    
    /**
     * Set the start date.
     * 
     * @param date The formatted string date
     */
    public void setStartDate(String date)
    {
        startDate = date;
    }
    
    /**
     * Set the end date.
     * 
     * @param date The formatted string date
     */
    public void setEndDate(String date)
    {
        endDate = date;
    }
    
    //EVENTS
    
    @FXML
    /**
     * Changes the display when the left button is clicked
     * Depends on what was being previously displayed
     */
    public void leftClick(ActionEvent event){
        if(totalTotalDeaths == -1){ //detects if data has been loaded yet
            totalTotalDeaths = this.processStatistic("totalTotalDeaths");
            parks = this.processStatistic("parks");
            workplaces = this.processStatistic("workplaces");
            averageCases = this.processStatistic("total cases");
        }
        
        //Changes the display depending on what was previously displaying
        if (statisticTitle.getText().equals("Click arrows to show statistics")){
            //Changes the title and display labels' text
            statisticTitle.setText("Total number of (total) deaths:");
            statisticDisplay.setText(Integer.toString(totalTotalDeaths));
        }
        else if (statisticTitle.getText().equals("Total number of (total) deaths:")){
            statisticTitle.setText("Average of total cases:");
            statisticDisplay.setText(Integer.toString(averageCases)); 
        }
        else if (statisticTitle.getText().equals("Average of total cases:")){
            statisticTitle.setText("Percentage change in visits to parks:");
            statisticDisplay.setText(Integer.toString(parks));
        }
        else if (statisticTitle.getText().equals("Percentage change in visits to parks:")){
            statisticTitle.setText("Percentage change in visits to workplaces:");
            statisticDisplay.setText(Integer.toString(workplaces));
        }
        else if (statisticTitle.getText().equals("Percentage change in visits to workplaces:")){
            statisticTitle.setText("Total number of (total) deaths:");
            statisticDisplay.setText(Integer.toString(totalTotalDeaths));
        }
        
    }

    @FXML
    /**
     * Changes the display when the right button is clicked
     * Depends on what was being previously displayed
     */
    public void rightClick(ActionEvent event){
        if(totalTotalDeaths == -1){ //detects if data has been loaded yet
            totalTotalDeaths = this.processStatistic("totalTotalDeaths");
            parks = this.processStatistic("parks");
            workplaces = this.processStatistic("workplaces");
            averageCases = this.processStatistic("total cases");
        }
        //Changes the display depending on what was previously displaying
        if (statisticTitle.getText().equals("Click arrows to show statistics")){
            statisticTitle.setText("Percentage change in visits to parks:");
            statisticDisplay.setText(Integer.toString(parks)); 
        }
        else if (statisticTitle.getText().equals("Total number of (total) deaths:")){
            statisticTitle.setText("Percentage change in visits to workplaces:");
            statisticDisplay.setText(Integer.toString(workplaces)); 
        }
        else if (statisticTitle.getText().equals("Average of total cases:")){
            statisticTitle.setText("Total number of (total) deaths:");
            statisticDisplay.setText(Integer.toString(totalTotalDeaths));
        }
        else if (statisticTitle.getText().equals("Percentage change in visits to parks:")){
            statisticTitle.setText("Average of total cases:");
            statisticDisplay.setText(Integer.toString(averageCases));
        }
        else if (statisticTitle.getText().equals("Percentage change in visits to workplaces:")){
            statisticTitle.setText("Percentage change in visits to parks:");
            statisticDisplay.setText(Integer.toString(parks));
        }
    }
    
    public String getStartDate()
    {
        return startDate;
    }
    
    public String getEndDate()
    {
        return endDate;
    }
}
