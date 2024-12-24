import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class Examples{
    LinkedList<Double> sameMonth = new LinkedList<>();
    LinkedList<Double> differentDate = new LinkedList<>();
    LinkedList<Double> emptyDate = new LinkedList<>();

    public Examples() {
        sameMonth.addAll(List.of(20231106010101.0, 45.5, 34.0, 46.0, 40.0, 20231130020202.0));
        differentDate.addAll(List.of(20210101010101.0, 20.0, 15.1, 22.2, 15.0, 20220301010101.0, 23.0, 19.0, 22.1, 22.1));
        emptyDate.addAll(List.of());
    }

    @Test
    public void testSuperTempHumidityReadingEquals1() {
        assertEquals(new SuperTempHumidReading(), new SuperTempHumidReading());
    }

    @Test
    public void testSuperTempHumidityReadingEquals2() {
        assertEquals(new SuperTempHumidReading(30.0, 33.3), new SuperTempHumidReading(30.0, 33.3));
    }

    @Test
    public void testSuperTempHumidityReadingToString() {
        assertEquals("{80.0F;33.3%}",
                new SuperTempHumidReading(80.0, 33.3).toString());
    }

    @Test
    public void testSuperTempHumidityReadingToString2() {
        assertEquals("{Err;33.3%}",
                new SuperTempHumidReading(-999, 33.3).toString());
    }

    //create more tostring for other senarios
    @Test
    public void testSuperTempHumidityReadingCopyConstructor() {
        SuperTempHumidReading sthr = new SuperTempHumidReading(55.5, 33.3);
        assertEquals(sthr, new SuperTempHumidReading(sthr));
        assertNotSame(sthr, new SuperTempHumidReading(sthr));
    }

    /* @Test
     public void testGreenHouseNurserySameMonth1(){
         Sensible ghn = new GreenHouseNursery();
         ghn.pollSensorData(sameMonth);
         //TEMP: 45.5, 46.6, 22.2, 35.5, 32.2
         //HUMIDITY: 34.0, 40.0, 20.0, 30.0, 31.0
         assertEquals(new SuperTempHumidReading(35.5, 31.0),
                 ghn.middleReading());
     } why is this failing?!?!?!?!?
     */
    @Test
    public void testGreenHouseNurserySameMonth2() {
        Sensible ghn = new GreenHouseNursery();
        ghn.pollSensorData(sameMonth);
        //TEMP: 45.5, 46.6 --> 2 values --> 2/2 = 1 --> value at index 1
        //HUMIDITY: 45.5, 46.6 --> 2 values --> 2/2 = 1 --> value at index 1
        assertEquals(new SuperTempHumidReading(46.0, 40.0),
                ghn.middleReading(20231106));
    }

    @Test
    public void testGreenHouseProducePollSensorData() {
        Sensible ghp = new GreenHouseProduce();
        ghp.pollSensorData(sameMonth);
        assertEquals(new SuperTempHumidReading(46.0, 40.0),
                ghp.middleReading(20231106));
    }

    @Test
    public void testGreenHouseProductPollSensorData2() {
        Sensible ghp = new GreenHouseProduce();
        ghp.pollSensorData(differentDate);
        assertEquals(new SuperTempHumidReading(22.2, 15.1),
                ghp.middleReading(20210101));
    }

    @Test
    public void testGreenHouseProductPollSensorData3() {
        Sensible ghp = new GreenHouseProduce();
        ghp.pollSensorData(emptyDate);
        assertEquals(new SuperTempHumidReading(),
                ghp.middleReading());
    }

    @Test
    public void testTimingNurseryProduceSensor1() {
        GreenHouseNursery nursery = new GreenHouseNursery();
        GreenHouseProduce produce = new GreenHouseProduce();
        long time1 = System.nanoTime();
        nursery.pollSensorData(sameMonth);
        long time2 = System.nanoTime();
        produce.pollSensorData(sameMonth);
        long time3 = System.nanoTime();
        System.out.println(String.format("nursery.pollSensorData(data); : produce.pollSensorData(data) :: %s : %s", time2 - time1, time3 - time2));
        assertTrue(time2 - time1 < time3 - time2);
    }

    @Test
    public void testTimingProduceNurserySensor2() {
        GreenHouseNursery nursery = new GreenHouseNursery();
        GreenHouseProduce produce = new GreenHouseProduce();
        long time1 = System.nanoTime();
        produce.pollSensorData(sameMonth);
        long time2 = System.nanoTime();
        nursery.pollSensorData(sameMonth);
        long time3 = System.nanoTime();
        System.out.println(String.format("nursery.pollSensorData(data); : produce.pollSensorData(data) :: %s : %s", time2 - time1, time3 - time2));
        assertFalse(time2 - time1 < time3 - time2);
    }

    @Test
    public void testTimingNurseryProduceReading1() {
        GreenHouseNursery nursery = new GreenHouseNursery();
        GreenHouseProduce produce = new GreenHouseProduce();
        long time1 = System.nanoTime();
        nursery.middleReading();
        long time2 = System.nanoTime();
        produce.middleReading();
        long time3 = System.nanoTime();
        System.out.println(String.format("nursery.middleReading(data); : produce.middleReading(data) :: %s : %s", time2 - time1, time3 - time2));
        assertFalse(time2 - time1 < time3 - time2);
    }

    @Test
    public void testTimingNurseryProduceReading2() {
        GreenHouseNursery nursery = new GreenHouseNursery();
        GreenHouseProduce produce = new GreenHouseProduce();
        long time1 = System.nanoTime();
        produce.middleReading();
        long time2 = System.nanoTime();
        nursery.middleReading();
        long time3 = System.nanoTime();
        System.out.println(String.format("nursery.middleReading(data); : produce.middleReading(data) :: %s : %s", time2 - time1, time3 - time2));
        assertTrue(time2 - time1 < time3 - time2);
    }

    @Test
    public void testMiddleReadingsProduce() {
        GreenHouseProduce prod = new GreenHouseProduce();
        List<Double> value = List.of(20231106010101.0, 45.5, 34.0, 46.6, 40.0, 20231130020202.0, 22.2, 20.0, 35.5, 30.0, -999.0, 31.0, 32.2, -999.0);
        prod.pollSensorData(value);
        double onDate = 20231106010101.0;
        assertEquals(new SuperTempHumidReading(-999, -999), prod.middleReading(onDate));
    }

    @Test
    public void testDefaultReading() {
        SuperTempHumidReading sthr = new SuperTempHumidReading();
        assertEquals("{Err;Err}", sthr.toString());
    }
    @Test
    public void testMediumReadingsNursery() {
        GreenHouseNursery green = new GreenHouseNursery();
        List<Double> value = List.of(20231106010101.0, 45.5, 34.0, 46.6, 40.0, 20231130020202.0, 22.2, 20.0, 35.5, 30.0, -999.0, 31.0, 32.2, -999.0);
        green.pollSensorData(value);
        assertEquals(new SuperTempHumidReading(35.5, 31.0), green.middleReading());
    }
}

