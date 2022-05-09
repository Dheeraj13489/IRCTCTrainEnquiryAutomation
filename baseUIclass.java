
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
public class baseUIclass {

    public WebDriver driver;


    public void LaunchBrowser(String browserName) {



        if (browserName.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver",
                   System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
            ChromeOptions co=new ChromeOptions();
            co.setAcceptInsecureCerts(true);
            co.addArguments("--disable-infobars");
            co.addArguments("--disable-notifications");
            driver = new ChromeDriver(co);


        } else if (browserName.equalsIgnoreCase("Mozila")) {
            System.setProperty("webdriver.gecko.driver",
                    System.getProperty("user.dir")+"\\Driver\\geckodriver.exe");
            driver = new FirefoxDriver();

        }
		/*
		  else if (browserName.equalsIgnoreCase("Opera")) {
			System.setProperty("webdriver.opera.driver","");
			driver = new OperaDriver();
			reportPass("Initilizing the Brower");
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver","");
			driver = new OperaDriver();
			reportPass("Initilizing the Brower");
		} else {
			driver = new SafariDriver();
			reportPass("Initilizing the Brower");
			}
		}
			*/


        driver.manage().window().maximize();
        waitForPageLoad();
    }

    public void openURL(String websiteURL) {
        driver.get(websiteURL);

    }

    public void tearDown() {
        driver.close();

    }

    public void quitBrowser() {
        driver.quit();

    }
    public void clickElement(WebElement element)
    {

        element.click();



    }
    public String getElemetText(String xpath)
    {
        return driver.findElement(By.xpath(xpath)).getText();

    }


    public void clickLink(String xpath)
    {

        driver.findElement(By.xpath(xpath)).click();
    }
    public WebElement getElement(String xpath)
    {
        return driver.findElement(By.xpath(xpath));
    }

    public List<WebElement> getElements(String xpath)
    {
        return driver.findElements(By.xpath(xpath));
    }

    public void verifyAlertMessage(String actual,String expect)
    {

        Assert.assertEquals(actual, expect,"Alert message same as Expected");



    }
    public void clickLinkText(String linktext)
    {

        driver.findElement(By.linkText(linktext)).click();

    }

    public void sendData(WebElement element,String data)
    {
        element.sendKeys(data);
    }




    //Wait for pageload
    public void waitForPageLoad(){
        JavascriptExecutor js = (JavascriptExecutor)driver;

        int i =0;
        while( i !=20){
            String pageState = (String)js.executeScript("return document.readyState;");
            System.out.println(pageState);

            if(pageState.equals("complete")){
                break;
            }else{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    //select date
    public String selectDateIncalendar(String date) {

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate =null;
        try {
            expectedDate = dateFormat.parse(date);

            String day = new SimpleDateFormat("dd").format(expectedDate);
            String month = new SimpleDateFormat("MMMM").format(expectedDate);
            String year = new SimpleDateFormat("yyyy").format(expectedDate);

            String expectedMonthYear = month + "" + year;

            while (true) {
                String displayDate =driver.findElement(By.xpath("//*[@id='jDate']/span/div/div/div[1]/div")).getText();

                if (expectedMonthYear.equalsIgnoreCase(displayDate)) {
                    StringBuffer day1=new StringBuffer(day);
                    if(day1.charAt(0)=='0')
                    {
                        day1.deleteCharAt(0);
                        driver.findElement(By.xpath("//*[@id='jDate']//a[text()= '" + day1 + "']")).click();
                    }
                    else
                    {
                        driver.findElement(By.xpath("//*[@id='jDate']//a[text()= '" + day + "']")).click();
                    }
                    break;
                } else if (expectedDate.compareTo(currentDate) > 0) {
                    driver.findElement(By.xpath("//*[@id='jDate']/span/div/div/div[1]/a[2]/span")).click();
                } else {
                    driver.findElement(By.xpath("//*[@id='jDate']/span/div/div/div[1]/a[1]/span")).click();
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
        String stringDate = dateFormat.format(expectedDate);
        return stringDate;

    }

    public String selectDate()
    {

        String result=null;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd MM yyyy");
        String stringDate= DateFor.format(date);
        String datearr[]=stringDate.split(" ");

        int day=Integer.parseInt(datearr[0]);
        int month=Integer.parseInt(datearr[1]);
        if(month==1||month==3||month==5||month==8||month==7||month==10||month==12)
        {
            day=day+4;
            if(day<=31){
                String d=(day)+"/"+month+"/"+datearr[2];
                result=selectDateIncalendar(d);

            }
            else
            {
                day=day-31;
                String d=(day)+"/"+(month+1)+"/"+datearr[2];
                result=	selectDateIncalendar(d);

            }
        }
        else if(month==2)

        {
            day=day+4;
            if(day<=28){
                String d=(day)+"/"+month+"/"+datearr[2];
                result=selectDateIncalendar(d);

            }
            else
            {
                day=day-28;
                String d=(day)+"/"+(month+1)+"/"+datearr[2];
                result=selectDateIncalendar(d);
            }


        }
        else
        {
            day=day+4;
            if(day<=30){
                String d=(day)+"/"+month+"/"+datearr[2];
                result=selectDateIncalendar(d);
            }
            else
            {
                day=day-30;
                String d=(day)+"/"+(month+1)+"/"+datearr[2];
                result=	selectDateIncalendar(d);

            }


        }
        return result;
    }

    public void takeScreenShot(){


        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(System.getProperty(("user.dir"))+"\\Screenshot_"+GetDate.date()+".jpg");
        try {
            FileHandler.copy(SrcFile, DestFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}

