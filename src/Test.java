import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {
	
	public Test() throws IOException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("maxminvalue.properties");
		Properties maxminProps = new Properties();
		maxminProps.load(in);
		System.out.println(maxminProps.getProperty("bpm_max"));
	}
	
	public static void main(String args[]) throws IOException {
		Test a = new Test();
	}

}
