package com.stupidbird.actor.example.cluster.transform;

public class TransformApp {

	public static void main(String[] args) {
		// start 2 frontend nodes and 3 backend nodes
		BackendMain.main(new String[] { "2551" });
		BackendMain.main(new String[] { "2552" });
		BackendMain.main(new String[] { "2553" });
		FrontendMain.main(new String[0]);
	}

}
