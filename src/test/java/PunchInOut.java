import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

public class PunchInOut {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", map);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://opensource-demo.orangehrmlive.com/");

        //Login
        Login(driver);

        //panggil test case
//        Test1(driver);
//        Test2(driver);
//        Test3(driver);
//        Test4(driver);
//        Test5(driver);
//        Test6(driver);

        //Test Punch Out
        PunchIn(driver);
//        Test1Out(driver);
//        Test2Out(driver);
//        Test3Out(driver);
//        Test4Out(driver);
//        Test5Out(driver);
        Test6Out(driver);
    }

//    public static void testPunchInOut(WebDriver driver){
//        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
//        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
//        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
//        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
//    }

    //fungsi login dan membuka halaman attendance - punch in/out
    public static void Login(WebDriver driver){
        WebElement element = driver.findElement(By.xpath("//*[@id = 'txtUsername']"));
        WebElement element2 = driver.findElement(By.xpath("//*[@id = 'txtPassword']"));
        element.clear();
        element2.clear();
        element.sendKeys("Admin");
        element2.sendKeys("admin123");
        element2.submit();
        WebElement element3 = driver.findElement(By.xpath("//*[@id = 'menu_time_viewTimeModule']"));
        element3.click();
        WebElement element4 = driver.findElement(By.xpath("//*[@id = 'menu_attendance_Attendance']"));
        element4.click();
        WebElement element5 = driver.findElement(By.xpath("//*[@id = 'menu_attendance_punchIn']"));
        element5.click();
    }

    //test punch in
    //input time dan date sesuai dengan permintaan sistem
    public static void Test1(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        String tanggal = "2021-05-01";
        String jam = "12:00";

        date.clear();
        date.sendKeys(tanggal);

        time.clear();
        time.sendKeys(jam);

        note.sendKeys("test case 1");

        btnPunch.click();
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        softAssert.assertEquals(btnPunch.getAttribute("value"), "Out");
        softAssert.assertAll();

        btnPunch.click();
        driver.quit();
    }

    //input date tidak sesuai format
    public static void Test2(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
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
        for (int i=0; i<test.length; i++){
            date.clear();
            CharSequence temp = test[i];
            System.out.println("testing test case ke:"+(i+1)+", isi test case : "+temp);
            date.sendKeys(temp);
            btnPunch.click();
            System.out.println(driver.getCurrentUrl());
            softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test ke-"+(i+1)+" gagal");
        }
        softAssert.assertAll();
        btnPunch.click();
        driver.quit();
    }

    //input time tidak sesuai format
    public static void Test3(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence[] test = new String[13];
        test[0] = "";                  //coba String kosong
        test[1] = "a";                 //coba isi char
        test[2] = "aa:bb";        //coba isi alfabet
        test[3] = "a1:b2";        //coba isi alpanumerik
        test[4] = "@@:$$";        //coba isi simbol
        test[5] = "1212";          //coba angka tanpa pemisah
        test[6] = "25:00";        //coba jam 25
        test[7] = "-10:00";        //coba jam minus
        test[8] = "12:61";        //coba menit 61
        test[9] = "12:-10";        //coba menit minus

        //coba input time
        for (int i=0; i<test.length; i++){
            time.clear();
            CharSequence temp = test[i];
            System.out.println("testing test case ke:"+(i+1)+", isi test case : "+temp);
            time.sendKeys(temp);
            btnPunch.click();
            System.out.println(driver.getCurrentUrl());
            softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test ke-"+(i+1)+" gagal");
        }
        softAssert.assertAll();
        btnPunch.click();
        driver.quit();
    }

    //input note alphanumeric
    public static void Test4(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence test = "qwertyuiopasdfghjklzxccvbnm1234567890";

        //testing
        System.out.println("testing test case input note alphanumeric");
        note.clear();
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input alphanumeric gagal");

        softAssert.assertAll();
        driver.quit();
    }

    //input note query SQL
    public static void Test5(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence test = "* FROM user'";

        //testing
        System.out.println("testing test case input note query SQL");
        note.clear();
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input query sql gagal");

        softAssert.assertAll();
        driver.quit();
    }

    //input note lebih dari 250 karakter
    public static void Test6(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence test = "";
        for (int i=0; i<260; i++){
            test= test +"a";
        }

        //testing
        System.out.println("testing test case input note lebih dari 250 karakter");
        note.clear();
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input lebih dari 250 karakter gagal");

        softAssert.assertAll();
        driver.quit();
    }

    public static void PunchIn(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
        String tanggal = "2021-06-02";
        String jam = "12:00";
        date.clear();
        date.sendKeys(tanggal);
        time.clear();
        time.sendKeys(jam);
        note.sendKeys("");
        btnPunch.click();
    }

    //Jika berhasil
    public static void Test1Out(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        String tanggal = "2021-06-02";
        String jam = "12:01";
        date.clear();
        date.sendKeys(tanggal);
        time.clear();
        time.sendKeys(jam);
        note.sendKeys("test case punch out 1");

        btnPunch.click();
        wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn"));
        softAssert.assertEquals(btnPunch.getAttribute("value"), "In");
        softAssert.assertAll();
        btnPunch.click();
        driver.quit();
    }

    //Test waktu kurang dari Punch In
    public static void Test2Out(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
        String tanggal = "2021-06-02";
        String jam = "11:59";
        date.clear();
        date.sendKeys(tanggal);
        time.clear();
        time.sendKeys(jam);
        note.sendKeys("test case punch out 2");

        btnPunch.click();
        System.out.println(driver.getCurrentUrl());
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test case punch out 2 gagal");
        softAssert.assertAll();
        btnPunch.click();
        driver.quit();
    }

    //Test tanggal kurang dari Punch In
    public static void Test3Out(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
        String tanggal = "2021-06-01";
        String jam = "12:00";
        date.clear();
        date.sendKeys(tanggal);
        time.clear();
        time.sendKeys(jam);
        note.sendKeys("test case punch out 2");

        btnPunch.click();
        System.out.println(driver.getCurrentUrl());
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test case punch out 3 gagal");
        softAssert.assertAll();
        btnPunch.click();
        driver.quit();
    }

    //input date tidak sesuai format
    public static void Test4Out(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence[] test = new String[12];
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
        for (int i=0; i<test.length; i++){
            date.clear();
            CharSequence temp = test[i];
            System.out.println("testing test case ke:"+(i+1)+", isi test case : "+temp);
            date.sendKeys(temp);
            btnPunch.click();
            System.out.println(driver.getCurrentUrl());
            softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test ke-"+(i+1)+" gagal");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        softAssert.assertAll();
        btnPunch.click();
        driver.quit();
    }

    //input time tidak sesuai format
    public static void Test5Out(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence[] test = new String[10];
        test[0] = "";                  //coba String kosong
        test[1] = "a";                 //coba isi char
        test[2] = "aa:bb";        //coba isi alfabet
        test[3] = "a1:b2";        //coba isi alpanumerik
        test[4] = "@@:$$";        //coba isi simbol
        test[5] = "1212";          //coba angka tanpa pemisah
        test[6] = "25:00";        //coba jam 25
        test[7] = "-10:00";        //coba jam minus
        test[8] = "12:61";        //coba menit 61
        test[9] = "12:-10";        //coba menit minus

        //coba input time
        for (int i=0; i<test.length; i++){
            time.clear();
            CharSequence temp = test[i];
            System.out.println("testing test case ke:"+(i+1)+", isi test case : "+temp);
            time.sendKeys(temp);
            btnPunch.click();
            System.out.println(driver.getCurrentUrl());
            softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test ke-"+(i+1)+" gagal");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        softAssert.assertAll();
        btnPunch.click();
        driver.quit();
    }

    //input note lebih dari 250 karakter
    public static void Test6Out(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence test = "";
        for (int i=0; i<260; i++){
            test= test +"a";
        }

        //testing
        System.out.println("testing test case input note lebih dari 250 karakter");
        note.clear();
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input lebih dari 250 karakter gagal");

        softAssert.assertAll();
        driver.quit();
    }
}
