import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
        driver.quit();
    }

    public static void testPunchInOut(WebDriver driver){
        WebElement date = driver.findElement(By.xpath("//*[@id = 'attendance_date']"));
        WebElement time = driver.findElement(By.xpath("//*[@id = 'attendance_time']"));
        WebElement note = driver.findElement(By.xpath("//*[@id = 'attendance_note']"));
        WebElement btnPunch = driver.findElement(By.xpath("//*[@id = 'btnPunch']"));
    }
}
