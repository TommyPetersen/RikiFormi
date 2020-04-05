(ns riki-formi.demo2
  (:use riki-formi.pointCollections
        riki-formi.lineCollections)
  (:import
	(java.io File FileWriter BufferedWriter)
	(java.awt Color)
	(java.util ArrayList)
	(java.lang Math)
	(RikiFormi Camera Polygon3D Point3D Line3D)))

(def camera (new Camera 10.0 200.0 600.0 400.0 1600 1000))

(doto camera
;      (.updateTransformationByXRotation (/ Math/PI 40.0))
;      (.updateTransformationByYRotation (/ Math/PI 3.0))
;      (.updateTransformationByZRotation (- (/ Math/PI 4.0)))
      (.updateTransformationByTranslation 0.0 0.0 -200.0)
)

(let [z0 200.0
      z1 400.0
      color1 Color/red
      color2 Color/white
      color3 Color/blue
      color4 Color/green]
     (def filledRectangle1 (doto (new Polygon3D)
     	       	     	  (.addPoint (new Point3D -300.0 -300.0 5 color1))
          	     	  (.addPoint (new Point3D -300.0 300.0 z0 color2))
     	     	   	  (.addPoint (new Point3D 300.0 300.0 z1 color3))
     	     	   	  (.addPoint (new Point3D 300.0 -300.0 z1 color4))
			  ))
)


(doto camera
      (.updateScene filledRectangle1)
)

(.showScene camera)
