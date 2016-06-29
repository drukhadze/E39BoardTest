package eu.myopg.drukhadze.e39ioio.canbus;

import java.util.Arrays;

import eu.myopg.drukhadze.e39ioio.utils.MCPConstants;
import ioio.lib.api.IOIO;
import ioio.lib.api.SpiMaster;
import ioio.lib.api.exception.ConnectionLostException;

public class CanBusMCP {
	IOIO _ioio = null;
	SpiMaster spi = null;
	byte recBuf[] = new byte[8];
	
	public CanBusMCP(IOIO _ioio) {
		this._ioio = _ioio;
		
	}
	
	public void initSPI() throws ConnectionLostException
	{
		spi = _ioio.openSpiMaster(10, 11, 12, 18, SpiMaster.Rate.RATE_8M);
		
	}

	private boolean initCan() throws ConnectionLostException, InterruptedException
	{
		spiPush(MCPConstants.Commands.RESET);
		Thread.sleep(100);
		
		byte mode = (byte)(readReg(MCPConstants.Registers.CANSTAT) >> 5);
		if (mode != 0b100)
			return false;
		
		//TODO CNF Values
		return setCANcnfRegisters((byte)0x00, (byte)0x00, (byte)0x00);
	}
	
	boolean setCANcnfRegisters(byte cnf1, byte cnf2, byte cnf3) throws ConnectionLostException, InterruptedException
	{
		writeReg(MCPConstants.Registers.CNF1, cnf1); 
		writeReg(MCPConstants.Registers.CNF2, cnf2);
		writeReg(MCPConstants.Registers.CNF3, cnf3);
		
		return true;
	}
	
	private void spiPush(byte data) throws ConnectionLostException, InterruptedException
	{
		spi.writeRead(new byte[]{data}, 1, 1, null, 0);
	}

	private byte[] spiReadBuf() throws ConnectionLostException, InterruptedException
	{
		spi.writeRead(null, 0, recBuf.length, recBuf, recBuf.length);
		return Arrays.copyOf(recBuf, recBuf.length);
	}

	private byte[] spiRead(int size) throws ConnectionLostException, InterruptedException
	{
		spi.writeRead(null, 0, size, recBuf, size);
		return Arrays.copyOf(recBuf, size);
	}

	private byte spiReadByte() throws ConnectionLostException, InterruptedException
	{
		return spiRead(1)[0];
	}

	byte readReg(byte regno) throws ConnectionLostException, InterruptedException
	{
	  byte val;
//	  spi.writeRead(new byte[]{, writeSize, totalSize, readData, readSize);
	  spiPush(MCPConstants.Commands.READ);
	  spiPush(regno);
	  val = spiReadByte();

	  return val;  
	}  
	
	void writeReg(byte regno, byte val) throws ConnectionLostException, InterruptedException
	{
	  spiPush(MCPConstants.Commands.WRITE);
	  spiPush(regno);
	  spiPush(val);
	}


}
