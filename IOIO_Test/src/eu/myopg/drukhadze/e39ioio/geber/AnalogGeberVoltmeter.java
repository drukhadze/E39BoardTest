package eu.myopg.drukhadze.e39ioio.geber;

import ioio.lib.api.IOIO;
import eu.myopg.drukhadze.e39ioio.utils.Geber;

public class AnalogGeberVoltmeter extends AnalogGeber {
	static final float DEFAULT_R1 = 100000f;
	float R1 = 0;

	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float resistor) {
		super(ioio, pin, resistor);
		this.R1 = DEFAULT_R1;
	}

	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float resistor, int bufferSize) {
		super(ioio, pin, resistor, bufferSize);
		this.R1 = DEFAULT_R1;
	}

	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float resistor, int bufferSize, float R1in) {
		super(ioio, pin, resistor, bufferSize);
		this.R1 = R1in;
	}
	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float resistor, float R1in) {
		super(ioio, pin, resistor, BUFFER_SIZE);
		this.R1 = R1in;
	}

	@Override
	public float getValue()
	{
		float currAvgValue = super.getValue();
		return calculateVoltage(currAvgValue);
	}
	
	private float calculateVoltage(float value)
	{
		float vIn = value*_aInput.getReference();
		return  (divideResistor/(R1+divideResistor)) * vIn;
	}
}
