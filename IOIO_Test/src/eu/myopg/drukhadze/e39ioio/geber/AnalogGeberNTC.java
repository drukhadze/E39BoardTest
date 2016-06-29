package eu.myopg.drukhadze.e39ioio.geber;

import ioio.lib.api.IOIO;
import eu.myopg.drukhadze.e39ioio.utils.Geber;
import eu.myopg.drukhadze.e39ioio.utils.NTCSensor;

public class AnalogGeberNTC extends AnalogGeber {
	
	static final float ABSZERO = 273.15f;
	
	float T1 = 0;
	float R1 = 0;
	float T2 = 0;
	float R2 = 0;

	public AnalogGeberNTC(IOIO ioio, Geber pin, float resistor) {
		super(ioio, pin, resistor);
		
	}

	public AnalogGeberNTC(IOIO ioio, Geber pin, float resistor, int bufferSize) {
		super(ioio, pin, resistor, bufferSize);
		// TODO Auto-generated constructor stub
	}

	public AnalogGeberNTC(IOIO ioio, Geber pin, float resistor, int bufferSize, float T1in, float R1in, float T2in, float R2in) {
		super(ioio, pin, resistor, bufferSize);
		this.T1 = T1in;
		this.R1 = R1in;
		this.T2 = T2in;
		this.R2 = R2in;
	}
	public AnalogGeberNTC(IOIO ioio, Geber pin, float resistor, float T1in, float R1in, float T2in, float R2in) {
		super(ioio, pin, resistor, BUFFER_SIZE);
		this.T1 = T1in;
		this.R1 = R1in;
		this.T2 = T2in;
		this.R2 = R2in;
	}
	

	public AnalogGeberNTC(IOIO ioio, Geber pin, float resistor, NTCSensor ntcSensor) {
		super(ioio, pin, resistor, BUFFER_SIZE);
		this.T1 = ntcSensor.getTlow();
		this.R1 = ntcSensor.getRlow();
		this.T2 = ntcSensor.getThigh();
		this.R2 = ntcSensor.getRhigh();
	}

	@Override
	public float getValue()
	{
		float currAvgValue = super.getValue();
		return temperature_NTC(T1, R1, T2, R2,divideResistor, currAvgValue);
	}
	
	float temperature_NTC(float Tlow, float Rlow, float Thigh, float Rhigh, float RV, float VA_VB)
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
	 Tlow+=ABSZERO;  // umwandeln Celsius in absolute Temperatur
	 Thigh+=ABSZERO;  // umwandeln Celsius in absolute Temperatur
	 double B= (Tlow * Thigh)/ (Thigh-Tlow) * Math.log(Rlow/Rhigh); // Materialkonstante B
	 float RN=RV*VA_VB / (1-VA_VB); // aktueller Widerstand des NTC
//	 System.out.println("Curr R: " + RN + " B=" + B + " TEMP: " + (float)(T0 * B / (B + T0 * Math.log(RN / R0))));
	 return (float)(Tlow * B / (B + Tlow * Math.log(RN / Rlow)))-ABSZERO;
	}

}
