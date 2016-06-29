package eu.myopg.drukhadze.e39ioio.utils;

public enum NTCSensor {
	NTC1000(25, 1000, 100, 93.7f),
	NTC2000(25, 2000, 100, 135.79f),
	NTC2252(25, 2252, 100, 152.83f),
	NTC3000(25, 3000, 100, 203.59f),
	NTC5000(25, 5000, 100, 339.32f),
	NTC10000(25, 10000, 100, 678.63f),
	
	BMWE46Getriebe(25, 2252, 100, 152.83f),
	BMWE36Getriebe(25, 2252, 100, 152.83f),
	
	VDO120(25, 2252, 100, 152.83f),
	VDO150(25, 2252, 100, 152.83f),
	RAID_HP(25, 2252, 100, 152.83f);
	
	
	
	float Tlow = 25;
	float Rlow = 0;
	float Thigh = 100;
	float Rhigh = 0;
	
	private NTCSensor(float Tlow, float Rlow, float Thigh, float Rhigh) {
		this.Tlow = Tlow;
		this.Rlow = Rlow;
		this.Thigh = Thigh;
		this.Rhigh = Rhigh;
	}

	public float getTlow() {
		return Tlow;
	}

	public float getRlow() {
		return Rlow;
	}

	public float getThigh() {
		return Thigh;
	}

	public float getRhigh() {
		return Rhigh;
	}
	
}
