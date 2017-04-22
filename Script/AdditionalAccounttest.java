package com.appiumtest;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
//import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

@SuppressWarnings("unused")
public class AdditionalAccounttest {
  AppiumDriver<WebElement> driver;
  @BeforeTest
  public void Setup() throws MalformedURLException{
	  System.out.println("print");
	  System.out.println("print");System.out.println("print");
	  DesiredCapabilities cap = new DesiredCapabilities();
	  cap.setCapability(MobileCapabilityType.DEVICE_NAME, "ZTE-Z958");
	  cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.postmates.android");
	  //added after first success
	// cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.postmates.android.IntroActivity");
	 // cap.setCapability(AndroidMobileCapabilityType.VERSION, "5.1");
	  
	  //cap.setCapability("avd","cda13c43");
	  System.out.println("This is driver"+driver);
	  driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),cap); 
  }
  @Test
  private  void checkUIElements() {
	  System.out.println("This is driver $$$$"+driver);
		Assert.assertNotNull(driver.getContext());
		driver.findElementById("intro_signin_container").click();
		driver.findElementById("email_layout").sendKeys("bharadwaj.vfg42@gmail.com");
		driver.findElementById("password_layout").sendKeys("Mysjsu2015!");
		driver.findElementById("login_button").click();
	   	int count =0;
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
}
  @Test
  private void verifyMenuItems() {
		// TODO Auto-generated method stub
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
	    		System.out.println("\n"+ i+1 + "." + menuItems[i]);
	    	}
	    }
			GsonBuilder gsonBuilder = new GsonBuilder();
				// TODO Auto-generated method stub
		    	Gson gson = gsonBuilder.create();
		    	//driver.findElement(By.className("android.widget.ImageButton")).click();
		    	driver.findElementById("left_drawer").findElements(By.className("android.widget.LinearLayout")).get(2).click();
		    	boolean detailsCheck = driver.findElementById("save_action").isEnabled();
		    	boolean emailEditable = driver.findElementById("account_email").getAttribute("clickable").equals("false");
		    	Assert.assertTrue( "Test case Failed. Save action is enabled by default without any modification of user details",!detailsCheck);
		    	Assert.assertTrue( "Test case failed. Email address should not be editable.",!emailEditable);
		    	driver.findElementById("profile_image_view");
		    	WebElement accountName = driver.findElementById("account_full_name");
		    	WebElement phone = driver.findElementById("account_mobile_number");
		    	String defaultName = accountName.getText(); 
		    	String defaultPhone = phone.getText();
		    	try {
					BufferedReader br = new BufferedReader(new FileReader("testData/accountDetails.properties"));
			    	List<fetchDetails> fd =  gson.fromJson(br, new TypeToken<List<fetchDetails>>(){}.getType());
			    	for(int i=0; i<fd.size(); i++){
			    		accountName.clear();
			    		accountName.sendKeys(fd.get(i).getName());
			    		((AndroidDriver<WebElement>) driver).pressKeyCode(AndroidKeyCode.ENTER);
			    		phone.clear();
			    		phone.sendKeys(fd.get(i).getPhone());
			    		if(!accountName.getText().equals(defaultName) || !phone.getText().equals(defaultPhone)){
			    			System.out.println("condition check");
			    			Assert.assertTrue( "Testcase Failed. Save action should be enabled when details are modified", !detailsCheck);
			    			driver.findElementById("save_action").click();
			    			System.out.println("clicked on save");
			    			//if((accountName.getText().isEmpty())|| (!fd.get(i).getPhone().substring(0, 1).equals("1") && fd.get(i).getPhone().length()!=10)|| (fd.get(i).getPhone().substring(0, 1).equals("1") && fd.get(i).getPhone().length()!=11)){
				    		//if(driver.findElementById("parentPanel").findElement(By.id("contentPanel")).findElement(By.id("message")).isEnabled()){	
			    			if(driver.findElementByName("OK").isEnabled()){
			    			System.out.println("condition check-2");
			    				Assert.assertTrue( "Test case failed. Error Message is not displayed",driver.findElementByName("OK").isEnabled());
			    				//driver.findElementById("parentPanel").findElement(By.id("contentPanel")).findElement(By.id("buttonPanel")).findElement(By.id("button1")).click();
			    				//Thread.sleep(3000);
			    				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			    				driver.findElementByName("OK").click();
			    				//continue;
			    			}
			    			else{
			    				Assert.assertTrue("Testcase failed. Details not updated", (accountName.getText().equals(fd.get(i).getName())&& phone.getText().equals(fd.get(i).getPhone())));
			    				defaultName = accountName.getText(); 
			    		    	defaultPhone = phone.getText(); 
			    			}
			    		}
			    	}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		    	//System.out.println(defaultName+"\t"+defaultEmail+ "\t" + defaultPhone);
		    	
		
	    
	}
}
