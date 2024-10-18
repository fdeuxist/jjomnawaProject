package com.example.jjomnawaProject.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Crawler {

    // https://lotuus.tistory.com/108?category=981314
    // https://googlechromelabs.github.io/chrome-for-testing/
    //chromedriver	win32	https://storage.googleapis.com/chrome-for-testing-public/129.0.6668.100/win32/chromedriver-win32.zip
    // https://www.selenium.dev/documentation/webdriver/elements/finders/#find-elements-from-element

    private WebDriver driver;

    private static final String curl = "https://www.compuzone.co.kr/product/product_detail.htm?ProductNo=1078453";
    private static final String iurl = "https://usr.icoda.co.kr/item/view/1453620";
    private static final String jurl = "https://www.joyzen.co.kr/product/sInfo.html?fid=1&uid=38&Pnum=416965";

    public void process() {
        System.out.println("aaaaaaaaaaaaaaaa");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\chromedriver-win32\\chromedriver.exe");
        //크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)

        driver = new ChromeDriver();
        //브라우저 선택

        try {
            getData(curl,".price_real");
            getData(iurl,".total_money");
            getData(jurl,"#total_price");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (driver != null) {
                driver.close();	//탭 닫기
                driver.quit();	//브라우저 닫기
            }
        }



    }

    private String getData(String url, String selector) throws InterruptedException {

        driver.get(url); // 브라우저에서 URL로 이동
        Thread.sleep(1000); // 잠시 대기

        WebElement priceElement = driver.findElement(By.cssSelector(selector));
        System.out.println("c price : "+ priceElement.getText());
        String price = priceElement.getText();
        return price;
    }

    /*
    // data가져오기
    private List<String> getDataList() throws InterruptedException {
        List<String> priceList = new ArrayList<>();

        driver.get(url); // 브라우저에서 URL로 이동
        Thread.sleep(1000); // 잠시 대기

        List<WebElement> prices = driver.findElements(By.cssSelector(".price_real"));
        System.out.println("prices : "+ prices);
        System.out.println("총 가격 수: " + prices.size());
        for (WebElement priceElement : prices) {
            String price = priceElement.getText();
            System.out.println("가격: " + price);
            priceList.add(price);
        }
        WebElement price = driver.findElement(By.cssSelector(".price_real"));
        System.out.println("price : "+ price);
        System.out.println("price : "+ price.getText());
        return priceList;
    }
    */





}
