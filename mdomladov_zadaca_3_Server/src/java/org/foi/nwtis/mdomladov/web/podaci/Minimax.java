/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.web.podaci;

/**
 *
 * @author Zeus
 */
public class Minimax {

    private double min;
    private double max;

    public Minimax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public Minimax() {
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    
}
