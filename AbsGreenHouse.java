import java.util.List;
import java.util.stream.Collectors;

/**
 * An abstract superclass to provide template methods for performance specific subclasses.
 */
public abstract class AbsGreenHouse {
    /**
     * Assume a sensor value is a date if it is greater than January 1, 1970.
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
    public boolean isDate(double sensorDatum){
        return sensorDatum > 19700101.0;
    }

    /**
     * Assume a sensor value is a date if it is greater than January 1, 1970 00:00:00 represented as a double.
     * @param sensorDatum the datum which may be a date, datetime, temperature, or humidity
     * @return true if it is a formatted date number
     */
    public boolean isDateTime(double sensorDatum){
        return sensorDatum > 19700101000000.0;
    }

    /**
     * Converts the double date time format to just the date part by dividing and rounding.
     * @param dateTime YYYYMMDDhhmmss.0
     * @return YYYYMMDD.0
     */
    public double toDate(double dateTime){
        return Math.floor(dateTime / 1000000.0); // convert YYYYMMDDhhmmss -> YYYYMMDD
    }

    /**
     * Compares two YYYYMMDD.0 for equality within some error tolerance (0.001).
     * @param date1 one YYYYMMDD.0
     * @param date2 another YYYYMMDD.0
     * @return true if they are within some error tolerance (0.001) of each other
     */
    public boolean sameDate(double date1, double date2){
        return Math.abs(date1 - date2) < 0.001;
    }


    /**
     * Calculates the middle reading of temperature and humidity from sensor data.
     * @param sensorData a list of sensor readings containing temperature and humidity
     * @return OldSuperOldTempHumidReading object representing the middle temperature and humidity
     */
    protected SuperTempHumidReading calculateMiddleReading(List<SuperTempHumidReading> sensorData) {
        List<Double> temperatures = sensorData.stream()
                .filter(reading -> reading.temperature != -999)
                .map(reading -> reading.temperature)
                .sorted()
                .collect(Collectors.toList());

        List<Double> humidities = sensorData.stream()
                .filter(reading -> reading.humidity != -999)
                .map(reading -> reading.humidity)
                .sorted()
                .collect(Collectors.toList());

        double middleTemperature = getMiddleValue(temperatures);
        double middleHumidity = getMiddleValue(humidities);

        SuperTempHumidReading result = new SuperTempHumidReading(middleTemperature, middleHumidity);
        return result;
    }

    /**
     * Gets the middle value from a list of doubles.
     * @param values list of double values
     * @return middle value from the list or -999 if the list is empty
     */
    protected double getMiddleValue(List<Double> values) {
        if (values.isEmpty()) return -999;
        int middleIndex = values.size() / 2;
        return values.get(middleIndex);
    }

    /**
     * Checks if the reading's date matches the specified date.
     * @param reading OldSuperOldTempHumidReading object containing date, temperature, and humidity
     * @param onDate specified date in YYYYMMDD.0 format
     * @return true if the reading's date matches the specified date
     */
    protected boolean matchesDate(SuperTempHumidReading reading, double onDate) {
        double readingDate = reading.getDate();
        if (isDate(onDate) && readingDate == onDate) {
            return true;
        } else {
            return false;
        }
    }
}