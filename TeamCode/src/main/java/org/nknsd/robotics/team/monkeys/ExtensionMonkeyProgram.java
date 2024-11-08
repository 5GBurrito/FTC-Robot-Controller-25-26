package org.nknsd.robotics.team.monkeys;

import org.nknsd.robotics.framework.NKNComponent;
import org.nknsd.robotics.framework.NKNProgram;
import org.nknsd.robotics.team.components.ExtensionHandler;
import org.nknsd.robotics.team.components.PotentiometerHandler;
import org.nknsd.robotics.team.components.RotationHandler;
import org.nknsd.robotics.team.components.testfiles.ExtensionMonkey;

import java.util.List;

public class ExtensionMonkeyProgram extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        // Create pot and arm rotator so that the extensionHandler can be linked to them
        PotentiometerHandler potentiometerHandler = new PotentiometerHandler("armPot");
        components.add(potentiometerHandler);

        RotationHandler rotationHandler = new RotationHandler ();
        components.add(rotationHandler);

        // Actual components we actually use
        ExtensionHandler extensionHandler = new ExtensionHandler();
        components.add(extensionHandler);
        telemetryEnabled.add(extensionHandler);

        rotationHandler.link(potentiometerHandler, extensionHandler);
        extensionHandler.link(rotationHandler);

        ExtensionMonkey extensionMonkey = new ExtensionMonkey();
        extensionMonkey.link(extensionHandler);
        components.add(extensionMonkey);
        telemetryEnabled.add(extensionMonkey);
    }
}
