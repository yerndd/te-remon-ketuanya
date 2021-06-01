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
        WebElement element3 = driver.findElement(By.xpath("//*[@id = 'menu_time_viewTimeModule']"));
        element3.click();
        WebElement element4 = driver.findElement(By.xpath("//*[@id = 'menu_attendance_Attendance']"));
        element4.click();
        WebElement element5 = driver.findElement(By.xpath("//*[@id = 'menu_attendance_punchIn']"));
        element5.click();
    }

    public static void Punch(WebDriver driver){
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        String tanggal = "2021-06-10";
        String jam = "12:00";

        date.clear();
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);

        date.clear();
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);
        note.sendKeys("");
        btnPunch.click();
    }

    //test punch in
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
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //data test
        String tanggal = "2021-06-01";
        String jam = "12:00";
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";

        //input
        date.clear();
        date.sendKeys(tanggal);
        time.clear();
        time.sendKeys(jam);
        note.sendKeys("test case 1");

        System.out.println("klik in");
        btnPunch.click();

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test case 1 error");
        softAssert.assertAll();

        //punchOut
        Punch(driver);

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn"));
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
        for (int i=0; i<(test.length-1); i++){
            date.clear();
            CharSequence temp = test[i];
            System.out.println("testing test case ke:"+(i+1)+", isi test case : "+temp);
            date.sendKeys(temp);
            btnPunch.click();
            System.out.println(driver.getCurrentUrl());
            softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test ke-"+(i+1)+" gagal");
        }
        btnPunch.click();
        if (driver.getCurrentUrl().equals(urlTujuan)){
            Punch(driver);
        }
        driver.quit();
        softAssert.assertAll();
    }

    //input time tidak sesuai format
    public static void Test3(){
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
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence[] test = new String[11];
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
        for (int i=0; i<(test.length-1); i++){
            time.clear();
            CharSequence temp = test[i];
            System.out.println("testing test case ke:"+(i+1)+", isi test case : "+temp);
            time.sendKeys(temp);
            btnPunch.click();
            System.out.println(driver.getCurrentUrl());
            softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test ke-"+(i+1)+" gagal");
        }
        btnPunch.click();
        if (driver.getCurrentUrl().equals(urlTujuan)){
            Punch(driver);
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
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence test = "qwertyuiopasdfghjklzxccvbnm1234567890";

        //testing
        System.out.println("testing test case input note alphanumeric");
        date.clear();
        date.sendKeys("2021-06-02");
        note.clear();
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input alphanumeric gagal");

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        //punchOut
        Punch(driver);

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn"));
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
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut";
        CharSequence test = "* FROM user'";

        //testing
        System.out.println("testing test case input note query SQL");
        date.clear();
        date.sendKeys("2021-06-03");
        note.clear();
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input query sql gagal");

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        //punchOut
        Punch(driver);

        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn"));
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
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
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
        date.clear();
        date.sendKeys("2021-06-04");
        note.clear();
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input lebih dari 250 karakter gagal");

        if (driver.getCurrentUrl().equals(urlTujuan)){
            Punch(driver);
        }
        driver.quit();
        softAssert.assertAll();
    }

    //Jika berhasil
    public static void Test1Out(){
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
        //PunchIn
        Punch(driver);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        String tanggal = "2021-06-11";
        String jam = "12:01";
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";

        date.clear();
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);

        date.clear();
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);
        note.sendKeys("test case punch out 1");

        btnPunch.click();
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn"));
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test case punch out 1 error");
        softAssert.assertAll();
        driver.quit();
    }

    //Test waktu dan tanggal kurang dari Punch In
    public static void Test2Out(){
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
        //PunchIn
        Punch(driver);

        WebDriverWait wait = new WebDriverWait(driver, 10);
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
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);

        date.clear();
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);

        btnPunch.click();
        System.out.println(driver.getCurrentUrl());
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test case punch out 2 gagal");

        //insert data valid supaya ga stuck di PunchOut
        tanggal = "2021-06-12";
        date.clear();
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);

        date.clear();
        time.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);
        note.sendKeys("test case punch out 2");

        btnPunch.click();
        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn"));
        driver.quit();
        softAssert.assertAll();
    }

    //Test tanggal kurang dari Punch In
//    public static void Test3Out(){
//        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("profile.default_content_setting_values.notifications", 2);
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("prefs", map);
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.get("https://opensource-demo.orangehrmlive.com/");
//
//        //login
//        Login(driver);
//        //PunchIn
//        Punch(driver);
//
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.urlToBe("https://opensource-demo.orangehrmlive.com/index.php/attendance/punchOut"));
//        SoftAssert softAssert = new SoftAssert();
//        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
//        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
//        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
//        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
//        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
//        String tanggal = "2021-06-01";
//        String jam = "12:00";
//        date.clear();
//        date.sendKeys(tanggal);
//        time.clear();
//        time.sendKeys(jam);
//        note.sendKeys("test case punch out 2");
//
//        btnPunch.click();
//        System.out.println(driver.getCurrentUrl());
//        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test case punch out 3 gagal");
//        softAssert.assertAll();
//        btnPunch.click();
//        driver.quit();
//    }

    //input date tidak sesuai format
    public static void Test3Out(){
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
        //PunchIn
        Punch(driver);

        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
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
        }

        btnPunch.click();
        if (driver.getCurrentUrl().equals(urlTujuan) == false){
            //input data valid agar tidak stuck di PunchOut
            System.out.println("input");
            String tanggal = "2021-06-13";

            date.clear();
            date.sendKeys(tanggal);

            date.clear();
            date.sendKeys(tanggal);
            note.sendKeys("test out 3");
            btnPunch.click();
        }
        driver.quit();
        softAssert.assertAll();
    }

    //input time tidak sesuai format
    public static void Test4Out(){
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
        //PunchIn
        Punch(driver);

        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
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
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        btnPunch.click();
        if (driver.getCurrentUrl().equals(urlTujuan) == false){
            //input data valid agar tidak stuck di PunchOut
            System.out.println("input");
            String tanggal = "2021-06-14";
            String jam = "12:10";

            date.clear();
            time.clear();
            date.sendKeys(tanggal);
            time.sendKeys(jam);
            note.sendKeys("test out 4");
            btnPunch.click();
        }
        driver.quit();
        softAssert.assertAll();
    }

    //input note lebih dari 250 karakter
    public static void Test5Out(){
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
        //PunchIn
        Punch(driver);

        SoftAssert softAssert = new SoftAssert();
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));

        //data testing
        String tanggal = "2021-06-15";
        String jam = "12:00";
        String urlTujuan = "https://opensource-demo.orangehrmlive.com/index.php/attendance/punchIn";
        CharSequence test = "";
        for (int i=0; i<260; i++){
            test= test +"a";
        }

        //testing
        System.out.println("testing test case input note lebih dari 250 karakter");
        date.clear();
        time.clear();
        note.clear();
        date.sendKeys(tanggal);
        time.sendKeys(jam);
        note.sendKeys(test);

        btnPunch.click();
        softAssert.assertEquals(driver.getCurrentUrl(), urlTujuan, "test input lebih dari 250 karakter gagal");

        if (driver.getCurrentUrl().equals(urlTujuan) == false){
            //input data valid agar tidak stuck di PunchOut
            System.out.println("input");

            note.clear();
            note.sendKeys("test out 4");
            btnPunch.click();
        }
        driver.quit();
        softAssert.assertAll();
    }
}
