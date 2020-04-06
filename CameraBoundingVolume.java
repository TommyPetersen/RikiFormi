package RikiFormi;

import java.util.*;
import RikiFormi.Utils.*;

public class CameraBoundingVolume{

    private Line2D FrontBoundingPlane = null;
    private Line2D BackBoundingPlane = null;
    private Line2D LeftBoundingPlane = null;
    private Line2D RightBoundingPlane = null;
    private Line2D TopBoundingPlane = null;
    private Line2D BottomBoundingPlane = null;

    public CameraBoundingVolume(double frontPlaneValue,
				double backPlaneValue,
				double projectionPlaneValue,
				double projectionWindowWidth,
				double projectionWindowHeight
				) throws Exception{

	if (frontPlaneValue <= 0.0){
	    throw new Exception("The value for frontPlaneValue must be positive");
	}
	
	double rightPlaneValueOuter = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
										  new Point2D(projectionWindowWidth / 2.0, projectionPlaneValue)),
								       new Line2D(new Point2D(0.0, backPlaneValue),
										  new Point2D(1.0, backPlaneValue)));
	double leftPlaneValueOuter = -rightPlaneValueOuter;
	double rightPlaneValueInner = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
										  new Point2D(projectionWindowWidth / 2.0, projectionPlaneValue)),
								       new Line2D(new Point2D(0.0, frontPlaneValue),
										  new Point2D(1.0, frontPlaneValue)));
	double leftPlaneValueInner = -rightPlaneValueInner;
	double topPlaneValueOuter = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
										new Point2D(projectionWindowHeight / 2.0, projectionPlaneValue)),
								     new Line2D(new Point2D(0.0, backPlaneValue),
										new Point2D(1.0, backPlaneValue)));
	double bottomPlaneValueOuter = -topPlaneValueOuter;
	double topPlaneValueInner = Util3d.valueForLine2DCrossLine2D(new Line2D(new Point2D(0.0, 0.0),
										new Point2D(projectionWindowHeight / 2.0, projectionPlaneValue)),
								     new Line2D(new Point2D(0.0, frontPlaneValue),
										new Point2D(1.0, frontPlaneValue)));
	double bottomPlaneValueInner = -topPlaneValueInner;

	FrontBoundingPlane = new Line2D(new Point2D(0.0, frontPlaneValue), new Point2D(1.0, frontPlaneValue));
	BackBoundingPlane = new Line2D(new Point2D(0.0, backPlaneValue), new Point2D(1.0, backPlaneValue));
	RightBoundingPlane = new Line2D(new Point2D(rightPlaneValueInner, frontPlaneValue), new Point2D(rightPlaneValueOuter, backPlaneValue));
	LeftBoundingPlane = new Line2D(new Point2D(leftPlaneValueInner, frontPlaneValue), new Point2D(leftPlaneValueOuter, backPlaneValue));
	TopBoundingPlane = new Line2D(new Point2D(topPlaneValueInner, frontPlaneValue), new Point2D(topPlaneValueOuter, backPlaneValue));
	BottomBoundingPlane = new Line2D(new Point2D(bottomPlaneValueInner, frontPlaneValue), new Point2D(bottomPlaneValueOuter, backPlaneValue));
    }

    public Line2D getBoundary(BoundingPlaneName planeName) throws Exception{
	switch (planeName){
	case FRONT:
	    return FrontBoundingPlane;
	case BACK:
	    return BackBoundingPlane;
	case RIGHT:
	    return RightBoundingPlane;
	case LEFT:
	    return LeftBoundingPlane;
	case TOP:
	    return TopBoundingPlane;
	case BOTTOM:
	    return BottomBoundingPlane;
	default:
	    throw new Exception("Plane name '" + planeName.toString() + "' is not supported");
	}
    }
}
