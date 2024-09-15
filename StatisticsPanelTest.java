

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class StatisticsPanelTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StatisticsPanelTest
{
    StatisticsPanel statisticsPanel;
    
    /**
     * Default constructor for test class StatisticsPanelTest
     */
    public StatisticsPanelTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        statisticsPanel = new StatisticsPanel();
    }

    @Test
    public void testProcessStatistic()
    {
        statisticsPanel.setStartDate("2022-10-13");
        statisticsPanel.setEndDate("2022-10-15");
        assertEquals(66020, statisticsPanel.processStatistic("totalTotalDeaths"));
        assertEquals(12, statisticsPanel.processStatistic("parks"));
        assertEquals(-26, statisticsPanel.processStatistic("workplaces"));
        assertEquals(96235, statisticsPanel.processStatistic("total cases"));
    }
    
    @Test
    public void testSetStartDate()
    {
        statisticsPanel.setStartDate("2022-10-18");
        assertEquals("2022-10-18", statisticsPanel.getStartDate());
    }
    
    @Test
    public void testSetEndDate()
    {
        statisticsPanel.setEndDate("2022-10-20");
        assertEquals("2022-10-20", statisticsPanel.getEndDate());
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
