package de.mavecrit.pawars.util;

public class CountdownCalculator {
 
        private double currperc;
        private int bars;
        private int filledbars;
        private int restbars;
       
        public CountdownCalculator(double currperc, int bars) {
                this.bars = bars;
                this.currperc = currperc;
                this.filledbars = (int) (currperc * bars);
                this.restbars = bars - filledbars;
        }
        public CountdownCalculator(int secs, int currsec, int bars) {
                this(toPercent(secs, currsec), bars);
        }
       
        public double getPercentFilled() {
                return currperc;
        }
        public int getBarCount() {
                return bars;
        }
        public int getFilledBars() {
                return filledbars;
        }
        public int getRestBars() {
                return restbars;
        }
       
        @Override
        public String toString() {
                return "CountdownCalculator [currperc=" + currperc + ", bars=" + bars
                                + ", filledbars=" + filledbars + "]";
        }
        private static double toPercent(int secs, int currsecs) {
                double sec = Double.valueOf(secs);
                double curr = Double.valueOf(currsecs);
               
                return Double.valueOf((int) ((curr / sec) * 10)) / 10;
        }
}