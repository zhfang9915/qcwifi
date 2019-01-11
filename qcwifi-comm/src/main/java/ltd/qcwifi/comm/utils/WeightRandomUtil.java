package ltd.qcwifi.comm.utils;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.math3.util.Pair;

import com.google.common.base.Preconditions;

public class WeightRandomUtil<K, V extends Number> {
	
	private TreeMap<Double, K> weightMap = new TreeMap<Double, K>();
	

	public WeightRandomUtil(List<Pair<K, V>> list) {
		Preconditions.checkNotNull(list, "list can NOT be null!");
		for (Pair<K, V> pair : list) {
			double lastWeight = this.weightMap.size() == 0 ? 0 : this.weightMap.lastKey().doubleValue();// 统一转为double
			this.weightMap.put(pair.getValue().doubleValue() + lastWeight, pair.getKey());// 权重累加
		}
	}

	public K random() {
		double randomWeight = this.weightMap.lastKey() * Math.random();
		SortedMap<Double, K> tailMap = this.weightMap.tailMap(randomWeight, false);
		return this.weightMap.get(tailMap.firstKey());
	}
	
	
	

}
