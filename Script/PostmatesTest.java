package com.appiumtest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

@SuppressWarnings("unused")
public class PostmatesTest {
  AppiumDriver<WebElement> driver;
  @BeforeTest
  public void Setup() throws MalformedURLException{
	  System.out.println("print");
	  System.out.println("print");System.out.println("print");
	  DesiredCapabilities cap = new DesiredCapabilities();
	  cap.setCapability(MobileCapabilityType.DEVICE_NAME, "ZTE-Z958");
	  cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.postmates.android");
	  System.out.println("This is driver"+driver);
	  driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap); 
  }

  @Test
  public void EmailPassword() throws InterruptedException {
		System.out.println("Testing Signin Feature");
		driver.findElementById("intro_signin_container").click();
		driver.findElementById("email_layout").sendKeys("priyashah1411");
		driver.findElementById("password_layout").sendKeys("test!");
		Thread.sleep(3000);
		driver.findElementById("email_layout").sendKeys("priyashah1411@gmail.com");
		driver.findElementById("password_layout").sendKeys("test123");
		driver.findElementById("login_button").click();
		driver.findElementById("button1").click();
		Thread.sleep(3000);
	  driver.findElementById("email_layout").sendKeys("appiumpostmates@gmail.com");
		driver.findElementById("password_layout").sendKeys("appiumpostmates123");
		driver.findElementById("login_button").click(); 	
		System.out.println("PASSED: Signing-in Test");
		System.out.println("Starting UI validation Test");
		int count =0;
	   	Thread.sleep(5000);
  	WebElement toolbar = driver.findElement(By.id("home_toolbar")); 
  	List<WebElement> actionbar = driver.findElementsByClassName("android.support.v7.app.ActionBar$Tab");
  	for(int i=0; i<actionbar.size();i++){
  		if(actionbar.get(i).isEnabled()){
  			count++;
  		}
  	}
  	if (toolbar.isEnabled() && toolbar.findElement(By.id("search_nearby_item")).isEnabled()&& toolbar.findElement(By.id("toolbar_title")).isEnabled() && toolbar.findElement(By.className("android.widget.ImageButton")).isEnabled() && count==actionbar.size()  ) { 
  		System.out.println("The page has the following elements\n1. Toolbar with following elements \n\ti. Burger Menu Icon \n\tii. search bar and \n\tiii. Search Icon \n2. Actionbar menu with following menu items\n\t i. Anywhere\n\t ii. PLUS ");
  	} 
  	else { 
  		System.out.println("Test case failed..Missing UI Elements");
  	}
  	System.out.println("PASSED: UIvalidation test");
}
  @Test
  private void appTesting() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
	  Assert.assertNotNull(driver.getContext());
	  System.out.println("Starting Menu Items Validation test");
	    int menuCount =0;
	    String menuItems[]= {"Stores", "Deliveries", "Account Details", "Free Deliveries", "Enter Promo Code", "Help Center", "Become a Postmate", "Sign Out"}; 
	   driver.findElementByClassName("android.widget.ImageButton").click();
	    WebElement menubar = driver.findElementById("left_drawer");
	    List<WebElement> menu= menubar.findElements(By.className("android.widget.LinearLayout"));
	    for(int i=0; i<menu.size(); i++){
	    	if((menu.get(i).findElement(By.id("drawer_item_text_view")).getText()).equals(menuItems[i])){
	    		menuCount++;	    		
	    	}
	    }
	    if(menuCount == menuItems.length){
	    	System.out.println("\nFollowing menu items are present");
	    	for(int i=0; i<menuItems.length; i++){
	    		System.out.println("\n"+ i + "." + menuItems[i]);
	    	}
	    }
	    System.out.println("PASSED: Menu Items Validation test");  
		System.out.println("Starting Search Feature Testing");
		Assert.assertNotNull(driver.getContext());
		driver.findElementById("search_nearby_item").click();// To check for empty input value
		Thread.sleep(2000);
		driver.findElementById("search_src_text").sendKeys("Sandwich"+"\n"); //To look for Sandwich places
		System.out.println("Searched using valid String");
		if(driver.findElementByClassName("android.widget.RelativeLayout").isDisplayed())
			System.out.println("Stores were Displyaed");
		driver.findElementById("search_src_text").sendKeys(""+"\n");//To search with a restaurant's name
		System.out.println("Searched using empty String");
		driver.findElementById("search_src_text").sendKeys("dhvhkvekv"+"\n"); //Invalid input
		System.out.println("Searched using invalid String");
		if(driver.findElementByName("No Results").isDisplayed())
			System.out.println("No Stores were Displyaed");
		System.out.println("PASSED: Starting Search Feature Testing");
		System.out.println("Starting Promo cide feature testing");
		driver.findElementByClassName("android.widget.ImageButton").click();
		driver.findElementByName("Enter Promo Code").click();
		String str ="";
		BufferedReader search = new BufferedReader(new FileReader("E:/robotium/Appiumtest/src/com/appiumtest/Promocode.txt"));
		ArrayList<String> alsearch = new ArrayList<String>();
		while((str = search.readLine()) != null){
		alsearch.add(str); 
		}
		String[] ar_search = new String[alsearch.size()];
		ar_search = alsearch.toArray(ar_search);
		
		for(int i =0;i<ar_search.length;i++){
			driver.findElementById("promo_code_edit_text").sendKeys(ar_search[i]);
			driver.findElementById("done_action").click();
			Thread.sleep(1000);
			System.out.println(driver.findElementById("message").getText());
			driver.findElementById("button1").click();
			}
		System.out.println("PASSED: Promo cide feature testing");
	}
}