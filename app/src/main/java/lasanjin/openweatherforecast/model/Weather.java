package lasanjin.openweatherforecast.model;

/**
 * Weather contains temperature
 */
public class Weather {
    private Temperature temperature;
    private String description;
    private String icon;
    private byte[] iconData;

    public Weather() {
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setIconData(byte[] iconData) {
        this.iconData = iconData;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public byte[] getIconData() {
        return iconData;
    }
}
