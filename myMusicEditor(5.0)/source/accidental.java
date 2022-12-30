class accidental extends addons{
    note n;
    boolean isFlat;

    public accidental(String path, int myX, int myY) {
        super(path, myX, myY);
        if (path == "flat.png") {
            isFlat = true;
        } else {
            isFlat = false;
        }
        n = null;
    }

    public note getNote() {
        return this.n;
    }

    public void updateStaff() {
        if (this.m != this.n.m) {
            this.m = this.n.m;
        }
    }
}