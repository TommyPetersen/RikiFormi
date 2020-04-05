(ns riki-formi.pointCollections)

(defn compute-centered-rectangular-2D-point-grid [width height w-spacing h-spacing plane-id plane-value-fn]
      (if (or (<= width 0) (<= height 0)) nil)
      (if (or (< w-spacing 0) (> w-spacing width)) nil)
      (if (or (< h-spacing 0) (> h-spacing height)) nil)
      (if (not (and (= plane-id 'x') (= plane-id 'y') (= plane-id 'z'))) nil)

      (for [b (range (- (/ height 2.0)) (/ height 2.0) h-spacing)
            a (range (- (/ width 2.0)) (/ width 2.0) w-spacing)]
	  (if (= plane-id 'x')
	      [(plane-value-fn a b) a b]
	      (if (= plane-id 'y')
	      	  [a (plane-value-fn a b) b]
	  	  [a b (plane-value-fn a b)]
	      )
	  )
      )
)
