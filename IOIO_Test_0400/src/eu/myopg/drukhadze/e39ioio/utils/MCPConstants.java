package eu.myopg.drukhadze.e39ioio.utils;

public interface MCPConstants {
	interface CANMode {
		
	}
	interface Commands {
		byte RESET = (byte) 0xC0;
		byte READ = 0x03;
		byte READ_RX_BUFFER = (byte) 0x90;
		byte WRITE = 0x02;
		byte LOAD_TX_BUFFER = 0x40;
		byte RTS = (byte) 0x80;
		byte READ_STATUS = (byte) 0xA0;
		byte RX_STATUS = (byte) 0xB0;
		byte BIT_MODIFY = 0x05;
	}
	
	interface Registers {
		byte CANSTAT = 0x0E;
		byte CNF3 =0x28;
		byte CNF2 =0x29;
		byte CNF1 =0x2A;
	}
	
}
