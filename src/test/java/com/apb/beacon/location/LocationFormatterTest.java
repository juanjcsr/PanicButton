package com.apb.beacon.location;

import android.location.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static android.location.LocationManager.NETWORK_PROVIDER;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class LocationFormatterTest {

    @Test
    public void shouldReturnGoogleMapUrlForTheGivenLocation() {
        double latitude = -183.123456;
        double longitude = 78.654321;
        Location location = LocationTestUtil.location(NETWORK_PROVIDER, latitude, longitude, currentTimeMillis(), 10.0f);
        String expectedMessage = ". I'm at http://maps.google.com/maps?q=" + latitude + "," + longitude +" via network";
        LocationFormatter locationFormatter = new LocationFormatter(location);

        assertEquals(expectedMessage, locationFormatter.format());
    }

    @Test
    public void shouldReturnEmptyStringIfTheGivenLocationIsNull() {
        LocationFormatter locationFormatter = new LocationFormatter(null);
        assertEquals("", locationFormatter.format());
    }
}
