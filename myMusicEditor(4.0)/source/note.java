public class note extends addons {
    public void setDuration(String input) {
        this.duration = input;
    }

    @Override
    public void setCircleCenter() {
        if (this.duration.equals("Whole")) {
            this.xoffset = 10;
            this.yoffset = 6;
        } else if (this.duration.equals("Half")) {
            this.xoffset = 15;
            this.yoffset = 34;
        } else if (this.duration.equals("Quarter")) {
            this.xoffset = 7;
            this.yoffset = 35;
        } else if (this.duration.equals("Eighth")) {
            this.xoffset = 15;
            this.yoffset = 36;
        } else {
            this.xoffset = 6;
            this.yoffset = 35;
        }
        this.circleCenterY = this.y + this.yoffset;
        this.circleCenterX = this.x + this.xoffset;
    }

    public note(String path, int myX, int myY) {
        super(path, myX, myY);
        this.pitch = "Not Set.";
        this.setCircleCenter();
    }

    public void updateAccidental() {
        if ((this.pitch.endsWith("b")) || (this.pitch.endsWith("#"))) {
            this.pitch = this.pitch.substring(0,1);
        }
    }

    public void setX(int i){
        this.x = i;
        if (a != null) {
            this.a.x = i-10;
        }
    }
    public void setY(int i){
        this.y = i;
        if (a != null) {
            this.a.y = this.circleCenterY-this.a.height;
        }
    }
    
    public void setAccidental(accidental a) {
        if (this.a != null) {
            this.a = null;
        }
        
        this.a = a;
        this.a.x = this.x - 10;
        this.a.y = this.circleCenterY - this.a.height;
        this.a.n = this;
        
        if (this.a.isFlat) {
            if (!(this.pitch.endsWith("b"))) {
                this.pitch = this.pitch + "b";
            }
        } else {
            if (!(this.pitch.endsWith("#"))) {
                this.pitch = this.pitch + "#";
            }
        }
    }
}
