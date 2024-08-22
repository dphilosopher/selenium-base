import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SauceDemoTest extends BaseTest {

    @Test
    public void testLoginSuccessful() {
        driver.get("https://www.saucedemo.com/");

        // Confirm successful login with standard_user credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Confirm successful login by checking the presence of product header title
        Assertions.assertTrue(driver.findElement(By.id("right_component")).isDisplayed(), "Login was not successful, inventory container not found");
    }

    @Test
    public void testDefaultSortingByName() {
        // Login first
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // validate default sorting (A -> Z)
        List<String> itemNames = driver.findElements(By.className("inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> sortedNames = itemNames.stream().sorted().collect(Collectors.toList());
        Assertions.assertEquals(sortedNames, itemNames, "Items are not sorted A -> Z by default");
    }

    @Test
    public void testSortingByNameDescending() {
        // Login first
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Change sorting to Z -> A
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Name (Z to A)']")).click();

        // Verify sorting (Z -> A)
        List<String> itemNames = driver.findElements(By.className("inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> sortedNames = itemNames.stream().sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());
        Assertions.assertEquals(sortedNames, itemNames, "Items are not sorted Z -> A correctly");
    }

    @Test
    public void testLoginAndDefaultSortingAndSortingByNameDescending() {
        driver.get("https://www.saucedemo.com/");

        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Verify default sorting (A -> Z)
        List<String> itemNames = driver.findElements(By.className("inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> sortedNames = itemNames.stream().sorted().collect(Collectors.toList());
        Assertions.assertEquals(sortedNames, itemNames, "Items are not sorted A -> Z by default");

        // Change sorting to Z -> A
        driver.findElement(By.className("product_sort_container")).click();
        driver.findElement(By.xpath("//option[text()='Name (Z to A)']")).click();

        // Verify sorting (Z -> A)
        itemNames = driver.findElements(By.className("inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        sortedNames = itemNames.stream().sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());
        Assertions.assertEquals(sortedNames, itemNames, "Items are not sorted Z -> A correctly");
    }
}