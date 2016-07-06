package ioio.examples.hello_console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import eu.myopg.drukhadze.e39ioio.canbus.CanBusMCP;
import eu.myopg.drukhadze.e39ioio.geber.AnalogGeber;
import eu.myopg.drukhadze.e39ioio.geber.AnalogGeberNTC;
import eu.myopg.drukhadze.e39ioio.geber.AnalogGeberVoltmeter;
import eu.myopg.drukhadze.e39ioio.utils.Geber;
import eu.myopg.drukhadze.e39ioio.utils.NTCSensor;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOConnectionManager.Thread;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.pc.IOIOConsoleApp;

public class HelloIOIOConsole extends IOIOConsoleApp {
	private boolean ledOn_ = false;

	// Boilerplate main(). Copy-paste this code into any IOIOapplication.
	public static void main(String[] args) throws Exception {
//		System.setProperty("ioio.SerialPorts", "COM8");
		new HelloIOIOConsole().go(args);
	}

	@Override
	protected void run(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		boolean abort = false;
		String line;
		while (!abort && (line = reader.readLine()) != null) {
			if (line.equals("t")) {
				ledOn_ = !ledOn_;
			} else if (line.equals("n")) {
				ledOn_ = true;
			} else if (line.equals("f")) {
				ledOn_ = false;
			} else if (line.equals("q")) {
				abort = true;
			} else {
				System.out
						.println("Unknown input. t=toggle, n=on, f=off, q=quit.");
			}
		}
	}

	@Override
	public IOIOLooper createIOIOLooper(String connectionType, Object extra) {
		return new BaseIOIOLooper() {
			private DigitalOutput led_;
//			private AnalogInput		geber3 = null;
			AnalogGeber geber1 = null;
			AnalogGeber geber2 = null;
			AnalogGeber geber3 = null;
			AnalogGeber geber16 = null;
			Uart iBus = null;
			CanBusMCP canBus = null;
			private ArrayList<Byte> iBusReadBuffer = new ArrayList<Byte>();
			@Override
			protected void setup() throws ConnectionLostException,
					InterruptedException {
				
				//geber3 = ioio_.openAnalogInput(Geber.GEBER2.getPin());
				geber1 = new AnalogGeberNTC(ioio_, Geber.GEBER1, 100, NTCSensor.BMWE46Getriebe);
				geber1.start();
				geber2 = new AnalogGeberNTC(ioio_, Geber.GEBER2, 100, NTCSensor.BMWE46Getriebe);
				geber2.start();
				geber3 = new AnalogGeberNTC(ioio_, Geber.GEBER3, 100, NTCSensor.BMWE46Getriebe);
				geber3.start();

				geber16 = new AnalogGeberVoltmeter(ioio_, Geber.GEBER16, 9900f, 64000f);
				geber16.start();
				
				// TODO connect to Bus to test incoming traffic
//				iBus = ioio_.openUart(13, 14, 9600, Uart.Parity.EVEN, Uart.StopBits.ONE);
				// TODO can bus not implemented yet
/*				canBus = new CanBusMCP(ioio_);
				canBus.initSPI();*/
			}

			@Override
			public void loop() throws ConnectionLostException,
					InterruptedException {
				
				Thread.sleep(100);
//				float currValue = geber3.read();
//				float currVoltage = geber3.getVoltage();
				// NTC 2252Ohm
//				float temparature = temperature_NTC(25, 2252, 100, 152.83f, 100, currValue);
				//NTC 2000 Ohm
//				float temparature = temperature_NTC(25, 2000, 100, 135.79f, 100, currValue);
//				double temp = temp(100,  currValue);
//				System.out.println("GEBER3: currValue: " + currValue + " currVoltage: " + currVoltage + /*" currTemp: " + temp +*/ " temperature: " + temparature);
				
//				System.out.print("GEBER1: " + geber1.getValue() + " C ");
//				System.out.print("GEBER2: " + geber2.getValue() + " C ");
//				System.out.println("GEBER3: " + geber3.getValue() + " C ");
				System.out.println(String.format("GEBER1: %.3f C, GEBER2: %.3f C, GEBER3: %.3f C, GEBER16(V-Meter): %.2f, IBus Buf Size: %d", geber1.getValue(), geber2.getValue(), geber3.getValue(), geber16.getValue(), iBusReadBuffer.size()));
				try {
					if (iBus.getInputStream().available()>0)
					{
						iBusReadBuffer.add((byte)iBus.getInputStream().read());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			float ABSZERO = 273.15f;
			
			float temperature_NTC(float T0, float R0, float T1, float R1, float RV, float VA_VB)
			// Ermittlung der Temperatur mittels NTC-Widerstand
			// Version der Funktion bei unbekannter Materialkonstante B
			// Erklärung der Parameter:
			// T0           : Nenntemperatur des NTC-Widerstands in °C
			// R0           : Nennwiderstand des NTC-Sensors in Ohm
			// T1           : erhöhte Temperatur des NTC-Widerstands in °C
			// R1           : Widerstand des NTC-Sensors bei erhöhter Temperatur in Ohm
			// Vorwiderstand: Vorwiderstand in Ohm  
			// VA_VB        : Spannungsverhältnis "Spannung am NTC zu Betriebsspannung"
			// Rückgabewert : Temperatur
			{
			 T0+=ABSZERO;  // umwandeln Celsius in absolute Temperatur
			 T1+=ABSZERO;  // umwandeln Celsius in absolute Temperatur
			 double B= (T0 * T1)/ (T1-T0) * Math.log(R0/R1); // Materialkonstante B
			 float RN=RV*VA_VB / (1-VA_VB); // aktueller Widerstand des NTC
//			 System.out.println("Curr R: " + RN + " B=" + B + " TEMP: " + (float)(T0 * B / (B + T0 * Math.log(RN / R0))));
			 return (float)(T0 * B / (B + T0 * Math.log(RN / R0)))-ABSZERO;
			}

			
			double temp(float RV, float VA_VB)
			{
				float Divisor;
				float edTl, edTm, edTh;
				float lnRm, lnRh;
				float ln3Rm, ln3Rh;
//				float Ergebnis;
				double lgR;
//				float R_current;

				  edTl = (float)(1/(3 + 273.15));
				  edTm = (float)(1/(25 + 273.15));
				  edTh = (float)(1/(41 + 273.15));
				  lnRm  = (float)Math.log(2000);
				  ln3Rm = lnRm*lnRm*lnRm;
				  lnRh  = (float)Math.log(1023);
				  ln3Rh = lnRh*lnRh*lnRh;
				  Divisor = lnRm*ln3Rh - lnRh*ln3Rm;
				  double AA = (1/(4 + 273.15));
				  double BB = ((edTm - edTl)*ln3Rh - (edTh - edTl)*ln3Rm) / Divisor;
				  double CC = ((edTh - edTl)*lnRm - (edTm - edTl)*lnRh) / Divisor;
				  
				  float RN=RV*VA_VB / (1-VA_VB); // aktueller Widerstand des NTC
				   lgR = Math.log(RN/5611);
				  return  1.0/(AA + BB*lgR + CC*lgR*lgR*lgR) - 273.15;
			}
		};
	}
}
