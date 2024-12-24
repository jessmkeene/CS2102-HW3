import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GreenHouseProduce extends AbsGreenHouse implements Sensible {

    private List<SuperTempHumidReading> sensorData = new ArrayList<>();
    private SuperTempHumidReading cachedMiddleReading;

    public GreenHouseProduce() {
    }

    @Override
    public void pollSensorData(List<Double> values) {
        double savedDateTime = -1;
        for (int i = 0; i < values.size(); i++) {
            double currentValue = values.get(i);

            double temperature = -999;
            double humidity = -999;
            if(isDate(currentValue)){
                if (i + 2 >= values.size()) {
                    break;
                }
                savedDateTime = currentValue;
                temperature = values.get(i + 1);
                humidity = values.get(i + 2);
                i = i +2;
            }
            else{
                if (i + 1 >= values.size()) {
                    break;
                }
                temperature = values.get(i);
                humidity = values.get(i + 1);
                i = i + 1;
            }
            SuperTempHumidReading reading = new SuperTempHumidReading(temperature, humidity, toDate(savedDateTime));
            sensorData.add(reading);

            cachedMiddleReading = calculateMiddleReading(sensorData);
        }
    }

    @Override
    public TempHumidReading middleReading() {
        return calculateMiddleReading(sensorData);
    }

    @Override
    public SuperTempHumidReading middleReading(double onDate) {
        List<SuperTempHumidReading> filteredData = sensorData.stream()
                .filter(reading -> matchesDate(reading, onDate))
                .collect(Collectors.toList());

        if (filteredData.isEmpty()) {
            return new SuperTempHumidReading(-999, -999);
        }

        return calculateMiddleReading(filteredData);
    }
}