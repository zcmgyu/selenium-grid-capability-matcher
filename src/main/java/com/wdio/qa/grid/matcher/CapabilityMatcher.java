package com.wdio.qa.grid.matcher;

import java.util.Map;
import java.util.Objects;
import org.openqa.grid.internal.utils.DefaultCapabilityMatcher;

public class CapabilityMatcher extends DefaultCapabilityMatcher {

  final String DEVICE_NAME = "deviceName";
  final String APPIUM_DEVICE_NAME = "appium:deviceName";
  final String UDID = "udid";
  final String APPIUM_UDID = "appium:udid";

  @Override
  public boolean matches(Map<String, Object> nodeCapability,
      Map<String, Object> requestedCapability) {
    System.out.println("Node capabilities:\n" + nodeCapability);
    System.out.println("Requested capabilities:\n" + requestedCapability);

    // If the request does not have the special capability,
    // we return what the DefaultCapabilityMatcher returns
    if (nodeCapability.containsKey(UDID) && requestedCapability.containsKey(APPIUM_UDID)) {
      return matcher(nodeCapability, requestedCapability, UDID, APPIUM_UDID);
    } else if (nodeCapability.containsKey(UDID) && requestedCapability.containsKey(UDID)) {
      return matcher(nodeCapability, requestedCapability, UDID, UDID);
    } else if (nodeCapability.containsKey(DEVICE_NAME) && requestedCapability.containsKey(APPIUM_DEVICE_NAME)) {
      return matcher(nodeCapability, requestedCapability, DEVICE_NAME, APPIUM_DEVICE_NAME);
    } else if (nodeCapability.containsKey(DEVICE_NAME) && requestedCapability.containsKey(DEVICE_NAME)) {
      return matcher(nodeCapability, requestedCapability, DEVICE_NAME, DEVICE_NAME);
    } else {
      System.out.println("Basic matcher");
      return super.matches(nodeCapability, requestedCapability);
    }
  }

  private boolean matcher(Map<String, Object> nodeCapability,
      Map<String, Object> requestedCapability, String nCap, String rCap) {
    if (!nodeCapability.containsKey(nCap)) {
      return false;
    }

    String expected = (String) requestedCapability.get(rCap);
    String actual = (String) nodeCapability.get(nCap);

    boolean result = Objects.equals(expected, actual);
    System.out.println(String
        .format("Is %s matching (A==E): '%s'=='%s'. Result: %s", nCap, actual, expected,
            result));

    return result;
  }
}
