

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IRCTCTrainEnquiryAutomation extends baseUIclass {

    @Test
    public void IRCTCTrainEnquiry()
    {

        //Launch browser and open url
        LaunchBrowser("Chrome");
        openURL("https://www.irctc.co.in");
        waitForPageLoad();

        //accept alert
        clickLink("//button[@class='btn btn-primary']");
        waitForPageLoad();
        //	clickLink("//button[@class='btn btn-primary']");
        //Click on From
        List<WebElement> l=getElements("//span/input");
        sendData(l.get(0),"Hyd");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        clickLink("//span[@class='ng-star-inserted']");
        String str1=l.get(0).getAttribute("value");
        String source[]=str1.split(" -");



        //click on TO
        sendData(l.get(1),"Pune");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        clickLink("//li/span[contains(@class,'ng-star-inserted')]");
        String str2=l.get(1).getAttribute("value");
        String destination[]=str2.split(" -");



        //Select Date
        clickLink("//*[@id='jDate']/span/input");
        String date=selectDate();



        //select class
        List <WebElement> elements=getElements("//span[contains(@class,'ui-dropdown-trigger-icon')]");
        clickElement(elements.get(0));
        List <WebElement> elements1=getElements("//li[@class='ui-dropdown-item ui-corner-all']/span[contains(@class,'ng-star-inserted')]");
        clickElement(elements1.get(8));


        //select checkbox
        waitForPageLoad();
        clickLink("//*[@id='divMain']/div/app-main-page/div/div/div[1]/div[1]/div[1]/app-jp-input/div/form/div[4]/div/span[1]/label");
        waitForPageLoad();
        clickLink("//span[contains(@class,'ui-button-icon-left')]");


        //click on find train Button
        clickLink("//button[@type='submit']");
        waitForPageLoad();


        //Get Number of train
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String str=getElemetText("//*[@id='divMain']/div/app-train-list/div[4]/div/div[1]");
        String arr[]=str.split(" ");
        int numberOfAvailableTrain=Integer.parseInt(arr[0]);
        System.out.println("Num of train available : "+numberOfAvailableTrain);



        //List Of all Available Train
        System.out.println("List Of Available Train:-");
        for(int i=1;i<=numberOfAvailableTrain;i++)
        {

            System.out.println(getElemetText("//*[@id='divMain']/div/app-train-list/div[4]/div/div[5]/div["+i+"]/div[1]/app-train-avl-enq/div[1]/div[1]/div[1]/strong"));
        }


        //validation with search credential
        String expectedTitle=source[0]+" "+destination[0]+" | "+date;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element=getElement("//*[@id='divMain']/div/app-train-list/div[4]/div/div[1]/span/strong");
        wait.until(ExpectedConditions.visibilityOf(element));
        String actualTitle=element.getText();

        Assert.assertEquals(actualTitle, expectedTitle);


        //Take ScrrenShot

        takeScreenShot();


        //Close Browser
        driver.close();

    }


}
