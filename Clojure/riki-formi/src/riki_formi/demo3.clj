(ns riki-formi.demo3
  (:use riki-formi.pointCollections
        riki-formi.lineCollections)
  (:import
	(java.io File FileWriter BufferedWriter)
	(java.awt Color)
	(java.util ArrayList)
	(java.lang Math)
	(RikiFormi Camera Polygon3D Point3D Line3D Triangle3D)))

(def camera (new Camera 10.0 200.0 800.0 800.0 800 800))

(doto camera
;      (.updateTransformationByXRotation (/ Math/PI 40.0))
;      (.updateTransformationByYRotation (/ Math/PI 3.0))
;      (.updateTransformationByZRotation (- (/ Math/PI 4.0)))
;      (.updateTransformationByTranslation 0.0 0.0 -200.0)
)

(.updateScene camera (new Line3D (new Point3D -200.0 100.0 300.0 (Color/red))
                                 (new Point3D 200.0 100.0 300.0 (Color/white))))
(.updateScene camera (new Line3D (new Point3D 100.0 -100.0 -5000.0 (Color/red))
                                 (new Point3D 100.0 -100.0 5000.0 (Color/white))))
(.updateScene camera (new Line3D (new Point3D -100.0 -100.0 -5000.0 (Color/red))
                                 (new Point3D -100.0 -100.0 5000.0 (Color/white))))

(.updateScene camera (new Point3D -200.0 0.0 300.0 (Color/white)))
(.updateScene camera (new Point3D 0.0 0.0 300.0 (Color/white)))
(.updateScene camera (new Point3D 200.0 0.0 300.0 (Color/white)))

(let [z0 500.0
      z1 800.0
      color1 Color/red
      color2 Color/white]
     (def filledTriangle1A (new Triangle3D 
     	       	     	      (new Point3D -100.0 -100.0 z0 color1)
          	     	      (new Point3D -100.0 100.0 z0 color1)
     	     	   	      (new Point3D 100.0 100.0 z1 color1)))
     (def filledTriangle1B (new Triangle3D 
     	     	   	      (new Point3D 100.0 100.0 z1 color1)
     	     	   	      (new Point3D 100.0 -100.0 z1 color1)
     	       	     	      (new Point3D -100.0 -100.0 z0 color1)))
     (def filledTriangle2A (new Triangle3D
     	       	     	   	(new Point3D -300.0 -100.0 z1 color2)
          	     	  	(new Point3D -300.0 100.0 z1 color2)
     	     	   	  	(new Point3D -100.0 100.0 z0 color2)))
     (def filledTriangle2B (new Triangle3D
     	     	   	  	(new Point3D -100.0 100.0 z0 color2)
     	     	   	  	(new Point3D -100.0 -100.0 z0 color2)
				(new Point3D -300.0 -100.0 z1 color2)))
     (def filledRectangle1 (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -100.0 -200.0 z1 color2))
     	     	   	  	    (.addPoint (new Point3D -100.0 -400.0 z1 color2))
				    (.addPoint (new Point3D 200.0 -400.0 z0 color2))
				    (.addPoint (new Point3D 200.0 -200.0 z0 color2))))
)

(doto camera
      (.updateScene filledTriangle1A)
      (.updateScene filledTriangle1B)
      (.updateScene filledTriangle2A)
      (.updateScene filledTriangle2B)
      (.updateScene filledRectangle1)
)



(.showScene camera)


(str "Demo3 is over!")
