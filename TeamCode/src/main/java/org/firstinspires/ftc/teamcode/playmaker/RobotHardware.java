package org.firstinspires.ftc.teamcode.playmaker;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.util.OmniDrive;

public abstract class RobotHardware {

    public HardwareMap hardwareMap;
    public Telemetry telemetry;

    public static final String vulforiaKey = "ATYXw6b/////AAAAGbsBoJKrv00tn+OIBZOySi93f157TAsX4H3f444TrvUXKWFiNjsiBUjhwGrShLYay8wFSrlf+nRtoS+xnZJ3IApbJe2W0NSLKz21/B3/IpstUZGH29ZD/ogg3ZixfNyUGyb+F5gy5LzvGTdRhGLwy0d4z2i6QauSDPYHU4bBnhmehHBFMPkA6aP94fqOfa4qJGKBCCrn1EcH+c5TXD2EP21vciteCYktsfBedAnveiDGR7yLbTPr5kdfLvem0iyH8ESxhOsr90wGnIGWOQJa83eilaVbmLHtWkQx/hT/CnNTglJXb6TGRuDEwv/Zs+zdswp9dvCHZL5Qq1pT4y+LNUZZfhtmLlYXNifiEn7HnM5f";
    public VuforiaLocalizer vuforia;
    public VuforiaTrackables relicTrackables;
    public VuforiaTrackable relicTemplate;
    public TFObjectDetector tfod;
    public OmniDrive omniDrive;

    public RobotHardware(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    public abstract void initializeHardware();

    public void initializeDevice(Class<HardwareDevice> deviceClass, String name) {
        try {
            this.hardwareMap.get(deviceClass, name);
        } catch (Exception e) {
            telemetry.addData("Error:", String.format("Device \"%s\" cannot be found.", name));
        }
    }

    public void hardware_loop() {}

}
