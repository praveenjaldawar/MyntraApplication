package testCases;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import baseClass.TestBase;

public class SearchProduct extends TestBase {

	@Test
	public static void addToBag() throws IOException {
		
		test = extent.createTest("addToBag");

		Actions actions = new Actions(driver);

		WebElement menTab = driver.findElement(By.xpath(or.getProperty("XMen")));

		Assert.assertEquals(menTab.isDisplayed(), true);

		Assert.assertEquals(menTab.isEnabled(), true);

		screenShots();

		actions.moveToElement(menTab).perform();

		screenShots();

		WebElement phonecaseTab = driver.findElement(By.xpath(or.getProperty("XPhonecase")));

		Assert.assertEquals(phonecaseTab.isDisplayed(), true);

		Assert.assertEquals(phonecaseTab.isEnabled(), true);

		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOf(phonecaseTab));

		actions.moveToElement(phonecaseTab).click().perform();

		screenShots();

		driver.findElement(By.xpath(or.getProperty("XThirdRowThird"))).click();

		String parentWindow = driver.getWindowHandle();

		Set<String> handles = driver.getWindowHandles();

		for (String newWindowHandle : handles) {

			if (!newWindowHandle.equals(parentWindow)) {

				driver.switchTo().window(newWindowHandle);

				screenShots();

				WebElement price = driver.findElement(By.xpath(or.getProperty("XPrice")));

				Assert.assertEquals(price.isDisplayed(), true);

				String s = driver.findElement(By.xpath(or.getProperty("XPrice"))).getText();
				System.out.println(s);

				String[] value = s.split(" ");

				int priceTag = Integer.parseInt(value[1]);
				System.out.println(priceTag);

				if (priceTag > 10) {

					Assert.assertTrue(true);

					System.out.println(priceTag + " is greater than 10 ");
				} else {

					Assert.assertTrue(false);

					System.out.println(priceTag + " is less than 10 ");
				}

			}

		}

	}

}
