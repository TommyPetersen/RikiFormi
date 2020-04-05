(ns riki-formi.demo1
  (:use riki-formi.pointCollections
        riki-formi.lineCollections)
  (:import
	(java.io File FileWriter BufferedWriter)
	(java.awt Color)
	(java.util ArrayList)
	(java.lang Math)
	(RikiFormi Camera Polygon3D Triangle3D Point3D Line3D)))

(def camera (new Camera 10.0 200.0 800.0 800.0 1000 1000))

(doto camera
;      (.updateTransformationByXRotation (/ Math/PI 16.0))
      (.updateTransformationByYRotation (/ Math/PI 16.0))
      (.updateTransformationByZRotation (/ Math/PI 16.0))
;      (.updateTransformationByTranslation 0.0 0.0 -1000.0)
)

(let [z0 500.0
      z1 800.0
      color1 Color/red
      color2 Color/white
      color3 Color/green]
     (def filledDiamond1 (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D 700.0 0.0 z0 color1))
     	     	   	  	    (.addPoint (new Point3D 950.0 100.0 z0 color1))
				    (.addPoint (new Point3D 1200.0 0.0 z0 color1))
				    (.addPoint (new Point3D 950.0 -100.0 z0 color1))))
     (def filledDiamond2 (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -700.0 0.0 z0 color1))
     	     	   	  	    (.addPoint (new Point3D -950.0 100.0 z0 color1))
				    (.addPoint (new Point3D -1200.0 0.0 z0 color1))
				    (.addPoint (new Point3D -950.0 -100.0 z0 color1))))
     (def filledRectangle1a (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D 400.0 650.0 z0 color1))
     	     	   	  	    (.addPoint (new Point3D 400.0 850.0 z0 color1))
				    (.addPoint (new Point3D 900.0 850.0 z0 color1))
				    (.addPoint (new Point3D 900.0 650.0 z0 color1))))
     (def filledRectangle2a (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D 700.0 400.0 z0 color2))
     	     	   	  	    (.addPoint (new Point3D 700.0 600.0 z0 color2))
				    (.addPoint (new Point3D 1200.0 600.0 z0 color2))
				    (.addPoint (new Point3D 1200.0 400.0 z0 color2))))
     (def filledRectangle3a (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D 100.0 900.0 z0 color3))
     	     	   	  	    (.addPoint (new Point3D 100.0 1100.0 z0 color3))
				    (.addPoint (new Point3D 600.0 1100.0 z0 color3))
				    (.addPoint (new Point3D 600.0 900.0 z0 color3))))
     (def filledRectangle1b (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -400.0 650.0 z0 color1))
     	     	   	  	    (.addPoint (new Point3D -400.0 850.0 z0 color1))
				    (.addPoint (new Point3D -900.0 850.0 z0 color1))
				    (.addPoint (new Point3D -900.0 650.0 z0 color1))))
     (def filledRectangle2b (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -700.0 400.0 z0 color2))
     	     	   	  	    (.addPoint (new Point3D -700.0 600.0 z0 color2))
				    (.addPoint (new Point3D -1200.0 600.0 z0 color2))
				    (.addPoint (new Point3D -1200.0 400.0 z0 color2))))
     (def filledRectangle3b (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -100.0 900.0 z0 color3))
     	     	   	  	    (.addPoint (new Point3D -100.0 1100.0 z0 color3))
				    (.addPoint (new Point3D -600.0 1100.0 z0 color3))
				    (.addPoint (new Point3D -600.0 900.0 z0 color3))))
     (def filledRectangle4a (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D 400.0 -650.0 z0 color1))
     	     	   	  	    (.addPoint (new Point3D 400.0 -850.0 z0 color1))
				    (.addPoint (new Point3D 900.0 -850.0 z0 color1))
				    (.addPoint (new Point3D 900.0 -650.0 z0 color1))))
				    
     (def filledRectangle5a (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D 700.0 -400.0 z0 color2))
     	     	   	  	    (.addPoint (new Point3D 700.0 -600.0 z0 color2))
				    (.addPoint (new Point3D 1200.0 -600.0 z0 color2))
				    (.addPoint (new Point3D 1200.0 -400.0 z0 color2))))
				    
     (def filledRectangle6a (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D 100.0 -900.0 z0 color3))
     	     	   	  	    (.addPoint (new Point3D 100.0 -1100.0 z0 color3))
				    (.addPoint (new Point3D 600.0 -1100.0 z0 color3))
				    (.addPoint (new Point3D 600.0 -900.0 z0 color3))))
     (def filledRectangle4b (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -400.0 -650.0 z0 color1))
     	     	   	  	    (.addPoint (new Point3D -400.0 -850.0 z0 color1))
				    (.addPoint (new Point3D -900.0 -850.0 z0 color1))
				    (.addPoint (new Point3D -900.0 -650.0 z0 color1))))
     (def filledRectangle5b (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -700.0 -400.0 z0 color2))
     	     	   	  	    (.addPoint (new Point3D -700.0 -600.0 z0 color2))
				    (.addPoint (new Point3D -1200.0 -600.0 z0 color2))
				    (.addPoint (new Point3D -1200.0 -400.0 z0 color2))))
     (def filledRectangle6b (doto (new Polygon3D)
     	  		   	    (.addPoint (new Point3D -100.0 -900.0 z0 color3))
     	     	   	  	    (.addPoint (new Point3D -100.0 -1100.0 z0 color3))
				    (.addPoint (new Point3D -600.0 -1100.0 z0 color3))
				    (.addPoint (new Point3D -600.0 -900.0 z0 color3))))
)


(doto camera
      (.updateScene filledDiamond1)
      (.updateScene filledDiamond2)
      (.updateScene filledRectangle1a)
      (.updateScene filledRectangle2a)
      (.updateScene filledRectangle3a)
      (.updateScene filledRectangle1b)
      (.updateScene filledRectangle2b)
      (.updateScene filledRectangle3b)
      (.updateScene filledRectangle4a)

      (.updateScene filledRectangle5a) ; <---!
      
      (.updateScene filledRectangle6a)
      (.updateScene filledRectangle4b)
      (.updateScene filledRectangle5b)
      (.updateScene filledRectangle6b)
)



(defn const-fn [a b] 0)

(defn offset-const-fn [offset]
   (fn [a b] (+ offset (const-fn a b)))
)

(defn sum-fn [a b] (+ a b))

(defn distance-fn [a b] (Math/sqrt (+ (* a a) (* b b))))

(defn random-fn [a b] (* 5 (rand 10)))

(defn cos-fn [a b]
   (let [distance (Math/sqrt (+ (* a a) (* b b)))
   	 wave-height 5.0]
      (* wave-height (Math/cos distance))
   )
)

(defn offset-cos-fn [offset]
   (fn [a b] (+ offset (cos-fn a b)))
)

(defn sin-fn [a b]
   (let [distance (Math/sqrt (+ (* a a) (* b b)))]
      (* 5.0 (Math/sin distance))
   )
)


(let [point-list (new ArrayList)]
;     (doseq [xy-value1 (compute-centered-rectangular-2D-point-grid 100 100 50 30 'z' (offset-const-fn 200.0))]
;     	 (.add point-list (new Point3D (first xy-value1) (second xy-value1) (last xy-value1) (Color/blue)))
;     )

     (doseq [xy-value2 (compute-centered-rectangular-2D-point-grid 5000 5000 40 20 'y' (offset-const-fn -400.0))]
     	 (.add point-list (new Point3D (first xy-value2) (second xy-value2) (last xy-value2) (Color/yellow)))
     )
     
;     (doseq [xy-value3 (compute-centered-rectangular-2D-point-grid 5000 5000 40 20 'y' (offset-const-fn 400.0))]
;     	 (.add point-list (new Point3D (first xy-value3) (second xy-value3) (last xy-value3) (Color/white)))
;     )
;     (doseq [xy-value5 (compute-centered-rectangular-2D-point-grid 200 5000 20 20 'x' (offset-const-fn 2000.0))]
;     	 (.add point-list (new Point3D (first xy-value5) (second xy-value5) (last xy-value5) (Color/white)))
;     )
     (.updateScene camera point-list)
)



(let [line-list (new ArrayList)]
     (doseq [line-end-points (compute-centered-rectangular-2D-vertical-line-grid 800 3000 20 20 'x' (offset-const-fn 1000.0))]
         (let [x0 (first (first line-end-points))
	       y0 (second (first line-end-points))
	       z0 (last (first line-end-points))
               x1 (first (second line-end-points))
	       y1 (second (second line-end-points))
	       z1 (last (second line-end-points))]
	       
     	     (.add line-list (new Line3D (new Point3D x0 y0 z0 (Color/red))
	                                 (new Point3D x1 y1 z1 (Color/white))))
	 )
     )
;     (.updateScene camera line-list)
)

(let [line-list (new ArrayList)]
     (doseq [line-end-points (compute-centered-rectangular-2D-horisontal-line-grid 800 30000 40 20 'x' (offset-const-fn -2000.0))]
         (let [x0 (first (first line-end-points))
	       y0 (second (first line-end-points))
	       z0 (last (first line-end-points))
               x1 (first (second line-end-points))
	       y1 (second (second line-end-points))
	       z1 (last (second line-end-points))]
	       
     	     (.add line-list (new Line3D (new Point3D x0 y0 z0 (Color/white))
	                                 (new Point3D x1 y1 z1 (Color/white))))
	 )
     )
;     (.updateScene camera line-list)
)

(.showScene camera)
