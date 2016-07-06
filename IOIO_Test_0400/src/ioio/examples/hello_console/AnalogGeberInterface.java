package ioio.examples.hello_console;

import ioio.lib.api.AnalogInput;
import ioio.lib.api.exception.ConnectionLostException;

public class AnalogGeberInterface implements AnalogInput {
	AnalogInput input = null;
	float vorwiederstand = 0;
	
	public AnalogGeberInterface(AnalogInput analogObj, float vorwiederstand)
	{
		this.input = analogObj;
		this.vorwiederstand = vorwiederstand;
	}
	
	@Override
	public void close() {
		input.close();
	}

	@Override
	public float getVoltage() throws InterruptedException,
			ConnectionLostException {
		return input.getVoltage();
	}

//	@Override
//	public float getVoltageSync() throws InterruptedException,
//			ConnectionLostException {
//		return input.getVoltageSync();
//	}

	@Override
	public float getReference() {
		return input.getReference();
	}

	@Override
	public float read() throws InterruptedException, ConnectionLostException {
		return input.read();
	}

//	@Override
//	public float readSync() throws InterruptedException,
//			ConnectionLostException {
//		return input.readSync();
//	}

	@Override
	public void setBuffer(int capacity) throws ConnectionLostException {
		input.setBuffer(capacity);
	}

	@Override
	public int getOverflowCount() throws ConnectionLostException {
		return input.getOverflowCount();
	}

	@Override
	public int available() throws ConnectionLostException {
		return input.available();
	}

	@Override
	public float readBuffered() throws InterruptedException,
			ConnectionLostException {
		return input.readBuffered();
	}

	@Override
	public float getVoltageBuffered() throws InterruptedException,
			ConnectionLostException {
		return input.getVoltageBuffered();
	}

	@Override
	public float getSampleRate() throws ConnectionLostException {
		return input.getSampleRate();
	}
}
