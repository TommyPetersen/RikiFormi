package RikiFormi;

import java.util.*;

public class RasterPolygon{

    public ArrayList<RasterPoint> RasterPoints;

    public RasterPolygon(){
	RasterPoints = new ArrayList<RasterPoint>();
    }

    public void addPoint(RasterPoint rasterPoint){
	RasterPoints.add(rasterPoint);
    }

    public ArrayList toRasterLineList(){
	Iterator<RasterPoint> IL = RasterPoints.iterator();
	
	ArrayList<RasterLine> lines = new ArrayList<RasterLine>();
	
	if (IL.hasNext()){
	    RasterPoint p0, pi, pj;
	    
	    p0 = pi = IL.next();

	    while (IL.hasNext()){
		pj = IL.next();

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
