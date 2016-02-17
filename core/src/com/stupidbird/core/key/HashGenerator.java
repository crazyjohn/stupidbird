package com.stupidbird.core.key;

public class HashGenerator implements ObjectKeyGenerator<Integer> {
	@Override
	public Integer generate(Object object) {
		// if (key instanceof ISceneActiveObject) {
		// ISceneActiveObject sceneObject = (ISceneActiveObject) key;
		// return sceneObject.getId();
		// }
		return System.identityHashCode(object);
	}
}
