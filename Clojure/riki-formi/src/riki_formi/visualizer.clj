(ns riki-formi.visualizer
  (:import
	(java.io File FileWriter BufferedWriter)
	(java.awt Color)
	(RikiFormi Camera Polygon3D Point3D)))

(def camera (new Camera 10.0 70.0 600.0 400.0 1600 1000))

(def polygon0 (doto (new Polygon3D) (.addPoint (new Point3D -22.0 0.0 20.0 Color/blue))
     	     	   		    (.addPoint (new Point3D -10.0 20.0 20.0 Color/darkGray))
     	     	   		    (.addPoint (new Point3D 0.0 25.0 20.0 Color/darkGray))
     	     	   		    (.addPoint (new Point3D 5.0 30.0 20.0 Color/darkGray))
				    (.addPoint (new Point3D 10.0 -2.0 20.0 Color/white))))

(.updateScene camera polygon0)
