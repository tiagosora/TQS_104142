import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTest {
    private WebDriver driver;
    // private WebDriverWait driverWait;
    JavascriptExecutor js;

    @Test
    @Disabled
    public void test() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;

        // Test name: test
        // Step # | name | target | value
        // 1 | open | http://localhost:3000/ | 
        driver.get("http://localhost:3000/");
        // 2 | setWindowSize | 1920x1001 | 
        driver.manage().window().setSize(new Dimension(1920, 1001));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // 3 | mouseDown | css=.Mui-focused > .MuiSelect-select | 
        {
        WebElement element = driver.findElement(By.cssSelector(".Mui-focused > .MuiSelect-select"));
        Actions builder = new Actions(driver);
        builder.moveToElement(element).clickAndHold().perform();
        }
        // 4 | mouseUp | css=.MuiButtonBase-root:nth-child(10) | 
        {
        WebElement element = driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(10)"));
        Actions builder = new Actions(driver);
        builder.moveToElement(element).release().perform();
        }
        // 5 | click | css=body | 
        driver.findElement(By.cssSelector("body")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // 6 | click | css=.MuiButtonBase-root:nth-child(61) | 
        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(61)")).click();
        // 7 | mouseDown | css=.Mui-focused > .MuiSelect-select |
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); 
        {
        WebElement element = driver.findElement(By.cssSelector(".Mui-focused > .MuiSelect-select"));
        Actions builder = new Actions(driver);
        builder.moveToElement(element).clickAndHold().perform();
        }
        // 8 | mouseUp | css=.MuiButtonBase-root:nth-child(9) | 
        {
        WebElement element = driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(9)"));
        Actions builder = new Actions(driver);
        builder.moveToElement(element).release().perform();
        }
        // 9 | click | css=body | 
        driver.findElement(By.cssSelector("body")).click();
        // 10 | click | css=.MuiButtonBase-root:nth-child(2) | 
        driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(2)")).click();
        // 11 | click | css=.MuiButtonBase-root | 
        driver.findElement(By.cssSelector(".MuiButtonBase-root")).click();
        // 12 | click | css=.MuiTypography-body1 | 
        driver.findElement(By.cssSelector(".MuiTypography-body1")).click();
        // 13 | click | css=.MuiTypography-body1 | 
        driver.findElement(By.cssSelector(".MuiTypography-body1")).click();

        driver.quit();
    }
}