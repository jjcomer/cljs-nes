(ns cljsnes.display
  (:require [clojure.pprint :as pprint]))

(enable-console-print!)

(def palette [#_0x00 [0x75 0x75 0x75]
              #_0x01 [0x27 0x1B 0x8F]
              #_0x02 [0x00 0x00 0xAB]
              #_0x03 [0x47 0x00 0x9F]
              #_0x04 [0x8F 0x00 0x77]
              #_0x05 [0xAB 0x00 0x13]
              #_0x06 [0xA7 0x00 0x00]
              #_0x07 [0x7F 0x0B 0x00]
              #_0x08 [0x43 0x2F 0x00]
              #_0x09 [0x00 0x47 0x00]
              #_0x0A [0x00 0x51 0x00]
              #_0x0B [0x00 0x3F 0x17]
              #_0x0C [0x1B 0x3F 0x5F]
              #_0x0D [0x00 0x00 0x00]
              #_0x0E [0x00 0x00 0x00]
              #_0x0F [0x00 0x00 0x00]
              #_0x10 [0xBC 0xBC 0xBC]
              #_0x11 [0x00 0x73 0xEF]
              #_0x12 [0x23 0x3B 0xEF]
              #_0x13 [0x83 0x00 0xF3]
              #_0x14 [0xBF 0x00 0xBF]
              #_0x15 [0xE7 0x00 0x5B]
              #_0x16 [0xDB 0x2B 0x00]
              #_0x17 [0xCB 0x4F 0x0F]
              #_0x18 [0x8B 0x73 0x00]
              #_0x19 [0x00 0x97 0x00]
              #_0x1A [0x00 0xAB 0x00]
              #_0x1B [0x00 0x93 0x3B]
              #_0x1C [0x00 0x83 0x8B]
              #_0x1D [0x00 0x00 0x00]
              #_0x1E [0x00 0x00 0x00]
              #_0x1F [0x00 0x00 0x00]
              #_0x20 [0xFF 0xFF 0xFF]
              #_0x21 [0x3F 0xBF 0xFF]
              #_0x22 [0x5F 0x97 0xFF]
              #_0x23 [0xA7 0x8B 0xFD]
              #_0x24 [0xF7 0x7B 0xFF]
              #_0x25 [0xFF 0x77 0xB7]
              #_0x26 [0xFF 0x77 0x63]
              #_0x27 [0xFF 0x9B 0x3B]
              #_0x28 [0xF3 0xBF 0x3F]
              #_0x29 [0x83 0xD3 0x13]
              #_0x2A [0x4F 0xDF 0x4B]
              #_0x2B [0x58 0xF8 0x98]
              #_0x2C [0x00 0xEB 0xDB]
              #_0x2D [0x00 0x00 0x00]
              #_0x2E [0x00 0x00 0x00]
              #_0x2F [0x00 0x00 0x00]
              #_0x30 [0xFF 0xFF 0xFF]
              #_0x31 [0xAB 0xE7 0xFF]
              #_0x32 [0xC7 0xD7 0xFF]
              #_0x33 [0xD7 0xCB 0xFF]
              #_0x34 [0xFF 0xC7 0xFF]
              #_0x35 [0xFF 0xC7 0xDB]
              #_0x36 [0xFF 0xBF 0xB3]
              #_0x37 [0xFF 0xDB 0xAB]
              #_0x38 [0xFF 0xE7 0xA3]
              #_0x39 [0xE3 0xFF 0xA3]
              #_0x3A [0xAB 0xF3 0xBF]
              #_0x3B [0xB3 0xFF 0xCF]
              #_0x3C [0x9F 0xFF 0xF3]
              #_0x3D [0x00 0x00 0x00]
              #_0x3E [0x00 0x00 0x00]
              #_0x3F [0x00 0x00 0x00]])

(def palette-a (apply array (map #(apply array %) palette)))

(defn render-frame [buffer ctx]
  (when ctx
    (let [image-data (.createImageData ctx 256 224)
          data (.-data image-data)]
      (loop [x 0
             y 0]
        (let [rgb (aget palette-a (get-in buffer [y x]))
              r (aget rgb 0)
              g (aget rgb 1)
              b (aget rgb 2)
              idx (* 4 (+ x (* y 256)))]
          (aset data idx r)
          (aset data (+ idx 1) g)
          (aset data (+ idx 2) b)
          (aset data (+ idx 3) 0xFF))
        (if (not (and (= x 256) (= y 224)))
          (recur (if (= x 256) 0 (inc x)) (if (do (= x 256)) (inc y) y))))
      (.putImageData ctx image-data 0 0))))
