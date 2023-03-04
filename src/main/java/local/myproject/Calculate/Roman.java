package local.myproject.Calculate;

public enum Roman {
	I (1), IV (4), V (5), IX (9), X (10), XL (40), L (50), XC (90), C (100), CD (400), D (500), CM (900), M (1000);

    private final int value;
    
    private Roman(int value) {
        this.value = value;
    }
    public int toInt() {
        return value;
    }
    
    public boolean shouldCombine(Roman next) {
        return this.value < next.value;
    }
    public int toInt(Roman next) {
        return next.value - this.value;
    }
}
