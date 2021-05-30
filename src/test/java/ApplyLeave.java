import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ApplyLeave {
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
        driver.quit();
    }

    public static void testApplyLeave(WebDriver driver){

    }
}
