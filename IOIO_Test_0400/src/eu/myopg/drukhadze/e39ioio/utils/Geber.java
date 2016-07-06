package eu.myopg.drukhadze.e39ioio.utils;

public enum Geber {
	GEBER1(46),
	GEBER2(45),
	GEBER3(44),
	GEBER4(43),
	GEBER5(42),
	GEBER6(41),
	GEBER7(40),
	GEBER8(39),
	GEBER9(38),
	GEBER10(37),
	GEBER11(36),
	GEBER12(35),
	GEBER13(34),
	GEBER14(33),
	GEBER15(32),
	GEBER16(31);
	
	int pin = -1;
	Geber(int pin)
	{
		this.pin = pin;
	}
	
	public int getPin()
	{
		return pin;
	}
}
