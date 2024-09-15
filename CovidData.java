 

/**
 * Represents one record in the COVID dataset.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */ 

public class CovidData {

    /*
    The date the COVID information (cases & deaths) was collected
    */
    private String date;
    
    /*
    The COVID information is organised by (London) borough
    */
    private String borough;
    
    
    /*
    The COVID information that's collected daily for each London borough
    */
    private int newCases;
    private int totalCases;
    private int newDeaths;
    private int totalDeaths;
    
    
    /*
    Google analysed location data from Android smartphones to measure movement
    in London.  The data shows percent change from the baseline.  For example, 
    a negative value means there's less human traffic compared to the baseline.
    */
    private int retailRecreationGMR;
    private int groceryPharmacyGMR;
    private int parksGMR;
    private int transitGMR;
    private int workplacesGMR;
    private int residentialGMR;



    public CovidData(String date, String borough, int retailRecreationGMR, int groceryPharmacyGMR, 
                        int parksGMR, int transitGMR, int workplacesGMR, int residentialGMR, 
                        int newCases, int totalCases, int newDeaths, int totalDeaths) {

        this.date = date;
        this.borough = borough;
        this.retailRecreationGMR = retailRecreationGMR;
        this.groceryPharmacyGMR = groceryPharmacyGMR;
        this.parksGMR = parksGMR;
        this.transitGMR = transitGMR;
        this.workplacesGMR = workplacesGMR;
        this.residentialGMR = residentialGMR;
        this.newCases = newCases;
        this.totalCases = totalCases;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
    }


    public String getDate() {
        return date;
    }
    
    /**
     * Checks whether a data entry's date falls between two specified dates.
     * This is done due to the provided csv data not entirely being in order.
     *
     * @param  start Start Date
     * @param  end End Date
     */
    public boolean inDateRange(String start, String end){
        int checkDate = convertDate(date); //see below for method desc.
        if(checkDate >= convertDate(start) && checkDate <= convertDate(end)){
            return true;
        }
        return false;
    }
    
    /**
     * Converts a date string to an integer, allowing its size to be compared
     * to others. This is used mainly for the method above.
     *
     * @param  dateString Date to be converted
     */
    public int convertDate(String dateString){
        //date format: XXXX-OX-OX. The Os may or may not be present
        String intValue = dateString.substring(0, 4); //year characters
        //check if month is 1 or 2 characters
        if(String.valueOf(dateString.charAt(6)).equals("-")){ //1 char
            intValue = intValue + "0" + String.valueOf(dateString.charAt(5));
            //format is XXXX-X-OX (length of 8 or 9)
            //check if day is 1 or 2 characters
            if(dateString.length() == 8){ //1 char
                intValue = intValue + "0" + String.valueOf(dateString.charAt(7));
            }else{ //2 char
                intValue = intValue + dateString.substring(7, 9); //char 7+8
            }
        }else{ //month is 2 char
            intValue = intValue + dateString.substring(5, 7); //char 5+6
            //format is XXXX-XX-OX (length of 9 or 10)
            //check if day is 1 or 2 characters
            if(dateString.length() == 9){ //1 char
                intValue = intValue + "0" + String.valueOf(dateString.charAt(8));
            }else{ //2 char
                intValue = intValue + dateString.substring(8, 10); //char 8+9
            }
        }

        //convert final string to int and return
        return Integer.parseInt(intValue);
    }


    public String getBorough() {
        return borough;
    }


    public int getRetailRecreationGMR() {
        return retailRecreationGMR;
    }


    public int getGroceryPharmacyGMR() {
        return groceryPharmacyGMR;
    }


    public int getParksGMR() {
        return parksGMR;
    }


    public int getTransitGMR() {
        return transitGMR;
    }


    public int getWorkplacesGMR() {
        return workplacesGMR;
    }


    public int getResidentialGMR() {
        return residentialGMR;
    }


    public int getNewCases() {
        return newCases;
    }


    public int getTotalCases() {
        return totalCases;
    }


    public int getNewDeaths() {
        return newDeaths;
    }


    public int getTotalDeaths() {
        return totalDeaths;
    }

    @Override
    public String toString() {
        return "Covid Record {" + 
        " date='" + date +'\'' +
        ", borough='" + borough +'\'' +
        ", retailRecreationGMR=" + retailRecreationGMR + 
        ", groceryPharmacyGMR=" + groceryPharmacyGMR + 
        ", parksGMR=" + parksGMR + 
        ", transitGMR=" + transitGMR + 
        ", workplacesGMR=" + workplacesGMR + 
        ", residentialGMR=" + residentialGMR + 
        ", newCases=" + newCases + 
        ", totalCases=" + totalCases + 
        ", newDeaths=" + newDeaths + 
        ", totalDeaths=" + totalDeaths + 
        "}";
    }
}
