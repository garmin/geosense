/**
 * TestGeoSense.java
 * 
 * Copyright (c) 2013 eBay Software Foundation
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.redlaser.geosense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

/**
 * @author Frank D Russo
 */
@SuppressWarnings("static-method")
public class TestGeoSense {
	static {
		// force initialization once
		GeoSense.init();
	}

	@Test
	public void testGetTimeZone() {
		TimeZone tz1 = GeoSense.getTimeZone(37.29390,-121.91413);
		assertNotNull(tz1);
		assertEquals("America/Los_Angeles", tz1.getID());
		
		TimeZone tz2 = GeoSense.getTimeZone(0.0,50.0); // Etc/GMT-3
		assertNotNull(tz2);
		assertEquals("Etc/GMT-3", tz2.getID());
		
		TimeZone tz3 = GeoSense.getTimeZone(0.0,-20.0); // Etc/GMT+1
		assertNotNull(tz3);
		assertEquals("Etc/GMT+1", tz3.getID());
		
		TimeZone tz4 = GeoSense.getTimeZone(38.7436214, -9.195223); // Lisbon, Portugal
		assertNotNull(tz4);
		assertEquals("Europe/Lisbon", tz4.getID());

                TimeZone tz5 = GeoSense.getTimeZone(38.7436214, -9.0);
                assertNotNull(tz5);
                assertEquals("Europe/Lisbon", tz5.getID());
	}

	@Test
	public void testGetTimeZonesByCountry() {
		List<TimeZone> usTZs = GeoSense.getTimeZones("US");
		assertTrue(usTZs.contains(TimeZone.getTimeZone("America/New_York")));
		assertTrue(usTZs.contains(TimeZone.getTimeZone("America/Denver")));
		
		List<TimeZone> deTZs = GeoSense.getTimeZones("DE");
		assertTrue(deTZs.contains(TimeZone.getTimeZone("Europe/Berlin")));
		
		TimeZone deTZ = GeoSense.getATimeZone("DE");
		assertNotNull(deTZ);
		assertEquals("Europe/Berlin", deTZ.getID());
	}

	@Test
	public void testGetACountryByTimezone() {
		String country = GeoSense.getACountry(TimeZone.getTimeZone("Asia/Shanghai"));
		assertEquals("CN", country);
	}
}
