(ns riki-formi.lineCollections)

(defn compute-centered-rectangular-2D-vertical-line-grid [width height w-spacing h-spacing plane-id plane-value-fn]
      (if (or (<= width 0) (<= height 0)) nil)
      (if (or (< w-spacing 0) (> w-spacing width)) nil)
      (if (or (< h-spacing 0) (> h-spacing height)) nil)
      (if (not (and (= plane-id 'x') (= plane-id 'y') (= plane-id 'z'))) nil)

      (for [b (range (- (/ height 2.0)) (/ height 2.0) h-spacing)]
          (let [a (/ width 2.0)
	        fn-value-on-lower (plane-value-fn (- a) b)
	  	fn-value-on-upper (plane-value-fn a b)]
	      (if (= plane-id 'x')
	          [[fn-value-on-lower (- a) b] [fn-value-on-upper a b]]
	          (if (= plane-id 'y')
	      	      [[(- a) fn-value-on-lower b] [a fn-value-on-upper b]]
	  	      [[(- a) b fn-value-on-lower] [a b fn-value-on-upper]]
	          )
	      )
	  )
      )
)

(defn compute-centered-rectangular-2D-horisontal-line-grid [width height w-spacing h-spacing plane-id plane-value-fn]
      (if (or (<= width 0) (<= height 0)) nil)
      (if (or (< w-spacing 0) (> w-spacing width)) nil)
      (if (or (< h-spacing 0) (> h-spacing height)) nil)
      (if (not (and (= plane-id 'x') (= plane-id 'y') (= plane-id 'z'))) nil)

      (for [a (range (- (/ width 2.0)) (/ width 2.0) w-spacing)]
          (let [b (/ height 2.0)
	        fn-value-on-lower (plane-value-fn a (- b))
	  	fn-value-on-upper (plane-value-fn a b)]
	      (if (= plane-id 'x')
	          [[fn-value-on-lower a (- b)] [fn-value-on-upper a b]]
	          (if (= plane-id 'y')
	      	      [[a fn-value-on-lower (- b)] [a fn-value-on-upper b]]
	  	      [[a (- b) fn-value-on-lower] [a b fn-value-on-upper]]
	          )
	      )
	  )
      )
)
