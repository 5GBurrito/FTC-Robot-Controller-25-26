package org.firstinspires.ftc.teamcode.bots;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorEx;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.HardwareMap;
        import com.qualcomm.robotcore.util.ElapsedTime;

        import java.util.Timer;
        import java.util.TimerTask;

public class PivotBot extends OdometryBot {

    ElapsedTime timer = new ElapsedTime();


    // Pivot motor constants
    public final int maximumPivotPos = 1300;
    public final int minumimPivotPos = -100;
    private final int searchPivotPos = 250; // tested
    private final int pickupSpecimenPivotPos = 130; // tested
    private final int pickupSamplePivotPos = 60; // tested
    private final int pickupUpPivotPos = 400;
    public final int specimenHighPivotPos = 1196;
    public final int specimenLowPivotPos = 750;
    public final int highBasketPivotPos = 1274;
    public final int lowBasketPivotPos = 1000;
    public boolean pivotOutOfRange = false;
    public int pivotTarget = 300;
    public double pivotPower = 0.7;
    // Pivot timmer
    private Timer pivotTimer = new Timer();
    private TimerTask pivotTimerTask;

    // Slide motor constants
    public final int maximumSlidePos = 2400;
    public final int minimumSlidePos = 170;
    public final int searchSlidePos = 350;
    public final int specimenHighSlidePos = 1100;
    public final int specimenLowSlidePos = 800;
    public final int highBasketSlidePos = 2400;
    public final int lowBasketSlidePos = 800;
    public final double slideCMCoefficient = 60.0;

    public int slideTarget = 110;


    public DcMotorEx pivotMotor = null;
    public DcMotorEx slideMotor = null;

    @Override
    public void init(HardwareMap ahwMap) {
        super.init(ahwMap);
        pivotMotor = hwMap.get(DcMotorEx.class, "Pivot Motor");
        pivotMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        pivotMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivotMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pivotMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pivotMotor.setPower(pivotPower);

        slideMotor = hwMap.get(DcMotorEx.class, "slide");
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setPower(0);

        // TODO
        pivotMotor.setTargetPosition(pivotTarget);
        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pivotPower = 0.5;
    }

    public PivotBot(LinearOpMode opMode) {
        super(opMode);
    }

    public int getSlidePosition() {
        return slideMotor.getCurrentPosition();
    }

    public int getPivotPosition() {
        return pivotMotor.getCurrentPosition();
    }

    protected void onTick() {

        super.onTick();

        if (pivotTarget > minumimPivotPos - 100 && pivotTarget < maximumPivotPos + 100){

            pivotOutOfRange = false;

            pivotMotor.setTargetPosition(pivotTarget);
            pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // TODO : PID control for the pivot motor
            pivotMotor.setPower(0.6);

        } else {

            pivotOutOfRange = true;
            pivotMotor.setPower(0);

        }
        if (slideTarget > 0 && slideTarget < maximumSlidePos + 100){

            slideMotor.setTargetPosition(slideTarget);
            slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // TODO : PID control for the slide motor
            slideMotor.setPower(0.5);

        } else {

            slideMotor.setPower(0);

        }
    }
    public void slideByDelta(int delta){
        slideTarget += delta;
    }
    public void slideControl(boolean up, boolean down) {
        if (up) {
            if (slideMotor.getCurrentPosition() < maximumSlidePos) {
                slideTarget = slideMotor.getCurrentPosition() + ((maximumSlidePos - slideMotor.getCurrentPosition()) / 10);
                slideMotor.setTargetPosition(slideTarget);
                slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            }
        }
        if (down) {
            if (slideMotor.getCurrentPosition() > minimumSlidePos) {
                slideTarget = slideMotor.getCurrentPosition() - (slideMotor.getCurrentPosition() / 10);
                slideMotor.setTargetPosition(slideTarget);
                slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            }
        }

        //make pivot same
    }

    public void pivotControl(boolean up, boolean down){
        if (up) {
            if (pivotMotor.getCurrentPosition() < maximumPivotPos) {
                pivotTarget = pivotMotor.getCurrentPosition() + ((maximumPivotPos - pivotMotor.getCurrentPosition()) / 10);
                pivotMotor.setTargetPosition(pivotTarget);
                pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
        if (down) {
            if (pivotMotor.getCurrentPosition() > minumimPivotPos) {
                pivotTarget = pivotMotor.getCurrentPosition() - (pivotMotor.getCurrentPosition() / 10);
                pivotMotor.setTargetPosition(pivotTarget);
                pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
    }

    public void pivotTo(int pos){
        pivotTarget = pos;
    }

    public void pivotToSearchPos(){
        pivotTarget = searchPivotPos;
    }
//    public void pivotToPickupPos(boolean isSpecimen){
//        if (isSpecimen){
//            pivotTarget = pickupSpecimenPivotPos;
//        } else {
//            pivotTarget = pickupSamplePivotPos;
//        }
//    }
    public void pivotToPickupPosSpecimen(){
        pivotTarget = pickupSpecimenPivotPos;
    }
    public void pivotToPickupPosSample(){
        pivotTarget = pickupSamplePivotPos;
    }
    public void pivotToPickupUpPos(){
        //pivotTarget = pickupUpPivotPos;
        relatePivotToSlide();
    }
    public void pivotToSpecimenHighPos(){
        pivotTarget = specimenHighPivotPos;
    }
    public void pivotToSpecimenLowPos(){
        pivotTarget = specimenLowPivotPos;
    }
    public void pivotToHighBasketPos(){
        pivotTarget = highBasketPivotPos;
    }
    public void pivotToLowBasketPos(){
        pivotTarget = lowBasketPivotPos;
    }
    public void pivotByDelta(int delta){
        pivotTarget += delta;
    }

    public void cancelPivotTimer(){
        pivotTimer.cancel();
    }

    public void moveSlide(int pos){
        slideTarget = pos;
    }

    public void moveSlideToSearchPos(){
        slideTarget = searchSlidePos;
    }
    public void moveSlideToHighSpecimenPos(){
        slideTarget = specimenHighSlidePos;
    }
    public void moveSlideToLowSpecimenPos(){
        slideTarget = specimenLowSlidePos;
    }
    public void moveSlideByDelta(int delta){
        slideTarget += delta;
    }

    /**
     * Move the slide by a delta value in CM
     * @param delta
     */
    public void moveSlideByDelta(double delta){
        slideTarget += Math.round(delta * slideCMCoefficient);
    }
    public void moveSlideToHighBucketPos(){
        slideTarget = highBasketSlidePos;
    }
    public void moveSlideToLowBucketPos(){
        slideTarget = lowBasketSlidePos;
    }

    public void relatePivotToSlide(){
        pivotTarget = Math.round((slideMotor.getCurrentPosition() / -23) + 245);
        pivotMotor.setTargetPosition(pivotTarget);
        pivotMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}