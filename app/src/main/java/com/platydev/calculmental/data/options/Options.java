package com.platydev.calculmental.data.options;

import java.io.Serializable;

public class Options implements Serializable {

    public static final int NIVEAU_MIN = 1;
    public static final int NIVEAU_MAX = 20;
    public static final int TEMPS_MIN = 30;
    public static final int TEMPS_MAX = 150;
    public static final int TEMPS_PAS =30;

    public static final Options DEFAULT = new Options(1, 90, Mode.Zen);

    private int niveau;
    private int temps;
    private Mode mode;

    public Options(int niveau, int temps, Mode mode) {
        this.niveau = niveau;
        this.temps = temps;
        this.mode = mode;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
