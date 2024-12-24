public class SuperTempHumidReading extends TempHumidReading {

    private double date;


    /**
     * Default constructor initializing temperature, humidity, and date with default values.
     */
    public SuperTempHumidReading() {
        super(-999, -999);
        this.date = -1;
    }


    /**
     * Constructor initializing temperature and humidity with specified values and date with a default value.
     * @param temperature the temperature value
     * @param humidity the humidity value
     */
    public SuperTempHumidReading(double temperature, double humidity) {
        super(temperature, humidity);
        this.date = -1;
    }

    /**
     * Constructor initializing temperature, humidity, and date with specified values.
     * @param temperature the temperature value
     * @param humidity the humidity value
     * @param date the date value
     */
    public SuperTempHumidReading(double temperature, double humidity, double date) {
        super(temperature, humidity);
        this.date = date;
    }

    /**
     * Constructor initializing from another OldTempHumidReading object and setting the date to a default value.
     * @param other another OldTempHumidReading object
     */
    public SuperTempHumidReading(TempHumidReading other) {
        super(other.temperature, other.humidity);
        this.date = -1;
    }


    /**
     * Checks equality with another OldSuperOldTempHumidReading object based on temperature and humidity values.
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SuperTempHumidReading that = (SuperTempHumidReading) obj;
        return Math.abs(temperature - that.temperature) < 0.001 &&
                Math.abs(humidity - that.humidity) < 0.001;
    }

    /**
     * Generates a string representation of the OldSuperOldTempHumidReading object.
     * @return the string representation of the object
     */
    @Override
    public String toString() {
        String tempStr = (temperature == -999) ? "Err" : String.format("%,.1fF", temperature);
        String humidStr = (humidity == -999) ? "Err" : String.format("%,.1f%%", humidity);
        return "{" + tempStr + ";" + humidStr + "}";
    }


    /**
     * Retrieves the date value.
     * @return the date value
     */
    public double getDate() {
        return date;
    }

    /**
     * Sets the date value.
     * @param date the date value to set
     */
    public void setDate(double date) {
        this.date = date;
    }
}