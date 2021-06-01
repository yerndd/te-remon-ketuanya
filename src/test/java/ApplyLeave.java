import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

public class ApplyLeave {
    public static void main(String[] args) {
        //panggil test case
        //Test Punch In
//        Test1();
//        Test2();
//        Test3();
//        Test4();
//        Test5();
//        Test6();

        //Test Punch Out
//        Test1Out();
//        Test2Out();
//        Test3Out();
//        Test4Out();
//        Test5Out();
    }

    //fungsi login dan membuka halaman attendance - punch in/out
    public static void Login(WebDriver driver){
        WebElement element = driver.findElement(By.xpath("//*[@id = 'txtUsername']"));
        WebElement element2 = driver.findElement(By.xpath("//*[@id = 'txtPassword']"));
        element.clear();
        element2.clear();
        element.sendKeys("Admin");
        element2.sendKeys("admin123");
        element2.submit();
        WebElement element3 = driver.findElement(By.xpath("//*[@id = 'menu_leave_viewLeaveModule']"));
        element3.click();
        WebElement element4 = driver.findElement(By.xpath("//*[@id = 'menu_leave_applyLeave']"));
        element4.click();
    }

    //test leave apply
    //input time dan date sesuai dengan permintaan sistem
    public static void Test1(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", map);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://opensource-demo.orangehrmlive.com/");

        //login
        Login(driver);

        SoftAssert softAssert = new SoftAssert();
        Select leaveType = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_txtLeaveType']")));
        WebElement dateFrom = driver.findElement(By.xpath("//*[@id = 'applyleave_txtFromDate']"));
        WebElement dateTo = driver.findElement(By.xpath("//*[@id = 'applyleave_txtToDate']"));
        Select duration = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_duration_duration']")));
        WebElement comment = driver.findElement(By.xpath("//*[@id = 'applyleave_txtComment']"));
        WebElement btnApply = driver.findElement(By.xpath("//*[@id = 'applyBtn']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //data test
        String tipeCuti = "CAN - Bereavement";
        String tanggalDari = "2021-06-01";
        String tanggalKe = "2021-06-01";
        int durasi = 0;
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave";

        //input
        leaveType.selectByVisibleText(tipeCuti);
        dateFrom.clear();
        dateFrom.sendKeys(tanggalDari);
        dateTo.clear();
        dateTo.sendKeys(tanggalKe);
        duration.selectByIndex(durasi);
        comment.sendKeys("test case 1");

        System.out.println("klik in");
        btnApply.click();

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave"));
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test case 1 error");
        softAssert.assertAll();
        driver.quit();
    }

    //input date tidak sesuai format
    public static void Test2(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", map);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://opensource-demo.orangehrmlive.com/");

        //login
        Login(driver);

        SoftAssert softAssert = new SoftAssert();
        Select leaveType = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_txtLeaveType']")));
        WebElement dateFrom = driver.findElement(By.xpath("//*[@id = 'applyleave_txtFromDate']"));
        WebElement dateTo = driver.findElement(By.xpath("//*[@id = 'applyleave_txtToDate']"));
        Select duration = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_duration_duration']")));
        WebElement comment = driver.findElement(By.xpath("//*[@id = 'applyleave_txtComment']"));
        WebElement btnApply = driver.findElement(By.xpath("//*[@id = 'applyBtn']"));

        //data testing
        String tipeCuti = "CAN - Bereavement";
        int durasi = 0;
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave";
        CharSequence[] test = new String[13];
        test[0] = "";                  //coba String kosong
        test[1] = "a";                 //coba isi char
        test[2] = "aaaa-aa-aa";        //coba isi alfabet
        test[3] = "1212-aa-12";        //coba isi alpanumerik
        test[4] = "@@@@-@@-##";        //coba isi simbol
        test[5] = "22221212";          //coba angka tanpa pemisah
        test[6] = "9999-10-10";        //coba tahun 9999
        test[7] = "0000-10-10";        //coba tahun 0000
        test[8] = "2021-13-10";        //coba bulan 13
        test[9] = "2021-00-10";        //coba bulan 00
        test[10] = "2021-10-32";        //coba tanggal 32
        test[11] = "2021-10-00";        //coba tanggal 00

        //coba input date
        for (int i=0; i<(test.length-1); i++){
            dateFrom.clear();
            dateTo.clear();
            CharSequence temp = test[i];
            System.out.println("testing test case ke:"+(i+1)+", isi test case : "+temp);
            leaveType.selectByVisibleText(tipeCuti);
            dateFrom.sendKeys(temp);
            dateTo.sendKeys(temp);
            duration.selectByIndex(durasi);
            comment.sendKeys("test case 2-"+(i+1));
            btnApply.click();
            System.out.println(driver.getCurrentUrl());
            softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test ke-"+(i+1)+" gagal");
        }
        driver.quit();
        softAssert.assertAll();
    }

    //input note alphanumeric
    public static void Test4(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", map);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://opensource-demo.orangehrmlive.com/");

        //login
        Login(driver);

        SoftAssert softAssert = new SoftAssert();
        Select leaveType = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_txtLeaveType']")));
        WebElement dateFrom = driver.findElement(By.xpath("//*[@id = 'applyleave_txtFromDate']"));
        WebElement dateTo = driver.findElement(By.xpath("//*[@id = 'applyleave_txtToDate']"));
        Select duration = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_duration_duration']")));
        WebElement comment = driver.findElement(By.xpath("//*[@id = 'applyleave_txtComment']"));
        WebElement btnApply = driver.findElement(By.xpath("//*[@id = 'applyBtn']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //data testing
        String tipeCuti = "CAN - Bereavement";
        String tanggalDari = "2021-06-01";
        String tanggalKe = "2021-06-01";
        int durasi = 0;
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave";
        CharSequence test = "qwertyuiopasdfghjklzxccvbnm1234567890";

        //testing
        System.out.println("testing test case input note alphanumeric");
        leaveType.selectByVisibleText(tipeCuti);
        dateFrom.clear();
        dateFrom.sendKeys(tanggalDari);
        dateTo.clear();
        dateTo.sendKeys(tanggalKe);
        duration.selectByIndex(durasi);
        comment.sendKeys(test);

        btnApply.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input alphanumeric gagal");

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave"));
        driver.quit();
    }

    //input note query SQL
    public static void Test5(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", map);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://opensource-demo.orangehrmlive.com/");

        //login
        Login(driver);

        SoftAssert softAssert = new SoftAssert();
        Select leaveType = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_txtLeaveType']")));
        WebElement dateFrom = driver.findElement(By.xpath("//*[@id = 'applyleave_txtFromDate']"));
        WebElement dateTo = driver.findElement(By.xpath("//*[@id = 'applyleave_txtToDate']"));
        Select duration = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_duration_duration']")));
        WebElement comment = driver.findElement(By.xpath("//*[@id = 'applyleave_txtComment']"));
        WebElement btnApply = driver.findElement(By.xpath("//*[@id = 'applyBtn']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //data testing
        String tipeCuti = "CAN - Bereavement";
        String tanggalDari = "2021-06-01";
        String tanggalKe = "2021-06-01";
        int durasi = 0;
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave";
        CharSequence test = "* FROM user'";

        //testing
        System.out.println("testing test case input note query SQL");
        leaveType.selectByVisibleText(tipeCuti);
        dateFrom.clear();
        dateFrom.sendKeys(tanggalDari);
        dateTo.clear();
        dateTo.sendKeys(tanggalKe);
        duration.selectByIndex(durasi);
        comment.sendKeys(test);

        btnApply.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input query sql gagal");

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave"));
        driver.quit();
    }

    //input note lebih dari 250 karakter
    public static void Test6(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", map);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://opensource-demo.orangehrmlive.com/");

        //login
        Login(driver);

        SoftAssert softAssert = new SoftAssert();
        Select leaveType = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_txtLeaveType']")));
        WebElement dateFrom = driver.findElement(By.xpath("//*[@id = 'applyleave_txtFromDate']"));
        WebElement dateTo = driver.findElement(By.xpath("//*[@id = 'applyleave_txtToDate']"));
        Select duration = new Select(driver.findElement(By.xpath("//*[@id = 'applyleave_duration_duration']")));
        WebElement comment = driver.findElement(By.xpath("//*[@id = 'applyleave_txtComment']"));
        WebElement btnApply = driver.findElement(By.xpath("//*[@id = 'applyBtn']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //data testing
        String tipeCuti = "CAN - Bereavement";
        String tanggalDari = "2021-06-01";
        String tanggalKe = "2021-06-01";
        int durasi = 0;
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave";
        CharSequence test = "";
        for (int i=0; i<260; i++){
            test= test +"a";
        }

        //testing
        System.out.println("testing test case input note lebih dari 250 karakter");
        leaveType.selectByVisibleText(tipeCuti);
        dateFrom.clear();
        dateFrom.sendKeys(tanggalDari);
        dateTo.clear();
        dateTo.sendKeys(tanggalKe);
        duration.selectByIndex(durasi);
        comment.sendKeys(test);

        btnApply.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input lebih dari 250 karakter gagal");

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/leave/applyLeave"));
        driver.quit();
        softAssert.assertAll();
    }
}
