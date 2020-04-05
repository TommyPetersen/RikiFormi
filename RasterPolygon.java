package RikiFormi;

import java.util.*;

public class RasterPolygon{

    public ArrayList<RasterPoint> RasterPoints;

    public RasterPolygon(){
	RasterPoints = new ArrayList();
    }

    public void addPoint(RasterPoint rasterPoint){
	RasterPoints.add(rasterPoint);
    }

    public ArrayList toRasterLineList(){
	Iterator IL = RasterPoints.iterator();
	
	ArrayList lines = new ArrayList();
	
	if (IL.hasNext()){
	    RasterPoint p0, pi, pj;
	    
	    p0 = pi = (RasterPoint) IL.next();

	    while (IL.hasNext()){
		pj = (RasterPoint) IL.next();

		lines.add(new RasterLine(pi, pj));

		pi = pj;
	    }

	    lines.add(new RasterLine(pi, p0));
	}

	return lines;
    }

    public Iterator<RasterPoint> iterator(){
	return RasterPoints.iterator();
    }
}
