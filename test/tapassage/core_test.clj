(ns tapassage.core-test
  (:require [clojure.test :refer :all]
            [tapassage.core :as tp]
            [clojure.algo.generic.math-functions :refer [approx=]])
  (:import [com.tictactec.ta.lib Core MInteger RetCode]))

(def ^:private total-periods 50)
(def ^:private ma-period 5)
(def ^:private input (double-array (repeatedly total-periods rand)))
(def ^:private epsilon 1e-15)

(defn seq-approx= [xs ys]
  (every? (fn [[x y]] (approx= x y epsilon)) (map vector xs ys)))

(deftest test-sma
  (testing "SMA"
    (let [out-start-idx (new MInteger)
          out-len (new MInteger)
          output (double-array total-periods)
          ret-code (.. (new Core) (sma 0 (dec total-periods) input ma-period
                                       out-start-idx out-len output))
          tp-sma (sequence (tp/sma ma-period) input)
          ta-sma (take (.-value out-len) (seq output))]
      (is (= ret-code RetCode/Success))
      (is (= (count tp-sma) (count ta-sma)))
      (is (seq-approx= tp-sma ta-sma)))))

(deftest test-ema
  (testing "EMA"
    (let [out-start-idx (new MInteger)
          out-len (new MInteger)
          output (double-array total-periods)
          ret-code (.. (new Core) (ema 0 (dec total-periods) input ma-period
                                       out-start-idx out-len output))
          tp-ema (sequence (tp/ema ma-period) input)
          ta-ema (take (.-value out-len) (seq output))]
      (is (= ret-code RetCode/Success))
      (is (= (count tp-ema) (count ta-ema)))
      (is (seq-approx= tp-ema ta-ema)))))

(deftest test-dema
  (testing "DEMA"
    (let [out-start-idx (new MInteger)
          out-len (new MInteger)
          output (double-array total-periods)
          ret-code (.. (new Core) (dema 0 (dec total-periods) input ma-period
                                        out-start-idx out-len output))
          tp-dema (sequence (tp/dema ma-period) input)
          ta-dema (take (.-value out-len) (seq output))]
      (is (= ret-code RetCode/Success))
      (is (= (count tp-dema) (count ta-dema)))
      (is (seq-approx= tp-dema ta-dema)))))

(deftest test-tema
  (testing "TEMA"
    (let [out-start-idx (new MInteger)
          out-len (new MInteger)
          output (double-array total-periods)
          ret-code (.. (new Core) (tema 0 (dec total-periods) input ma-period
                                        out-start-idx out-len output))
          tp-tema (sequence (tp/tema ma-period) input)
          ta-tema (take (.-value out-len) (seq output))]
      (is (= ret-code RetCode/Success))
      (is (= (count tp-tema) (count ta-tema)))
      (is (= (seq-approx= tp-tema ta-tema))))))

(deftest test-wma
  (testing "WMA"
    (let [out-start-idx (new MInteger)
          out-len (new MInteger)
          output (double-array total-periods)
          ret-code (.. (new Core) (wma 0 (dec total-periods) input ma-period
                                       out-start-idx out-len output))
          tp-wma (sequence (tp/wma ma-period) input)
          ta-wma (take (.-value out-len) (seq output))]
      (is (= ret-code RetCode/Success))
      (is (= (count tp-wma) (count ta-wma)))
      (is (= (seq-approx= tp-wma ta-wma))))))
