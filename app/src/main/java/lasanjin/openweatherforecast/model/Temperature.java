package lasanjin.openweatherforecast.model;

public class Temperature {
    private double temp;
    private double maxTemp;
    private double minTemp;

    public Temperature() {
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getTemp() {
        return temp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }
}
