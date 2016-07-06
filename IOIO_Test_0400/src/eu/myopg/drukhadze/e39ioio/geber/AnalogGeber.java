package eu.myopg.drukhadze.e39ioio.geber;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import eu.myopg.drukhadze.e39ioio.utils.Geber;
import ioio.lib.api.AnalogInput;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

public abstract class AnalogGeber extends Thread {
	static final int BUFFER_SIZE = 20;
	
	IOIO _ioio = null;
	AnalogInput _aInput = null;
	Geber PIN = null;
	float divideResistor = 0;
	CircularFifoQueue<Float> values = null; 
	
	public AnalogGeber(IOIO ioio, Geber pin, float resistor)
	{
		this(ioio, pin, resistor, BUFFER_SIZE);
	}
	
	public AnalogGeber(IOIO ioio, Geber pin, float resistor, int bufferSize)
	{
		this._ioio = ioio;
		this.PIN = pin;
		this.divideResistor = resistor;
		values = new CircularFifoQueue<Float>(bufferSize);
	}

	abstract public float getValue();
	float getAverageValue()
	{
		return calculateAverage(new ArrayList<Float>(values));
	}
	
	float calculateAverage(List <Float> marks) {
	  float sum = 0;
	  if(!marks.isEmpty()) {
	    for (Float mark : marks) {
	        sum += mark;
	    }
	    return sum / marks.size();
	  }
	  return sum;
	}

	@Override
	public void run() {
		try {
			while (true)
			{
				if (_aInput == null)
				{
					_aInput = _ioio.openAnalogInput(PIN.getPin());
				}
				
				values.add(_aInput.read());
				// 50 millisekunden
				try {
					sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionLostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
