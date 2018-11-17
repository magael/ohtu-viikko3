package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {

    public static void main(String[] args) {
        WebDriver driver = new HtmlUnitDriver();
        driver.get("http://localhost:4567");

        // tulostetaan sivu konsoliin
        System.out.println(driver.getPageSource());

        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        // tulostetaan sivu konsoliin
        System.out.println(driver.getPageSource());

        sleep(2);

        // epäonnistunut kirjautuminen: oikea käyttäjätunnus, väärä salasana
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkuj");
        element = driver.findElement(By.name("login"));
        
        sleep(2);
        element.submit();
        
        // tulostetaan sivu konsoliin
        System.out.println(driver.getPageSource());

        // epäonnistunut kirjautuminen: ei-olemassaoleva käyttäjätunnus
        Random r = new Random();

        element = driver.findElement(By.name("username"));
        element.sendKeys("arto" + r.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("otra");
        
        sleep(2);
        element.submit();
        
        // tulostetaan sivu konsoliin
        System.out.println(driver.getPageSource());
        
        // uuden käyttäjätunnuksen luominen
        element = driver.findElement(By.partialLinkText("home"));
        element.click();
        element = driver.findElement(By.partialLinkText("user"));
        element.click();
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("arto" + r.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("otra");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("otra");
        
        sleep(2);
        element.submit();
        
        // tulostetaan sivu konsoliin
        System.out.println(driver.getPageSource());
        
        // uuden käyttäjätunnuksen luomisen jälkeen tapahtuva ulkoskirjautuminen sovelluksesta
        element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();
        element = driver.findElement(By.linkText("logout"));
        element.click();
        
        sleep(2);
        
        // tulostetaan sivu konsoliin
        System.out.println(driver.getPageSource());

        driver.quit();
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
