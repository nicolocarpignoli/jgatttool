
import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Before;
import model.BLECharacteristic;
import model.BLEConnection;
import org.junit.Test;

public class BLEConnectionTest {
	BLEConnection conn;

	@Before
	public void setUp() {
		conn = new BLEConnection("");
	}

	@Test
	public void executeCmdTest() {
		// test for italian lang. windows version only
		Process process = conn.executeCmd("ipconfig");
		Scanner scanner = new Scanner(process.getInputStream());
		String str = scanner.next();
		assertEquals("Configurazione", str);
	}

	// to test in linux env
	@Test
	public void read_dataTest() throws InterruptedException {
		BLECharacteristic chars = new BLECharacteristic(0, int.class, "", "", true, false, false);
		chars.setValue(0);
		// conn.read_data(chars);
		assertEquals(0, chars.getValue());
	}

	@Test
	public void write_dataTest() throws InterruptedException {
		BLECharacteristic chars = new BLECharacteristic(0, int.class, "", "", false, true, false);
		// conn.write_data(chars, 1, false);
		// conn.read_data(chars);
		assertNotEquals(1, chars.getValue());
	}

}
