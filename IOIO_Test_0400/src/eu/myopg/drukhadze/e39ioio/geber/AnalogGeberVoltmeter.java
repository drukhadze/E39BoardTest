package eu.myopg.drukhadze.e39ioio.geber;


import ioio.lib.api.IOIO;
import eu.myopg.drukhadze.e39ioio.utils.Geber;

public class AnalogGeberVoltmeter extends AnalogGeber {
	public static final float DEFAULT_R1 = 68000f;
	public static final float DEFAULT_R2 = 10000f;
	float R1 = 0;

	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float R2in) {
		super(ioio, pin, R2in);
		this.R1 = DEFAULT_R1;
	}

	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float R2in, int bufferSize) {
		super(ioio, pin, R2in, bufferSize);
		this.R1 = DEFAULT_R1;
	}

	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float R2in, int bufferSize, float R1in) {
		super(ioio, pin, R2in, bufferSize);
		this.R1 = R1in;
	}
	public AnalogGeberVoltmeter(IOIO ioio, Geber pin, float R2in, float R1in) {
		super(ioio, pin, R2in, BUFFER_SIZE);
		this.R1 = R1in;
	}

	@Override
	public float getValue()
	{
		float currAvgValue = super.getAverageValue();
		return calculateVoltage(currAvgValue);
	}
	
	private float calculateVoltage(float value)
	{
		float vIn = value * _aInput.getReference();
		return  vIn/((divideResistor/ (R1+divideResistor) ));
	}
}
