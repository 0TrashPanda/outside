package outside;

public enum Modifiers {
	SHARP("sharp", "damage", +1),
	JAGGED("Jagged", "damage", +2, "durabilety", 	0.75),
	HEAVY("heavy", "damage", +3, "attack speed", -2),
	SWIFT("Swift", "attack speed", +1),
	QUICK("Quickened", "attack speed", +2, "damage", -1),
	RAPID("Rapid", "attack speed", +3, "damage", -2),
	LIGHT("light", "movement speed", +20),
	CRIIT("crit", "crit damage", +2, "non crit damage", -1);

	public final String name;
	public final String effect1;
	public final String effect2;
	public final double value1;
	public final double value2;

	private Modifiers(String name, String effect1, double value1) {
		this(name, effect1, value1, null, 0);
	}

	Modifiers(String name, String effect1, double value1, String effect2, double value2) {
		this.name = name;
		this.effect1 = effect1;
		this.effect2 = effect2;
		this.value1 = value1;
		this.value2 = value2;
	}

}
