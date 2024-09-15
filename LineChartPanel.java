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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import java.util.ArrayList;
import javafx.scene.control.ComboBox;

import javafx.fxml.Initializable;
import java.util.ResourceBundle;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Write a description of JavaFX class MainWindow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LineChartPanel implements Initializable
{
    //temporary dates because the dates will come from the main panel, once that is implemented
    private String startDate;
    private String endDate;    
    
    private CovidDataLoader x = new CovidDataLoader();
    private ArrayList<CovidData> records = x.load(); //loads the records from the csv file
    
    private String borough1 = "Kingston Upon Thames"; //initial values for the boroughs and category
    private String borough2 = "Kingston Upon Thames";
    private String category = "new_cases";
    
    @FXML
    private Button startButton; //displays the data on the graph
    
    @FXML
    private ComboBox <String> choice1; //for the first borough to be chosen
 
    @FXML
    private ComboBox <String> choice2;
    
    @FXML
    private ComboBox <String> categoryBox = null; //to select a category of data
    
    private String[] boroughs = {"Kingston Upon Thames", "Hammersmith And Fulham", "Hackney", "Barking And Dagenham",
        "Tower Hamlets", "Lewisham", "Islington", "Bexley", "Wandsworth", "Haringey", "Enfield", "Sutton", "Merton",
        "Havering", "Brent", "Hounslow", "Camden", "Greenwich", "Harrow", "Ealing", "Southwark", "Newham",
        "Kensington and Chelsea", "City of London", "Bromley", "Hillingdon", "Redbridge", "Croydon",
        "Richmond", "Barnet", "Westminister", "Waltham Forest", "Lambeth"};
    
    private String[] categories = {"retail_and_recreation", "grocery_and_pharmacy", "parks", "transit_stations",
        "workplaces", "residential", "new_cases", "total_cases", "new_deaths", "total_deaths"};
    
    @FXML
    private LineChart<?, ?> lineGraph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Populates the combo boxes
        choice1.getItems().addAll(boroughs);
        choice2.getItems().addAll(boroughs);
        categoryBox.getItems().addAll(categories);
        
        //Sets the default values
        choice1.setValue("Kingston Upon Thames");
        choice2.setValue("Kingston Upon Thames");
        categoryBox.setValue("new_cases");
        
        //Labels the axes
        xAxis.setLabel("Days since start date");
        yAxis.setLabel("new_cases");
    }
    
    public long daysSince(LocalDate startDate, LocalDate endDate){
        //calculates the days in between the dates
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    @FXML
    public void startDisplay(ActionEvent event){
        //Clears the graph's data for the new data to be loaded
        lineGraph.getData().clear();
       
        //Changes the name of the y axis to the selected category
        yAxis.setLabel(category);
        
        //A series refers to a line in the graph
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(borough1);
        
        //Theres a line for each borough
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(borough2);
        
        //Loads the data and applies it to the graph
        loadValues(series1, borough1);
        loadValues(series2, borough2);
        lineGraph.getData().addAll(series1, series2);
        
        //Sets a title custom to the values selected
        lineGraph.setTitle(category + " for: " + borough1 + " and " + borough2);
            
        //Hides all the dots on the graph so its clearer
        lineGraph.setCreateSymbols(false); 
        
    }
 
    public void loadValues(XYChart.Series series, String borough){
        //Adds the relevant data to the series
        
        for (CovidData data: records){
            //Selects the data corresponding to the selected borough and within the date range
            if (data.inDateRange(startDate, endDate) && data.getBorough().equals(borough)){
                //Uses the corresponding getter method to the category selected (at the end of series.getData()...)
                if (category.equals("new_cases")){
                    //LocalDate.parse() is used to change the string date into a date format...
                    //...so the method daysSince can process it
                    series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getNewCases()));                    
                }
                else if (category.equals("retail_and_recreation")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getRetailRecreationGMR()));                       
                }
                else if (category.equals("grocery_and_pharmacy")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getGroceryPharmacyGMR()));       
                }
                else if (category.equals("parks")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getParksGMR()));       
                }
                else if (category.equals("transit_stations")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getTransitGMR()));       
                }
                else if (category.equals("workplaces")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getWorkplacesGMR()));       
                }
                else if (category.equals("residential")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getResidentialGMR()));       
                }
                else if (category.equals("total_cases")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getTotalCases()));       
                }
                else if (category.equals("new_deaths")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getNewDeaths()));       
                }
                else if (category.equals("total_deaths")){
                  series.getData().add(new XYChart.Data(daysSince(LocalDate.parse(startDate),LocalDate.parse(data.getDate()))
                    , data.getTotalDeaths()));       
                }
            }
        }
    }
    
    /**
     * Parsing the date only works in the format YYYY-MM-DD, so this method
     * converts the date if its YYYY-M-DD or YYYY-MM-D.
     * 
     * @param date The date string to modify
     */
    private String makeParsable(String date)
    {
        String newDate;
        if(String.valueOf(date.charAt(6)).equals("-")){ //month is 1 char
            newDate = date.substring(0, 5); //YYYY-
            newDate += "0" + date.substring(5, 7); //YYYY-0M-
    
            //check day with format YYYY-M-0D
            if(date.length() == 8){ //day is 1 char
                newDate += "0" + String.valueOf(date.charAt(7)); //YYYY-0M-0D
            }else{ //day is 2 chars
                newDate +=  date.substring(7, 9); //YYYY-0M-DD
            }
        }else{ //month is 2 chars
            newDate = date.substring(0, 8); //YYYY-MM-
            
            //check day with format YYYY-MM-0D
            if(date.length() == 9){ //day is 1 char
                newDate += "0" + String.valueOf(date.charAt(8)); //YYYY-MM-0D
            }else{ //day is 2 chars
                newDate +=  date.substring(8, 10); //YYYY-MM-DD
            }
        }
        return newDate;
    }
    
    /**
     * Set the start date.
     * 
     * @param date The formatted string date
     */
    public void setStartDate(String date)
    {
        startDate = makeParsable(date);
        xAxis.setLabel("Days since start date (" + startDate + ")");
    }
    
    /**
     * Set the end date.
     * 
     * @param date The formatted string date
     */
    public void setEndDate(String date)
    {
        endDate = makeParsable(date);
    }

    @FXML
    public void choice1Box(ActionEvent event){
        //Sets borough1 to the option the user selects in the combo box
        borough1 = choice1.getValue();
    }
    
    @FXML
    public void choice2Box(ActionEvent event){
        borough2 = choice2.getValue();
    }
    
    @FXML
    public void categoryChoice(ActionEvent event){
        //Sets category to the option the user selects in the combo box
        category = categoryBox.getValue();
    }
}
