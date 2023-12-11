package outside;

public enum Modifiers {
	SHARP("sharp", 0xffe070, "damage", 1),
	JAGGED("Jagged", 0xffd747, "damage", 2, "durabilety", 	0.75f),
	HEAVY("heavy", 0xfcc500, "damage", 3, "attack speed", -2),
	SWIFT("Swift", 0x8ce8c2, "attack speed", 1),
	QUICK("Quickened", 0x5ddfa9, "attack speed", 2, "damage", -1),
	RAPID("Rapid", 0x27c482, "attack speed", 3, "damage", -2),
	LIGHT("light", 0x27b3c3, "movement speed", 20),
	CRIIT("crit", 0xa586ad, "crit damage", 2, "non crit damage", -1);

	public final String name;
	public final int hexColor;
	public final String effect1;
	public final String effect2;
	public final float value1;
	public final float value2;

	private Modifiers(String name,  int hexColor, String effect1, float value1) {
		this(name, hexColor, effect1, value1, "", 0);
	}

	Modifiers(String name,  int hexColor, String effect1, float value1, String effect2, float value2) {
		this.name = name;
		this.hexColor = hexColor;
		this.effect1 = effect1;
		this.effect2 = effect2;
		this.value1 = value1;
		this.value2 = value2;
	}

}
