
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.Proxy;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import com.mysql.cj.jdbc.Driver;
public class MiniPro {

	static WebDriver driver;
	//String[] xlvalue=XlReader();
	//xlvalue[0];
	static String[] XlReader() throws IOException{
		
			File file = new File("C:\\Users\\rautm\\OneDrive\\Desktop\\Demo\\MiniProject\\Excel\\Read1.xlsx"); // creating a new file instance
			FileInputStream fis = new FileInputStream(file); // obtaining bytes from the file
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = wb.getSheetAt(0); // creating a Sheet object to retrieve object			
			String data0 = sheet1.getRow(0).getCell(0).getStringCellValue();
			String data1 = sheet1.getRow(1).getCell(0).getStringCellValue();
			String data2 = sheet1.getRow(2).getCell(0).getStringCellValue();
			String data3 = sheet1.getRow(0).getCell(1).getStringCellValue();
			wb.close();
		    String[] xs = new String[] {data0,data1,data2,data3};
		    String[] ret = new String[4];	
		    ret[0]=xs[0];
		    ret[1]=xs[1];
		    ret[2]=xs[2];
		    ret[3]=xs[3];
		    return ret;
		    
        }
/*-----------------------------------------------------------------------------------------------------------------------------------------------*/	
	static void Search() throws InterruptedException, IOException {
		String[] res = XlReader();
		for(int j=0; j<3; j++) {
		driver.get("https://www.practo.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@data-qa-id='omni-searchbox-locality']")).click();
		driver.findElement(By.xpath("//i[@class='icon-ic_cross_solid']")).click();
		driver.findElement(By.xpath("//input[@data-qa-id='omni-searchbox-locality']")).sendKeys(res[j]);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//span[@class='c-omni-suggestion-item__content'])[2]")).click();
		driver.findElement(By.xpath("//input[@data-qa-id='omni-searchbox-keyword']")).sendKeys(res[3]);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//div[@class='c-omni-suggestion-item__content__title'])[1]")).click();
		driver.findElement(By.xpath("(//div[@class='c-filter__select--checkbox u-d-inlineblock u-valign--middle u-pos-rel'])[2]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@class='u-spacer--right-thin']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//div[@class='c-filter__select--checkbox u-d-inlineblock u-valign--middle u-pos-rel'])[6]")).click();
		Thread.sleep(2000);
		List<WebElement> srchrslt = driver.findElements(By.xpath("//div[@class='c-card']"));
		int size=srchrslt.size();
		int i=0;
		String lines[];
		FileWriter fw=new FileWriter("C:\\Users\\rautm\\OneDrive\\Desktop\\Demo\\MiniProject\\Excel\\HospitalData.txt", true);//optn the text file		
		BufferedWriter bfw1 = new BufferedWriter(fw);
		//bfw.write("Execution date time");
		//bfw.write("Datetime");
		bfw1.write(res[j]);
		for(i=0; i<size; i++)
		{ 
		String details=srchrslt.get(i).getText();		
		lines=details.split("[\\r\\n]+");
		/*System.out.println("lines");
		System.out.println(lines[0]+"- 1"+lines[1]+" - 2"+lines[2]+" -3"+lines[3]);
		System.out.println(" - ");*/
		
		if(isNumeric(lines[3]))
		{
			if(Double.parseDouble(lines[3])>=3)
			{
				System.out.println("HOSPITAL HAS RATING > 3- "+ lines[0]);				
				bfw1.newLine();
				bfw1.write("HOSPITAL HAS RATING > 3- "+ lines[0]);
		        //System.out.println("Done");
				//bfw.newline
				//bfw.write("HOSPITAL HAS RATING > 3.5- "+ lines[0]);
			}			
		}
		
			System.out.println("----------------------------------");
		}
		bfw1.close();
		fw.close(); //close the text file
		}
	}
/*-----------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public static boolean isNumeric(String strNum) {
		if(strNum==null) {
			return false;
		}
		return Pattern.matches(strNum, strNum);
	}
/*-----------------------------------------------------------------------------------------------------------------------------------------------*/	

/*
public  proxy {
String proxy = "127.0.0.1:5000";
ChromeOptions options = new ChromeOptions().addArguments("--proxy-server=http://" + proxy);
WebDriver driver = new ChromeDriver(options);
//WebDriver driver = new ChromeDriver();
driver.get("https://www.google.co.in/");
}*/

/*---------------------------------------------------------------------------------------------------------------------------------------*/

	public static void main(String[] args) throws InterruptedException, IOException {
		//System.setProperty("webdriver.chrome.driver","C:\\Users\\rautm\\Downloads\\chromedriver_win32\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","C:\\Users\\rautm\\Downloads\\geckodriver-v0.29.1-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		//driver=new ChromeDriver();
		
		Search();
		
	}
	

}