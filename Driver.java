import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Driver {

	public static void main(String[] args) throws IOException
	{
		Sumatra sumatra = new Sumatra();
		sumatra.scan();
		sumatra.parse();
	}
}
